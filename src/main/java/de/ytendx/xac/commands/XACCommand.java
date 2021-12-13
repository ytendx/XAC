package de.ytendx.xac.commands;

import de.ytendx.xac.XACMain;
import de.ytendx.xac.notify.type.CheckType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class XACCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!commandSender.hasPermission("xac.command")){
            commandSender.sendMessage(XACMain.PREFIX + "§c§lXAntiCheat §f" + XACMain.VERSION + " by §eytendx");
            commandSender.sendMessage(XACMain.PREFIX + "§7This is a AntiCheat wich only uses Bukkit events to detect cheats.");
            commandSender.sendMessage(XACMain.PREFIX + "§7It is very experimental and not recommended to use for production enviroments.");
            commandSender.sendMessage(XACMain.PREFIX + "§7Download: §ehttps://www.spigotmc.org/resources/xanticheat.98334/");
            commandSender.sendMessage(XACMain.PREFIX + "§fCurrent checks: §e" + CheckType.values().length);
        }else if(strings.length != 1){
            commandSender.sendMessage(XACMain.PREFIX + "§c§lXAntiCheat §f" + XACMain.VERSION + " by §eytendx");
            commandSender.sendMessage(XACMain.PREFIX + "§f/xac silent §8- §eToggles silent mode");
            commandSender.sendMessage(XACMain.PREFIX + "§f/xac config §8- §eShows the config");
        }else{
            if(strings[0].equalsIgnoreCase("silent")){
                XACMain.getInstance().getXACConfig().setSilent(!XACMain.getInstance().isSilent());
                commandSender.sendMessage(XACMain.PREFIX + "§7The Anti-Cheat detection is now " + (XACMain.getInstance().isSilent() ? "§asilent" : "§cnormal") + "§7.");
            }else if(strings[0].equalsIgnoreCase("config")){
                commandSender.sendMessage(XACMain.PREFIX + "§eConfig: §f" + XACMain.getInstance().getXACConfig().toString().replaceAll("§", "&"));
            }else{
                commandSender.sendMessage(XACMain.PREFIX + "§c§lXAntiCheat §f" + XACMain.VERSION + " by §eytendx");
                commandSender.sendMessage(XACMain.PREFIX + "§f/xac silent §8- §eToggles silent mode");
                commandSender.sendMessage(XACMain.PREFIX + "§f/xac config §8- §eShows the config");
            }
        }

        return false;
    }
}
