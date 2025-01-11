package al.Duszafir.listeners;

import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final HashMap<Block, UUID> lavaPlacers = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (AntiLavaGrieff.isEnabled){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!player.hasPermission("antilavagrief.evadeGrief")) {
                    if (block != null && isPlanks(block.getType()) &&
                            (item.getType().equals(Material.FLINT_AND_STEEL) || item.getType().equals(Material.LAVA_BUCKET) || item.getType().equals(Material.LAVA))) {
                        String blockName = formatName(block.getType().name());
                        String itemName = formatName(item.getType().name());
                        Bukkit.broadcastMessage(
                                MessageUtils.getColoredMessage(
                                        AntiLavaGrieff.prefix + "&4" + player.getName() + " has burned a " + blockName + " with a " + itemName));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLavaFlow(BlockFromToEvent event) {
        Block sourceBlock = event.getBlock();
        Block targetBlock = event.getToBlock().getRelative(0, -1, 0);

        if (AntiLavaGrieff.isEnabled){
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
                }
            }
        }
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
}