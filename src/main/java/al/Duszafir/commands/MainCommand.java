package al.Duszafir.commands;

import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    private final AntiLavaGrieff plugin;
    private static boolean cooldownEnabled = true;

    public MainCommand(AntiLavaGrieff plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 0) {
            help(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("author")) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aThe Author is &6&lDuszafir"));
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            help(sender);
            return true;
        } else if (!sender.hasPermission("antilavagrief.commands.maincommand")) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4You do not have permissions to run this command"));
            return true;
        }

        if (args[0].equalsIgnoreCase("enable")) {
            AntiLavaGrieff.isEnabled = true;
            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aLava protection has been activated."));
        } else if (args[0].equalsIgnoreCase("disable")) {
            AntiLavaGrieff.isEnabled = false;
            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cLava protection has been disabled."));
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aThe version is: &9" + plugin.getDescription().getVersion()));
        } else if (args[0].equalsIgnoreCase("cooldown")) {
            handleCooldownCommand(sender, args);
        } else {
            help(sender);
        }

        return true;
    }

    private void handleCooldownCommand(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4You do not have permissions to use this command"));
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cUsage: /al cooldown <enable/disable>"));
            return;
        }

        if (args[1].equalsIgnoreCase("enable")) {
            cooldownEnabled = true;
            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aCooldown has been enabled."));
        } else if (args[1].equalsIgnoreCase("disable")) {
            cooldownEnabled = false;
            Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cCooldown has been disabled."));
        } else {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cInvalid option. Use /al cooldown <enable/disable>"));
        }
    }

    public static boolean isCooldownEnabled() {
        return cooldownEnabled;
    }

    public void help(CommandSender sender) {
        sender.sendMessage(MessageUtils.getColoredMessage("&f----------COMMANDS " + AntiLavaGrieff.prefix + "&f----------"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al enable"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al disable"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al version"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al author"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al help"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al cooldown <enable/disable>"));
        sender.sendMessage(MessageUtils.getColoredMessage("&f----------COMMANDS " + AntiLavaGrieff.prefix + "&f----------"));
    }
}
