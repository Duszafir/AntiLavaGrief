package al.Duszafir;

import al.Duszafir.commands.MainCommand;
import al.Duszafir.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiLavaGrieff extends JavaPlugin {

    public static String prefix = "&4&l[AntiLava&9Grief&4] ";
    public String version = getDescription().getVersion();
    private static AntiLavaGrieff instance;
    public static boolean isEnabled = true;

    public static AntiLavaGrieff getInstance() {
        return instance;
    }

    public void onEnable() {
        registerCommands();
        registerEvents();
        instance = this;

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&cHa sido activado! &9Version:" + version));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&cHa sido desactivado. &9Version:" + version));
    }

    public void registerCommands() {
        this.getCommand("antilavagrief").setExecutor(new MainCommand());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
