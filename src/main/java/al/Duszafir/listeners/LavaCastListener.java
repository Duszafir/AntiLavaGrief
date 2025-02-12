package al.Duszafir.listeners;

import al.Duszafir.AntiLavaGrieff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LavaCastListener implements Listener {

    private AntiLavaGrieff plugin;

    public LavaCastListener(AntiLavaGrieff plugin) {
        this.plugin = plugin;
    }

    private final Map<Location, Integer> cobblestoneCount = new HashMap<>();
    private Location lastGeneratedLocation = null;
    private int cobblestoneTotal = 0;

    private final Map<UUID, Long> blockTimers = new HashMap<>();
    private boolean resetOccurred = false;
    private boolean check = true;

    private int taskID = -1;

    @EventHandler
    public void onCobblestoneForm(BlockFormEvent event) {
        if (check && cobblestoneCount.size() > 0) {
            taskID = Bukkit.getScheduler().runTaskTimer(plugin, this::checkTimeouts, 0L, 20L).getTaskId();
            check = false;
        }

        Block block = event.getBlock();
        Material type = event.getNewState().getType();

        if (type == Material.COBBLESTONE || type == Material.STONE) {
            Location blockLocation = block.getLocation();

            blockTimers.put(blockLocation.getWorld().getUID(), System.currentTimeMillis());

            if (!cobblestoneCount.containsKey(blockLocation)) {
                if (lastGeneratedLocation == null || lastGeneratedLocation.distance(blockLocation) > 1) {
                    cobblestoneTotal++;
                    lastGeneratedLocation = blockLocation;
                }
                cobblestoneCount.put(blockLocation, 1);
            }

            if (cobblestoneTotal >= plugin.getMainConfigManager().getLavaCastBlocks()) {
                removeGeneratedCobblestones();
                removeNearbyFluids(lastGeneratedLocation, 20);
                resetCobblestoneList();
            }

            resetOccurred = false;
        }
    }

    private void checkTimeouts() {
        long currentTime = System.currentTimeMillis();
        boolean reset = false;

        for (UUID worldUUID : blockTimers.keySet()) {
            long lastTime = blockTimers.get(worldUUID);
            if (currentTime - lastTime > 5000) {
                reset = true;
                break;
            }
        }

        if (reset && !resetOccurred) {
            resetCobblestoneList();
            resetOccurred = true;
        }

        if (cobblestoneCount.isEmpty()) {
            cancelTimeoutTask();
        }
    }

    private void resetCobblestoneList() {
        cobblestoneCount.clear();
        cobblestoneTotal = 0;
        lastGeneratedLocation = null;
        check = true;
        if (!cobblestoneCount.isEmpty()) {
            taskID = Bukkit.getScheduler().runTaskTimer(plugin, this::checkTimeouts, 0L, 20L).getTaskId();
        }
    }

    private void removeGeneratedCobblestones() {
        for (Location location : cobblestoneCount.keySet()) {
            location.getBlock().setType(Material.AIR);
        }
        cobblestoneCount.clear();
        cobblestoneTotal = 0;
    }

    private void removeNearbyFluids(Location center, int radius) {
        int startX = center.getBlockX() - radius;
        int startY = center.getBlockY() - radius;
        int startZ = center.getBlockZ() - radius;
        int endX = center.getBlockX() + radius;
        int endY = center.getBlockY() + radius;
        int endZ = center.getBlockZ() + radius;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Location loc = new Location(center.getWorld(), x, y, z);
                    Block block = loc.getBlock();
                    if (block.getType() == Material.WATER || block.getType() == Material.LAVA) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    private void cancelTimeoutTask() {
        if (taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
            taskID = -1;
        }
    }
}
