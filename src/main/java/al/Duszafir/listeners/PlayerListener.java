package al.Duszafir.listeners;

import al.Duszafir.AntiLavaGrieff;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerListener implements Listener {

    private final HashMap<Block, UUID> lavaPlacers = new HashMap<>();
    private final ConcurrentHashMap<UUID, Long> cooldowns = new ConcurrentHashMap<>();
    private static final long COOLDOWN_TIME = 10000;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();

        if (AntiLavaGrieff.isEnabled) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
                if (item != null && (item.getType() == Material.LAVA_BUCKET || item.getType() == Material.FLINT_AND_STEEL)) {
                    if (isPlanks(block.getType())) {
                        if (!player.hasPermission("antilavagrief.bypassCooldown")) {
                            if (isOnCooldown(player)) {
                                long timeLeft = (COOLDOWN_TIME - (System.currentTimeMillis() - cooldowns.get(player.getUniqueId()))) / 1000;
                                player.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cYou need to wait &f" + timeLeft + "&c more seconds before using that again!"));

                                event.setCancelled(true);
                                return;
                            }
                            startCooldown(player);
                        }

                        if (!player.hasPermission("antilavagrief.evadeGrief")) {
                            String blockName = formatName(block.getType().name());
                            String itemName = formatName(item.getType().name());
                            Bukkit.broadcastMessage(
                                    MessageUtils.getColoredMessage(
                                            AntiLavaGrieff.prefix + "&4" + player.getName() + " has burned a " + blockName + " with a " + itemName));
                            logGriefAttempt(player.getName(), block.getLocation());
                        }
                    }
                }
            }
        }
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
                            MessageUtils.getColoredMessage(
                                    AntiLavaGrieff.prefix + "&4" + placer.getName() + " burned " + blockName));
                    logGriefAttempt(placer.getName(), targetBlock.getLocation());
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
