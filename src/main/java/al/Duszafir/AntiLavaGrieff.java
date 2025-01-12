package al.Duszafir;

import al.Duszafir.commands.MainCommand;
import al.Duszafir.listeners.PlayerListener;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

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
        createLogFile();
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fHas been activated! &9Version: "+version));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fThanks for using this plugin!"));
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fHas been deactivated. &9Version: "+version));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix+"&fThanks for using this plugin!"));
    }

    public void registerCommands() {
        this.getCommand("antilavagrief").setExecutor(new MainCommand(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private void createLogFile() {
        File logFile = new File(getDataFolder(), "grief_logs.txt");
        if (!logFile.exists()) {
            try {
                if (logFile.createNewFile()) {
                    getLogger().info("Log file created: grief_logs.txt");
                }
            } catch (IOException e) {
                getLogger().severe("The log file could not be created.");
                e.printStackTrace();
            }
        }
    }
}