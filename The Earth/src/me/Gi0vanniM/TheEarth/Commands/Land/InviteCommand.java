package me.Gi0vanniM.TheEarth.Commands.Land;

import java.io.File;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.command.ConsoleCommandCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;

public class InviteCommand implements CommandExecutor {

	Methods methods = new Methods();
	MySQLDoStuff sql = new MySQLDoStuff();
	PlayerMethods pmethods = new PlayerMethods();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /invite [player] (land)
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (Main.plugin.playerCache.get(player).getRank().equalsIgnoreCase("koning")
					|| Main.plugin.playerCache.get(player).getRank().equalsIgnoreCase("President")
					|| sender.hasPermission("TheEarth.*") || sender.hasPermission("TheEarth.invite")) {

				if (cmd.getName().equalsIgnoreCase("uninvite")) {

					if (args.length == 0) {
						sender.sendMessage("§6/uninvite [player]");
						return false;
					}

					if (args.length == 1) {
						OfflinePlayer pp = Bukkit.getServer().getOfflinePlayer(args[0]);

						String kland = new MySQLDoStuff().getOfflinePlayerDataString(pp.getName(), "LAND");
						String sland = Main.plugin.playerCache.get((Player) sender).getLand();

						boolean checkland = kland.equals(sland);
//						Bukkit.broadcastMessage(kland + sland);

						if (checkland == false) {
							if (sender.hasPermission("TheEarth.*")) {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + args[0]);
								new MySQLDoStuff().setDataStringOffline(pp, "LAND", "LAND-KICKED");
								sender.sendMessage("§7Je hebt §c" + pp.getName() + "§7 geuninvite.");
								
								if (pp.isOnline() == true) {
									pp.getPlayer().kickPlayer("§4Je bent geuninvite.");
								}

							} else {
								sender.sendMessage("§cDeze speler zit niet in jouw land.");
								return false;
							}
						}
						if (checkland == true) {
//							sender.sendMessage("speler gekicked.");
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + args[0]);
							new MySQLDoStuff().setDataStringOffline(pp, "LAND", "LAND-KICKED");
							sender.sendMessage("§7Je hebt §c" + pp.getName() + "§7 uit je land gekicked.");

							if (pp.isOnline() == true) {
								pp.getPlayer().kickPlayer("§4Je bent geuninvite.");
							}
						}

					}

				}

				if (cmd.getName().equalsIgnoreCase("invite")) {

					if (args.length == 0) {
						sender.sendMessage("§6/invite [player] (land)");
						return false;
					}

					if (args.length == 1) {
						OfflinePlayer pp = Bukkit.getServer().getOfflinePlayer(args[0]);

						if (pp.hasPlayedBefore() || pp.isOnline() || new MySQLDoStuff().playerExistsString(args[0])) {
							sender.sendMessage("§cDeze speler is al eens geinvite, neem contact op met een staff-lid.");
							return false;
						}
						Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
						Team team = scoreboard.getPlayerTeam((Player) sender);
						String land = team.getName();

						boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

						if (check == true) {

							File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
							FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

							int maxsize = Main.plugin.getConfig().getInt("land.maxsize");
//							int size = team.getSize();
							int csize = cfg.getInt("size");

							if (csize < maxsize) {

								cfg.set("size", csize + 1);

								sql.invitePlayerData(args[0], land);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + args[0]);
								sender.sendMessage("");
							} else {
								sender.sendMessage("§cJe hebt teveel leden in je land.");

							}
						}
						return false;

					}
					if (args.length == 2) {
						if (sender.hasPermission("land.invite.other") || sender.hasPermission("TheEarth.*")
								|| sender.hasPermission("land.*")) {
							OfflinePlayer pp = Bukkit.getServer().getOfflinePlayer(args[0]);
							if (pp.hasPlayedBefore() || pp.isOnline()) {
								sender.sendMessage(
										"§cDeze speler is al eens geinvite, neem contact op met een staff-lid.");
								return false;
							}
							sql.invitePlayerData(args[0], args[1]);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + args[0]);
						} else {
							sender.sendMessage(ChatColor.RED + "Alleen staff kan iemand inviten naar een ander land.");
						}
					}

				}

//			if (sender instanceof ConsoleCommandSender) {
//
//			}
			}
		}

		else {
			sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit command.");
		}
		return false;
	}

}
