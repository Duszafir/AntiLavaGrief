package al.Duszafir.commands;

import al.Duszafir.AntiLavaGrieff;
import al.Duszafir.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!sender.hasPermission("antilavagrief.commands.maincommand")) {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4No tienes permisos para ejecutar este comando"));
            return true;
        }

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                AntiLavaGrieff.isEnabled = true;
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&aLa protección contra lava ha sido activada."));
            } else if (args[0].equalsIgnoreCase("disable")) {
                AntiLavaGrieff.isEnabled = false;
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&cLa protección contra lava ha sido desactivada."));
            } else {
                sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4Opción inválida. Usa 'enable' o 'disable'."));
            }
        } else {
            sender.sendMessage(MessageUtils.getColoredMessage(AntiLavaGrieff.prefix + "&4Opción inválida. Usa 'enable' o 'disable'."));
        }

        return true;
    }
}
