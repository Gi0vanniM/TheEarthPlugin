package me.Gi0vanniM.TheEarth.Commands.Land;

import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import me.Gi0vanniM.TheEarth.methods.Methods;
import me.Gi0vanniM.TheEarth.methods.createLand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;

public class LandCommands implements CommandExecutor {

	Methods methods = new Methods();
	MySQLDoStuff sql = new MySQLDoStuff();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// @formatter:off
		String help = "§c[§4§lThe Earth§c]§r \n"
				+ "§6Land commands: \n"
				+ "/land help \n" 
				+ "/land create [land naam] [kleur] \n"
				+ "/land set [land naam] \n"
				+ "/land switch [player] [land] \n" 
				+ "/setrank [player] [rank (Officier/Generaal)]";
		
		String helpL = "§c[§4§lThe Earth§c]§r \n"
				+ "§6Land commands: \n"
				+ "/land \n"
				+ "/setrank [player] [rank (Officier/Generaal)] \n"
				+ "/setsubrank [player] [args] \n"
				+ "/invite [player] \n"
				+ "/uninvite [player] ";
		// @formatter:on

		if (sender.hasPermission("land") || sender.hasPermission("land.*") || sender.hasPermission("TheEarth.*")) {
			if (cmd.getName().equalsIgnoreCase("land")) {
				if (args.length == 0) {
					sender.sendMessage(help);
					return false;
				}
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(help);
					return false;
				}

				// /land create [land naam] [kleur]
				if (sender.hasPermission("land.create") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("land.*")) {
					if (args[0].equalsIgnoreCase("create")) {

						if (args.length <= 2) {
							sender.sendMessage("/land create [landnaam] [kleur code (&f)]");
							return false;
						}
						String land = args[1];
						String color = args[2];
//				String color = args[2];
						new createLand().createLand(land, color, (Player) sender);

						return false;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit command.");
				}

				// /land set [land]
				if (sender.hasPermission("land.set") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("land.*")) {
					if (args[0].equalsIgnoreCase("set")) {

						if (args.length <= 1) {
							sender.sendMessage("/land set [landnaam]");
							return false;
						}
						String land = args[1];
						new LandMethods().setLandLocatie((Player) sender, land);
					}
				}

				// /land invite [player] (land)
				if (sender.hasPermission("land.invite") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("land.*") || sender.hasPermission("koning")) {

					if (args[0].equalsIgnoreCase("invite")) {
						if (args.length == 1) {
							sender.sendMessage("/land invite [player] (land)");
							return false;
						}
						if (args.length == 2) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + args[1]);
							Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
							Team team = scoreboard.getPlayerTeam((Player) sender);
							String land = team.getName();
							sql.invitePlayerData(args[1], land);
							Bukkit.broadcastMessage(land);
							return false;
						}
						if (args.length == 3) {
							if (sender.hasPermission("land.invite.other") || sender.hasPermission("TheEarth.*")
									|| sender.hasPermission("land.*")) {
								sql.invitePlayerData(args[1], args[2]);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + args[1]);
							} else {
								sender.sendMessage(
										ChatColor.RED + "Alleen staff kan iemand inviten naar een ander land.");
							}
						}

					}
				} else {
					sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit command.");
				}

				// /land switch [player] [land]
				if (sender.hasPermission("land.switch") || sender.hasPermission("TheEarth.*")
						|| sender.hasPermission("land.*")) {
					if (args[0].equalsIgnoreCase("switch")) {
						if (args.length <= 2) {
							sender.sendMessage("/land switch [player] [land]");
							return false;
						}
						OfflinePlayer tplayer = Bukkit.getServer().getOfflinePlayer(args[1]);
						new PlayerMethods().landSwitch((Player) sender, tplayer, args[2]);
					}

				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("land")) {
			if (new PlayerMethods().isLeader((Player) sender)) {
				sender.sendMessage(helpL);
				return false;
			}
		}

		return false;
	}

}
