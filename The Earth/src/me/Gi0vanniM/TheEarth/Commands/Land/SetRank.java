package me.Gi0vanniM.TheEarth.Commands.Land;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;

public class SetRank implements CommandExecutor {

	Methods methods = new Methods();
	MySQLDoStuff sql = new MySQLDoStuff();
	PlayerMethods pmethods = new PlayerMethods();
	Main plugin = Main.getPlugin(Main.class);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /setrank [player] [rank]
		if (sender.hasPermission("rank.set") || sender.hasPermission("TheEarth.*") || sender.hasPermission("rank.*")
				|| sender.hasPermission("koning")
				|| Main.plugin.playerCache.get((Player) sender).getRank().equalsIgnoreCase("Koning")
				|| Main.plugin.playerCache.get((Player) sender).getRank().equalsIgnoreCase("President")) {
			if (cmd.getName().equalsIgnoreCase("setrank")) {
				if (args.length <= 1) {
					sender.sendMessage("§6/setrank [player] [rank (Officier/Generaal/none)]");
					return false;
				}

				if (args[1].equals("Officier") || args[1].equals("Generaal") || args[1].equals("none")) {
					OfflinePlayer tplayer = Bukkit.getServer().getPlayer(args[0]);

					if (Main.plugin.playerCache.get((Player) sender).getLand().equalsIgnoreCase(
							Main.plugin.playerCache.get(tplayer).getLand()) || sender.hasPermission("setrank.other")) {

						if (sender.getName() != tplayer.getPlayer().getName()) {

//						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (tplayer instanceof Player) {

								sql.setDataString(tplayer.getPlayer(), "RANK", args[1]);
								plugin.playerCache.get(tplayer.getPlayer()).setRank(args[1]);

							} else {
								sql.setDataStringOffline(tplayer, "RANK", args[1]);
							}
							return false;

						} else {
							if (!sender.hasPermission("TheEarth.*")) {
								sender.sendMessage(
										"§cJe kunt je eigen rang niet aanpassen, neem contact op met een staff-lid voor meer info.");
							}
						}
					} else {
						sender.sendMessage("§cJe kan alleen mensen uit je eigen land een rang geven.");
					}
				}

				// ALLEEN STAFF V V V V V V
				if (args[1].equals("Koning") || args[1].equals("President") || args[1].equals("Staff")
						|| args[1].equals("Officier") || args[1].equals("Generaal") || args[1].equals("none")) {
					if (sender.hasPermission("rank.set") || sender.hasPermission("TheEarth.*")
							|| sender.hasPermission("rank.*")) {
						OfflinePlayer tplayer = Bukkit.getServer().getPlayer(args[0]);

//						if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (tplayer instanceof Player) {

							sql.setDataString(tplayer.getPlayer(), "RANK", args[1]);
							plugin.playerCache.get(tplayer.getPlayer()).setRank(args[1]);
						} else {
							sql.setDataStringOffline(tplayer, "RANK", args[1]);
						}
						return false;
					} else {
						sender.sendMessage(ChatColor.RED + "Alleen staff kan iemand Koning of President maken.");
					}
				}
				sender.sendMessage("§6/setrank [player] [rank (Officier/Generaal)]");
			}

			// /setsubrank [player] [subrank]

			if (cmd.getName().equalsIgnoreCase("setsubrank")) {
				if (args.length <= 1) {
					sender.sendMessage("/setsubrank [player] [subrank]");
					return false;
				}
				StringBuilder sr = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sr.append(args[i]).append(" ");
				}
				String s = sr.toString().replaceFirst(".$", "");

				OfflinePlayer tplayer = Bukkit.getServer().getPlayer(args[0]);

				if (Main.plugin.playerCache.get((Player) sender).getLand().equalsIgnoreCase(
						Main.plugin.playerCache.get(tplayer).getLand()) || sender.hasPermission("setsubrank.other")) {

					if (tplayer instanceof Player) {

						sql.setDataString(tplayer.getPlayer(), "SUBRANK", s);
						plugin.playerCache.get(tplayer).setSubrank(s);

					} else {
						sql.setDataStringOffline(tplayer, "SUBRANK", args[1]);
					}
				} else {
					sender.sendMessage("§cJe kan alleen mensen uit je eigen land een subrang geven.");
				}

				return false;

			}
		} else {
			sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit command.");
		}
		return false;
	}

}
