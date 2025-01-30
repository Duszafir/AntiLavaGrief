package al.Duszafir.utils;

import al.Duszafir.AntiLavaGrieff;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BannedBlocks {

    private static AntiLavaGrieff plugin;

    public static void initialize(AntiLavaGrieff pluginInstance) {
        plugin = pluginInstance;
    }

    public static boolean isAllowed(Material item) {
        if (plugin != null && plugin.getMainConfigManager().getBanBlocksList().contains(item.toString())) {
            return false;
        }
        return true;
    }
}

