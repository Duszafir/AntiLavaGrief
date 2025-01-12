package al.Duszafir.commands;

import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.nio.Buffer;

public class MainCommand implements CommandExecutor {

    private AntiLavaGrieff plugin;

    public MainCommand(AntiLavaGrieff plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (args[0].equalsIgnoreCase("author")){
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aThe Author is &6&lDuszafir"));
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            help(sender);
            return true;
        } else if (!sender.hasPermission("antilavagrief.commands.maincommand")){
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4You do not have permissions to run this command"));
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                AntiLavaGrieff.isEnabled = true;
                Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aLava protection has been activated."));
            } else if (args[0].equalsIgnoreCase("disable")) {
                AntiLavaGrieff.isEnabled = false;
                Bukkit.broadcastMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cLava protection has been disabled."));
            } else if (args[0].equalsIgnoreCase("version")){
                    sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aThe version is: &9"+ plugin.getDescription().getVersion()));
            } else {
                help(sender);
            }
        } else {
            help(sender);
        }

        return true;
    }

    public void help(CommandSender sender){
        sender.sendMessage(MessageUtils.getColoredMessage("&f----------COMMANDS "+AntiLavaGrieff.prefix+"&f----------"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al enable"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al disable"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al version"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al author"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7 /al help"));
        sender.sendMessage(MessageUtils.getColoredMessage("&f----------COMMANDS "+AntiLavaGrieff.prefix+"&f----------"));
    }
}