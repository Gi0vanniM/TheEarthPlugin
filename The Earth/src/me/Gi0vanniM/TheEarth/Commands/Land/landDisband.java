package me.Gi0vanniM.TheEarth.Commands.Land;

import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class landDisband implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("landdisban")) {
			if (sender.hasPermission("theearth.landdisban")) {
				if (args.length == 0) {

					return false;
				}

				String land = args[0];
				Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
				Team team = scoreboard.getTeam(land);

				for (OfflinePlayer pl : team.getPlayers()) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + pl.getName() + " land disban (" + team.getName() + ")");
					new MySQLDoStuff().setDataStringOffline(pl, "LAND", "LAND-DISBAN");
				}

			}
		}
		return false;
	}

}
