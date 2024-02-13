package me.Gi0vanniM.TheEarth.methods;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;

public class LandMethods {

	Main plugin = Main.getPlugin(Main.class);
	MySQLDoStuff sql = new MySQLDoStuff();

	public void sendLandMessage(String land, String message) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		if (scoreboard.getTeam(land) != null) {
			
			Team team = scoreboard.getTeam(land);

			for (OfflinePlayer pl : team.getPlayers()) {
				if (pl.isOnline()) {
					Player pland = pl.getPlayer();
					pland.sendMessage(message);
				}
			}
		}
	}

	public void addLandKracht(String land, int i) {

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			int landkracht = cfg.getInt("kracht");
			cfg.set("kracht", landkracht + i);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public void takeLandKracht(String land, int i) {

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			int landkracht = cfg.getInt("kracht") - i;
			cfg.set("kracht", landkracht);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public void addLandGeld(String land, double bedrag) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			double huidigBedrag = cfg.getDouble("landrekening");

			cfg.set("landrekening", huidigBedrag + bedrag);

			Main.plugin.saveconf(cfg, file);

		}

	}

	public void takeLandGeld(String land, double bedrag) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			double huidigBedrag = cfg.getDouble("landrekening");

			cfg.set("landrekening", huidigBedrag - bedrag);

			Main.plugin.saveconf(cfg, file);

		}

	}

	public void setLandbelasting(String land, int procent) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			if (procent <= 50) {

				cfg.set("belasting", procent);

				Main.plugin.saveconf(cfg, file);
			}

		}

	}

	public double getLandrekening(String land) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			double bedrag = cfg.getDouble("landrekening");
			return bedrag;
		}
		return 0;
	}

	public Player[] getOnlinePlayersfromLand(String land) {

		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = scoreboard.getTeam(land);
//		Team team = scoreboard.ge
		return null;
	}
	
	public void updateLandSidebar(String land) {
		for ( Player players : getOnlinePlayersfromLand(land)) {
			new SideBarScoreboard().setOrUpdateScoreboard(players);
		}
		
	}

	public void setLandLocatie(Player player, String land) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			Block block = player.getTargetBlock((Set<Material>) null, 5);

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("nexus.locatie.x", block.getX());
			cfg.set("nexus.locatie.y", block.getY());
			cfg.set("nexus.locatie.z", block.getZ());

			block.setType(Material.BEACON);
			block.setData((byte) 1);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public Location getLandSpawn(String land) {

		File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		double x = cfg.getDouble("nexus.locatie.x");
		double y = cfg.getDouble("nexus.locatie.y");
		double z = cfg.getDouble("nexus.locatie.z");

		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);

		return loc;
	}

}
