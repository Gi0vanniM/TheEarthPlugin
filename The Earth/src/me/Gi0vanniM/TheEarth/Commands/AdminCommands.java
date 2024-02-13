package me.Gi0vanniM.TheEarth.Commands;

import java.util.Collection;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;

public class AdminCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// @formatter:off
		String help = "§8[§4§lThe Earth Admin§8]§c \n"
				+ "Welkom TE Admin. \n" 
				+ "/admin help \n"
				+ "/admin oorlogsplit \n"
				+ "/admin opendoor \n"
				+ "/admin socialspy \n"
				+ "/admin disablechat \n"
				+ "/admin clearchat";
		// @formatter:on

		if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*") || sender.hasPermission("admin.*")
				|| sender.hasPermission("TheEarth.admin")) {
			if (cmd.getName().equalsIgnoreCase("admin")) {
				if (args.length == 0) {
					sender.sendMessage(help);
					return false;
				}
			}
			
			

			if (args[0].equalsIgnoreCase("oorlogsplit")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.oorlogsplit")) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						new PlayerMethods().randomTeam(p);
					}
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("fixlanddisban")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.oorlogsplit")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
						Team team = scoreboard.getPlayerTeam(player);
						new MySQLDoStuff().setDataStringOffline(player, "LAND", team.getName());
						new PlayerMethods().setLand(player, team.getName());
						

//						playerClass pl = new playerClass(id, name, ip, monet, kills, deaths, playtime, hours, land, rank,
//								subrank, joined, status);
					}
					return false;
				}
			}
			


			if (args[0].equalsIgnoreCase("opendoor")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.opendoor")) {
					Main.plugin.openDoors.add((Player) sender);
				}
			}

			if (args[0].equalsIgnoreCase("socialspy")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.socialspy")) {
					Player player = (Player) sender;
					if (Main.plugin.socialspy.contains(player)) {
						Main.plugin.socialspy.remove(player);
						sender.sendMessage("§cJe hebt socialspy uitgezet.");
					} else {
						Main.plugin.socialspy.add(player);
						sender.sendMessage("§cJe hebt socialspy aangezet.");
					}
				}
			}

			if (args[0].equalsIgnoreCase("uurloon")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.uurloon")) {
					new PlayerMethods().uurLoon((Player) sender);
				}
			}

			if (args[0].equalsIgnoreCase("disablechat")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.disablechat")) {
					boolean check = Main.plugin.getConfig().getBoolean("chat.disabled");
					if (check == false) {
						Main.plugin.getConfig().set("chat.disabled", true);
						Bukkit.broadcastMessage("§4De chat is nu uitgeschakeld.");
					} else {
						Main.plugin.getConfig().set("chat.disabled", false);
						Bukkit.broadcastMessage("§4De chat is weer bruikbaar.");
					}
				}
			}

			if (args[0].equalsIgnoreCase("clearchat")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.clearchat")) {
					Collection<? extends Player> arrayOfPlayer;
					int skkrt = (arrayOfPlayer = Bukkit.getOnlinePlayers()).size();
					for (int i = 0; i < skkrt; i++) {
						Player player1 = (Player) arrayOfPlayer.toArray()[i];

						for (int i1 = 0; i1 < 100; i1++) {
							player1.sendMessage(" ");
						}
					}
					Bukkit.broadcastMessage("§4De chat is gecleared.");
				}
			}

			if (args[0].equalsIgnoreCase("fixland")) {
				if (sender.hasPermission("admin") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("admin.*") || sender.hasPermission("TheEarth.admin.fixland")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (!Main.plugin.playerCache.containsKey(player)) {
							new MySQLDoStuff().getPlayerData(player);
							sender.sendMessage("fixed: " + player.getName());
						}
					}
				}
			}

			//
		}
		return false;
	}

}
