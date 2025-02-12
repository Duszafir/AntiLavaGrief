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
    private boolean banPlaceEnabled;
    private List<String> allowedBlocks;
    private String banPlaceTxt;
    private List<String> banBlocksList;
    private boolean antiDispenserEnabled;
    private boolean antiLavaCast;
    private int lavaCastBlocks;

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
        banPlaceEnabled = config.getBoolean("config.block_lava_place.enabled");
        allowedBlocks = config.getStringList("config.block_lava_place.allowed_blocks");
        banPlaceTxt = config.getString("messages.ban_lava_place");
        banBlocksList = config.getStringList("ban_blocks");
        antiDispenserEnabled = config.getBoolean("config.anti_dispenser_and_hopper.enabled");
        antiLavaCast = config.getBoolean("config.anti_lavacast.enabled");
        lavaCastBlocks = config.getInt("config.anti_lavacast.blocks");
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

    public boolean isBanPlaceEnabled() {
        return banPlaceEnabled;
    }

    public List<String> getAllowedBlocks() {
        return allowedBlocks;
    }

    public String getBanPlaceTxt() {
        return banPlaceTxt;
    }

    public List<String> getBanBlocksList() {
        return banBlocksList;
    }

    public boolean isAntiDispenserEnabled() {
        return antiDispenserEnabled;
    }

    public boolean isAntiLavaCast() {
        return antiLavaCast;
    }

    public int getLavaCastBlocks() {
        return lavaCastBlocks;
    }
}
