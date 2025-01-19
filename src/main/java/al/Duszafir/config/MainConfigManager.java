package al.Duszafir.config;

import al.Duszafir.AntiLavaGrieff;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MainConfigManager {

    private CustomConfig configFile;

    private AntiLavaGrieff plugin;

    private String antiGriefEnableText;
    private String cooldownTxt;
    private String griefTxt;
    private String lavaGriefTxt;
    private boolean cooldownEnable;
    private int cooldownInt;
    private List<String> worlds;

    public MainConfigManager(AntiLavaGrieff plugin){
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml",plugin,null);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig(){
        FileConfiguration config = configFile.getConfig();
        antiGriefEnableText = config.getString("messages.anti_grief_enable_text");
        cooldownEnable = config.getBoolean("config.cooldown.enabled");
        cooldownInt = config.getInt("config.cooldown.cooldown-time");
        cooldownTxt = config.getString("messages.cooldown_text");
        griefTxt = config.getString("messages.grief_text");
        lavaGriefTxt = config.getString("messages.lava_place_text");
        worlds = config.getStringList("config.worlds");
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }

    public String getAntiGriefEnableText() {
        return antiGriefEnableText;
    }

    public boolean isCooldownEnable() {
        return cooldownEnable;
    }

    public int getCooldownInt() {
        return cooldownInt;
    }

    public String getCooldownTxt() {
        return cooldownTxt;
    }

    public String getGriefTxt() {
        return griefTxt;
    }

    public String getLavaGriefTxt() {
        return lavaGriefTxt;
    }

    public List<String> getWorlds() {
        return worlds;
    }
}
