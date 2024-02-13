package me.Gi0vanniM.TheEarth.Commands.Land;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Gi0vanniM.TheEarth.kolonies.kolonieMethods;

public class KolonieCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("kolonie") || sender.hasPermission("TheEarth.*")
				|| sender.hasPermission("kolonie.*")) {
			if (cmd.getName().equalsIgnoreCase("kolonie")) {

			// @formatter:off
			String help = "§c[§4§lThe Earth§c]§r§6 \n"
						+ "Kolonie commands: \n"
						+ "/kolonie create [land naam]";
			// @formatter:on

				if (args.length == 0) {
					sender.sendMessage(help);
					return false;
				}
				
				// /kolonie list
				if (args[0].equalsIgnoreCase("list")) {
					
				}

				// /kolonie create [naam]
				if (sender.hasPermission("kolonie.create") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("kolonie.*")) {
					if (args[0].equalsIgnoreCase("create")) {
						if (args.length == 1) {
							sender.sendMessage("§6/kolonie create [naam]");
							return false;
						}
						StringBuilder sr = new StringBuilder();
						for (int i = 1; i < args.length; i++) {
							sr.append(args[i]).append(" ");
						}
						String s = sr.toString().replaceFirst(".$", "");
						new kolonieMethods().createKolonie((Player) sender, s);
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "U heeft geen toegang tot dit command.");
				}

				// /kolonie setuurloon [kolonie] [bedrag]
				if (sender.hasPermission("kolonie.setuurloon") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("kolonie.*")) {
					if (args[0].equalsIgnoreCase("setuurloon")) {
						if (args.length <= 2) {
							sender.sendMessage("§6/kolonie setuurloon [kolonie] [bedrag]");
							return false;
						}
						String kolonie = args[1];
						double bedrag = Double.parseDouble(args[2]);
						new kolonieMethods().setMoneyKolonie((Player) sender, kolonie, bedrag);
						sender.sendMessage("§7Uurloon van §6" + kolonie + "§7 aangepast naar §6" + bedrag + "§7.");
					}
				}

			}
		} else {
			sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit command.");
		}
		return false;
	}

}
