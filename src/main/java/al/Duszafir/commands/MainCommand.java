package al.Duszafir.commands;

import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        } else if (!sender.hasPermission("antilavagrief.commands.maincommand")){
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4You do not have permissions to run this command"));
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                AntiLavaGrieff.isEnabled = true;
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aLava protection has been activated."));
            } else if (args[0].equalsIgnoreCase("disable")) {
                AntiLavaGrieff.isEnabled = false;
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cLava protection has been disabled."));
            } else if (args[0].equalsIgnoreCase("version")){
                    sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aThe version is: &9"+ plugin.getDescription().getVersion()));
            } else {
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4Invalid option. Use 'enable', 'disable' or 'version'."));
            }
        } else {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4Invalid option. Use 'enable', 'disable' or 'version'."));
        }

        return true;
    }
}