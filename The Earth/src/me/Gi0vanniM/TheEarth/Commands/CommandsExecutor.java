package me.Gi0vanniM.TheEarth.Commands;

import java.util.ArrayList;
import java.util.List;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.Menu.StatsMenu;
import me.Gi0vanniM.TheEarth.methods.Time.getPlayerPlayTime;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import net.md_5.bungee.api.ChatColor;

public class CommandsExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// @formatter:off
		String help = "§c[§4§lThe Earth§c]§r§7 \n"
				+ "Welkom bij The Earth! \n" 
				+ "Plugin versie: §c" + Main.plugin.getDescription().getVersion() + "§7 \n"
				+ "Plugin gemaakt door: §c" + "GiovanniM" + "§7.";
		// @formatter:on

		if (cmd.getName().equalsIgnoreCase("landlist")) {
			if (sender instanceof Player) {
				if (args.length <= 0) {
					Player player = (Player) sender;
					String land = new PlayerMethods().getPlayerLand(player);
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
					Team team = scoreboard.getTeam(land);

					List<String> playerlist = new ArrayList<String>();
					for (OfflinePlayer p : team.getPlayers()) {
						
					}
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("TheEarth")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage(help);
				return false;
			}
			if (args[0].equalsIgnoreCase("scoreboard")) {
				new SideBarScoreboard().setScoreboard((Player) sender);
				return false;
			}
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(help);
				return false;
			}
//			if (args[0].equalsIgnoreCase("test")) {
//				new Bow().sendAnimation(player);
//			}

		}

		if (cmd.getName().equalsIgnoreCase("stats")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage("Hier uw stats.");
				new StatsMenu().openStatMenu(player, player);
				return false;
			}

			OfflinePlayer pTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
			sender.sendMessage(pTarget.getName() + "'s stats.");
			new StatsMenu().openStatMenu((Player) pTarget, player);
			return false;

		}

		if (cmd.getName().equalsIgnoreCase("playtime")) {
//			Player player = (Player) sender;
			if (args.length == 0) {
				String time = new getPlayerPlayTime().getPlayTime((Player) sender);
				sender.sendMessage(ChatColor.GRAY + "Your current playtime is " + time);
				return false;
			}

			Player pTarget = Bukkit.getServer().getPlayer(args[0]);
			if (pTarget instanceof Player) {
				String time = new getPlayerPlayTime().getPlayTime(pTarget);
				sender.sendMessage(
						ChatColor.WHITE + pTarget.getName() + ChatColor.GRAY + "'s current playtime is " + time);
				return false;
			}
		}

		return false;
	}

}
