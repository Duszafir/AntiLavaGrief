package al.Duszafir;

import al.Duszafir.commands.MainCommand;
import al.Duszafir.listeners.PlayerListener;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiLavaGrieff extends JavaPlugin {

    public static String prefix = "&c&l[&fAnti&4&lLava&fGrief&c&l] ";
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

        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fHas been activated! &9Version:"+version));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&7Thanks for using this plugin!"));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fHas been deactivated. &9Version:"+version));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&7Thanks for using this plugin!"));
    }

    public void registerCommands() {
        this.getCommand("antilavagrief").setExecutor(new MainCommand(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}