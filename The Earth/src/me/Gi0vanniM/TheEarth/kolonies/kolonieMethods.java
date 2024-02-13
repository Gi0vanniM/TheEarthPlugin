package me.Gi0vanniM.TheEarth.kolonies;

import java.io.File;
import java.util.List;
import java.util.Set;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.methods.Config;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class kolonieMethods {

	LandMethods landmethods = new LandMethods();
	Main plugin = Main.getPlugin(Main.class);

	public void cacheAllKolonies() {
		new BukkitRunnable() {

			@Override
			public void run() {
				File dir = new File("plugins/TheEarthPlugin/kolonie");
				File[] files = dir.listFiles();

				for (File file : files) {
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					String name = cfg.getString("naam");
					double uurloon = cfg.getDouble("uurloon");
					String land = cfg.getString("land");
					Location loc = new Location(Bukkit.getWorld("world"), cfg.getDouble("locatie.x"),
							cfg.getDouble("locatie.y"), cfg.getDouble("locatie.z"));
//					Bukkit.broadcastMessage(file.getName());
					Block block = loc.getBlock();

					kolonieClass ko = new kolonieClass(name, uurloon, land, block,
							block.getX() + "," + block.getY() + "," + block.getZ(), file.getName());
					plugin.kolonieCache.put(block, ko);
//					Bukkit.broadcastMessage(name + " put in map");
				}
			}
		}.runTaskLater(plugin, 80);

	}

	public void showAllCachedKolonies(CommandSender sender) {
		Set<Block> oof = plugin.kolonieCache.keySet();
		for (Block ooff : oof) {
//			sender.sendMessage(ooff);
			sender.sendMessage(plugin.kolonieCache.get(ooff).toString());
		}
	}

	public boolean isKolonie(Block block) {

		if (plugin.kolonieCache.containsKey(block)) {
			return true;
		}

		return false;
	}

	public String getKolonieFile(Block block) {
		String xyz = block.getX() + "," + block.getY() + "," + block.getZ();
		List<String> k = new Config().getFiles("plugins/TheEarthPlugin/kolonie", xyz);

		for (int i = 0; i < k.size(); i++) {
			if (new File("plugins/TheEarthPlugin/kolonie", k.get(i)).exists()) {
				return k.get(i);
			}
		}
		return null;
	}

	public String getKolonie(Block block) {
		String xyz = block.getX() + "," + block.getY() + "," + block.getZ();
		List<String> k = new Config().getFiles("plugins/TheEarthPlugin/kolonie", xyz);

		for (int i = 0; i < k.size(); i++) {
			if (new File("plugins/TheEarthPlugin/kolonie", k.get(i)).exists()) {
				File file = new File("plugins/TheEarthPlugin/kolonie", k.get(i));
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				return cfg.getString("naam");
			}
		}
		return null;
	}

	public void claimKolonie(String koloniefile, String land, Block block) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			File file = new File("plugins/TheEarthPlugin/kolonie", koloniefile);
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			File file2 = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg2 = YamlConfiguration.loadConfiguration(file2);

			int landkracht = cfg2.getInt("kracht");
			if (landkracht >= 250) {

				String kolonie = cfg.getString("naam");
				String currentLand = cfg.getString("land");

				Location loc = new Location(Bukkit.getWorld("world"), cfg.getDouble("locatie.x"),
						cfg.getDouble("locatie.y"), cfg.getDouble("locatie.z"));

				Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
				Team team = scoreboard.getTeam(land);

				new LandMethods().takeLandKracht(land, 250);
				landmethods.sendLandMessage(land,
						"§7Je land is nu de kolonie §a" + kolonie + "§7 aan het overnemen van " + currentLand
								+ ". Hiervoor zijn er 250 punten van de landkracht afgehaald.");
				landmethods.sendLandMessage(currentLand,
						"§7De kolonie §a" + kolonie + "§7 wordt aangevallen door " + land + ".");

				new BukkitRunnable() {
					int timer = 60;

					@Override
					public void run() {
						if (timer <= 0) {
							landmethods.sendLandMessage(land,
									"§7Het overnemen van de kolonie §a" + kolonie + "§7 is gelukt.");
							cfg.set("land", land);
							addKolonie(land, kolonie);
							removeKolonies(currentLand, kolonie);
							plugin.kolonieCache.get(block).setLand(land);
							Main.plugin.saveconf(cfg, file);
							cancel();
						}

						for (OfflinePlayer pl : team.getPlayers()) {
							if (pl instanceof Player) {
								Player players = pl.getPlayer();

								if (players.getLocation().distance(loc) > 10) {
									landmethods.sendLandMessage(land,
											"§7Helaas is het overnemen van de kolonie §a" + kolonie + "§7 mislukt.");
									cancel();
								}

							}
						}

						if (timer == 60 || timer == 50 || timer == 40 || timer == 30 || timer == 20
								|| timer <= 10 && timer > 0) {

							landmethods.sendLandMessage(land, "§7Het overnemen van de kolonie §a" + kolonie
									+ "§7 gaat nog §c" + timer + "§7 seconden duren.");
						}
						timer--;
					}
				}.runTaskTimer(Main.plugin, 0L, 20L);
				Main.plugin.saveconf(cfg, file);
				Main.plugin.saveconf(cfg2, file2);
			} 
		});

	}
	
	public boolean hasKracht(String land) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();
		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			
			int landkracht = cfg.getInt("kracht");
			if (landkracht >= 250) {
				return true;
			}

		}
		return false;
	}

	public boolean hasKolonie(String land, String kolonie) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();
		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			List<String> list = cfg.getStringList("kolonies");

			if (list.contains(kolonie)) {
				return true;
			}

		}

		return false;
	}

	public void addKolonie(String land, String kolonie) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			List<String> listerino = cfg.getStringList("kolonies");
			listerino.add(kolonie);
			cfg.set("kolonies", listerino);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public void removeKolonies(String land, String kolonie) {
		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			List<String> listerino = cfg.getStringList("kolonies");
			for (int i = 0; i < listerino.size(); i ++) {
				String k = listerino.get(i);
				if (k.equalsIgnoreCase(kolonie)) {
					listerino.remove(i);
				}
			}
			cfg.set("kolonies", listerino);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public void createKolonie(Player player, String naam) {
		Block block = player.getTargetBlock((Set<Material>) null, 5);
		String xyz = block.getX() + "," + block.getY() + "," + block.getZ();
		String xyzn = block.getX() + "," + block.getY() + "," + block.getZ() + " " + naam;

		boolean check = new File("plugins/TheEarthPlugin/kolonie", xyzn + ".yml").exists();

		if (check == false) {

			File file = new File("plugins/TheEarthPlugin/kolonie", xyzn + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			// naam
			cfg.set("naam", naam);

			// uurloon
			cfg.set("uurloon", 3000);

			// land
			cfg.set("land", "Onbekend");

			// locatie
			cfg.set("locatie.x", block.getX());
			cfg.set("locatie.y", block.getY());
			cfg.set("locatie.z", block.getZ());

			block.setType(Material.SPONGE);
			block.setData((byte) 1);

			Main.plugin.saveconf(cfg, file);

			kolonieClass ko = new kolonieClass(naam, 3000, "Onbekend", block, xyz, xyzn);
			plugin.kolonieCache.put(block, ko);

		} else {
			player.sendMessage(ChatColor.RED + "Deze Kolonie bestaat al.");
		}
	}

	public void setMoneyKolonie(Player player, String naam, double geld) {
		List<String> tk = new Config().getFiles2("plugins/TheEarthPlugin/kolonie", naam);

		for (int i = 0; i < tk.size(); i++) {
			File file = new File("plugins/TheEarthPlugin/kolonie", tk.get(i));
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("uurloon", geld);

			Main.plugin.saveconf(cfg, file);

			player.sendMessage("Kolonie uurloon van " + tk.get(i) + " gezet naar geld");
		}

	}

}
