package me.Gi0vanniM.TheEarth.methods;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Main;

public class createLand {

	Methods method = new Methods();

	public void createLand(String land, String color, Player player) {

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == false) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			// LAND NAAM
			cfg.set("land", land);

			// PREFIX
			cfg.set("prefix", "§8[" + color.replace("&", "§") + land + "§8]§r");

			// LAND REKENING
			cfg.set("landrekening", 0);

			// DISPLAY COLOR
			cfg.set("color", color);

			// UURLOON
			cfg.set("uurloon", 5000);

			// BELASTING
			cfg.set("belasting", 10);

			// KRACHT
			cfg.set("kracht", 0);

			// TIJD
			Long unix = Instant.now().getEpochSecond();
			cfg.set("unix", unix);

			// NEXUS
			cfg.createSection("nexus");
			cfg.set("nexus.health", 75);
			cfg.set("nexus.maxhealth", 75);
			cfg.set("nexus.locatie.x", 0);
			cfg.set("nexus.locatie.y", 0);
			cfg.set("nexus.locatie.z", 0);

			// SIZE
			cfg.set("size", 0);
			
			// PERMISSIONS
			List<String> list = new ArrayList();
			cfg.set("permissions", list);

			Main.plugin.saveconf(cfg, file);

			String name = land;
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			Team team = null;

			Main.economy.createBank(land, land);

			for (Team t : scoreboard.getTeams()) {
				if (t.getName().equals(name)) {
					team = t;
					break;
				}
			}
			if (team == null)
				team = scoreboard.registerNewTeam(name);

			team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
			team.setAllowFriendlyFire(false);
			ChatColor col = method.getColorfromCode(color);
			String colt = method.getMCcolorfromCode(color);
			Bukkit.broadcastMessage(col.toString());
//			team.setColor(col);
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option Rusland color " + col);
			/// scoreboard teams option Rusland color aqua
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option " + land + " color " + colt);

//			team.addPlayer(player);

			player.sendMessage(land + " aangemaakt als nieuw land.");

		} else {
			player.sendMessage(ChatColor.RED + "Dit land bestaat al.");
		}

	}
}
