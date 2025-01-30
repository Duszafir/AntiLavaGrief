package al.Duszafir.listeners;
import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.BannedBlocks;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerListener implements Listener {

    private AntiLavaGrieff plugin;

    public PlayerListener(AntiLavaGrieff plugin){
        this.plugin = plugin;
    }

    private final HashMap<Block, UUID> lavaPlacers = new HashMap<>();
    private final ConcurrentHashMap<UUID, Long> cooldowns = new ConcurrentHashMap<>();
    private static final long COOLDOWN_TIME = 10000;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        Boolean isCooldownActive = plugin.getMainConfigManager().isCooldownEnable();
        int cooldownTime = plugin.getMainConfigManager().getCooldownInt() * 1000;
        List<String> worlds = plugin.getMainConfigManager().getWorlds();
        boolean isBanPlaceEnabled = plugin.getMainConfigManager().isBanPlaceEnabled();
        List<String> allowedBlocks = plugin.getMainConfigManager().getAllowedBlocks();

        if (AntiLavaGrieff.isEnabled && worlds.contains(player.getWorld().getName())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
                if (item != null && !BannedBlocks.isAllowed(item.getType())) {
                    if (isBanPlaceEnabled) {
                        if (!allowedBlocks.contains(block.getType().name()) && !player.hasPermission("antilavagrief.evadeGrief")) {
                            event.setCancelled(true);
                            player.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + plugin.getMainConfigManager().getBanPlaceTxt()));
                            return;
                        }
                    } else if (isPlanks(block.getType()) || isLog(block.getType())) {
                        if (isCooldownActive && !player.hasPermission("antilavagrief.bypassCooldown")) {
                            if (isOnCooldown(player)) {
                                long timeLeft = (cooldowns.get(player.getUniqueId()) + cooldownTime - System.currentTimeMillis()) / 1000;
                                player.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + plugin.getMainConfigManager().getCooldownTxt().replace("%timeLeft%", String.valueOf(timeLeft))));

                                event.setCancelled(true);
                                return;
                            }
                            startCooldown(player, cooldownTime);
                        }

                        if (!player.hasPermission("antilavagrief.evadeGrief")) {
                            String blockName = formatName(block.getType().name());
                            String itemName = formatName(item.getType().name());
                            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + plugin.getMainConfigManager().getGriefTxt().replace(
                                    "%player%", player.getName()).replace("%block%", blockName).replace("%item%", itemName)));
                            logGriefAttempt(player.getName(), block.getLocation());
                        }
                    }
                }
            }
        }
    }


    private void startCooldown(Player player, int cooldownTime) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        Bukkit.getScheduler().runTaskLater(plugin, () -> cooldowns.remove(player.getUniqueId()), cooldownTime / 50);
    }

    @EventHandler
    public void onLavaFlow(BlockFromToEvent event) {
        Block sourceBlock = event.getBlock();
        Block targetBlock = event.getToBlock().getRelative(0, -1, 0);

        if (AntiLavaGrieff.isEnabled) {
            if (sourceBlock.getType() == Material.LAVA && isPlanksBlock(targetBlock)) {
                Player placer = (Player) sourceBlock.getWorld().getEntities().stream()
                        .filter(entity -> entity instanceof Player)
                        .filter(player -> player.getLocation().distance(sourceBlock.getLocation()) < 5)
                        .findFirst().orElse(null);

                if (placer != null && !placer.hasPermission("antilavagrief.evadeGrief")) {
                    String blockName = targetBlock.getType().name();
                    Bukkit.broadcastMessage(
                            MessageUtils.getColoredMessage(AntiLavaGrieff.prefix +
                                    plugin.getMainConfigManager().getLavaGriefTxt().replace("%placer%", placer.getName()).replace("%block%",blockName)));
                    logGriefAttempt(placer.getName(), targetBlock.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();

        if (plugin.getMainConfigManager().isAntiDispenserEnabled()) {

            if (event.getWhoClicked().hasPermission("antilavagrief.bypass_dispenser")) {
                return;
            }

            if (event.isShiftClick()) {
                InventoryView openInventory = event.getWhoClicked().getOpenInventory();
                Inventory topInventory = openInventory.getTopInventory();

                if (topInventory != null &&
                        (topInventory.getType().equals(InventoryType.DISPENSER) ||
                                topInventory.getType().equals(InventoryType.HOPPER))) {

                    if (clickedInventory != null &&
                            (clickedInventory.getType() == InventoryType.PLAYER ||
                                    clickedInventory.getType() == InventoryType.CHEST ||
                                    clickedInventory.getType() == InventoryType.SHULKER_BOX)) {

                        if (currentItem != null && !BannedBlocks.isAllowed(currentItem.getType())) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }

            if (clickedInventory == null ||
                    !(clickedInventory.getType().equals(InventoryType.DISPENSER) ||
                            clickedInventory.getType().equals(InventoryType.HOPPER))) {
                return;
            }

            ItemStack cursorItem = event.getCursor();
            if (cursorItem != null && !BannedBlocks.isAllowed(cursorItem.getType())) {
                event.setCancelled(true);
                return;
            }

            if (currentItem != null && !BannedBlocks.isAllowed(currentItem.getType())) {
                event.setCancelled(true);
                return;
            }

            if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
                int hotbarSlot = event.getHotbarButton();

                if (hotbarSlot >= 0 && hotbarSlot <= 8) {
                    ItemStack hotbarItem = event.getWhoClicked().getInventory().getItem(hotbarSlot);
                    if (hotbarItem != null && !BannedBlocks.isAllowed(hotbarItem.getType())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHandSwap(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (plugin.getMainConfigManager().isAntiDispenserEnabled()) {
            if (clickedInventory == null ||
                    !(clickedInventory.getType() == InventoryType.DISPENSER ||
                            clickedInventory.getType() == InventoryType.HOPPER)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();

            if (event.getClick() == ClickType.SWAP_OFFHAND && !player.hasPermission("antilavagrief.bypass_dispenser")) {

                ItemStack offHandItem = player.getInventory().getItemInOffHand();
                if (offHandItem != null && !BannedBlocks.isAllowed(offHandItem.getType())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory clickedInventory = event.getInventory();

        if (plugin.getMainConfigManager().isAntiDispenserEnabled()) {
            if (clickedInventory == null ||
                    !(clickedInventory.getType() == InventoryType.DISPENSER ||
                            clickedInventory.getType() == InventoryType.HOPPER)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();

            ItemStack draggedItem = event.getOldCursor();
            if (draggedItem != null && !BannedBlocks.isAllowed(draggedItem.getType()) && !player.hasPermission("antilavagrief.bypass_dispenser")) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onHopperItemPickup(InventoryPickupItemEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack item = event.getItem().getItemStack();

        if (plugin.getMainConfigManager().isAntiDispenserEnabled()) {
            if (inventory != null && inventory.getType() == InventoryType.HOPPER) {
                if (item != null && !BannedBlocks.isAllowed(item.getType())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemMoveToHopper(InventoryMoveItemEvent event) {
        Inventory destination = event.getDestination();

        if (destination.getType() != InventoryType.HOPPER) {
            return;
        }

        ItemStack movedItem = event.getItem();

        if (movedItem != null && !BannedBlocks.isAllowed(movedItem.getType()) && plugin.getMainConfigManager().isAntiDispenserEnabled()) {
            event.setCancelled(true);

            Location hopperLocation = destination.getLocation();
            Inventory source = event.getSource();

            if (source != null && hopperLocation != null) {
                int totalAmount = 0;

                for (ItemStack item : source.getContents()) {
                    if (item != null && item.getType() == movedItem.getType()) {
                        totalAmount += item.getAmount();
                    }
                }

                if (totalAmount > 0) {
                    source.removeItem(new ItemStack(movedItem.getType(), totalAmount));
                }
            }
        }
    }

    private boolean isOnCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        return cooldowns.containsKey(playerId) && (currentTime - cooldowns.get(playerId) < COOLDOWN_TIME);
    }

    private void startCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    private boolean isPlanks(Material material) {
        return material.name().endsWith("PLANKS");
    }

    public boolean isLog(Material material) {
        return material.name().contains("LOG");
    }


    private boolean isPlanksBlock(Block block) {
        Material material = block.getType();
        return material == Material.OAK_PLANKS || material == Material.SPRUCE_PLANKS ||
                material == Material.BIRCH_PLANKS || material == Material.JUNGLE_PLANKS ||
                material == Material.ACACIA_PLANKS || material == Material.DARK_OAK_PLANKS ||
                material == Material.MANGROVE_PLANKS || material == Material.CHERRY_PLANKS;
    }

    private String formatName(String name) {
        String formatted = name.toLowerCase().replace("_", " ");
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }

    private void logGriefAttempt(String playerName, Location location) {
        String logEntry = String.format(
                "[%s] Player %s attempted to place lava at %d, %d, %d (World: %s)%n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                playerName,
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld().getName()
        );

        File logFile = new File(AntiLavaGrieff.getInstance().getDataFolder(), "grief_logs.txt");

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            Bukkit.getLogger().severe("The log file could not be created.");
            e.printStackTrace();
        }
    }
}
