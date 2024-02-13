package me.Gi0vanniM.TheEarth.Players;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class PlayerMethods {

	MySQLDoStuff sql = new MySQLDoStuff();
	Main plugin = Main.getPlugin(Main.class);
	LandMethods landM = new LandMethods();

//	public void putDataInCache(Player player) {
//		
//	}

//	public void setPlayerColor(Player player, String name) {
////		String land = getPlayerLand(player);
////		String color = new PlayerMethods().getLandKleur(land);
////		
////		String name = color.replace('&', '§') + player.getName();
//		
////		player.setPlayerListName(name);
//		
////		for(Player pl : Bukkit.getOnlinePlayers()){
////            if(pl == player) continue;
////            //REMOVES THE PLAYER
////            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));
////            //CHANGES THE PLAYER'S GAME PROFILE
////            GameProfile gp = ((CraftPlayer)player).getProfile();
////            try {
////                Field nameField = GameProfile.class.getDeclaredField("name");
////                nameField.setAccessible(true);
////
////                Field modifiersField = Field.class.getDeclaredField("modifiers");
////                modifiersField.setAccessible(true);
////                modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
////
////                nameField.set(gp, name);
////            } catch (IllegalAccessException | NoSuchFieldException ex) {
////                throw new IllegalStateException(ex);
////            }
////            //ADDS THE PLAYER
////            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle()));
////            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
////            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)player).getHandle()));
////        }
//		
//	}
//	
//	public void setPlayerLandColor(Player player) {
//		String land = getPlayerLand(player);
//		String color = new PlayerMethods().getLandKleur(land);
//		String name = color.replace('&', '§') + player.getName();
//		
////		setPlayerColor(player, name);
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player " + player.getName().toLowerCase() + " prefix " + color.replace('§', '&'));
//	}
//	
//	public void updatePlayerLandColorAll() {
//		for (Player players : Bukkit.getOnlinePlayers()) {
//			setPlayerLandColor(players);
//		}
//	}

	public boolean isLead(Player player) {
		String rank = plugin.playerCache.get(player).getRank();
		if (rank.equalsIgnoreCase("Koning") 
				|| rank.equalsIgnoreCase("President") 
				|| rank.equalsIgnoreCase("Prins")
				|| rank.equalsIgnoreCase("Generaal") 
				|| rank.equalsIgnoreCase("Officier")) {
			return true;
		}
		return false;
	}

	public boolean isLeader(Player player) {
		String rank = plugin.playerCache.get(player).getRank();
		if (rank == "Koning" || rank == "President") {
			return true;
		}
		return false;
	}

	public void addLandKrachtPlayer(Player player, int i) {
		String land = getPlayerLand(player);

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			int landkracht = cfg.getInt("kracht");
			if (landkracht <= 999) {
				cfg.set("kracht", landkracht + i);

				Main.plugin.saveconf(cfg, file);
			}
		}
	}

	public void takeLandKrachtPlayer(Player player, int i) {
		String land = getPlayerLand(player);

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {
			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			int landkracht = cfg.getInt("kracht");
			cfg.set("kracht", landkracht - i);

			Main.plugin.saveconf(cfg, file);
		}
	}

	public void checkLoon(Player player) {
		int hours = plugin.playerCache.get(player).getHours();
		int time = player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 3600;

		if (hours < time) {
			uurLoon(player);
			plugin.playerCache.get(player).setHours(time);
			sql.setDataString(player, "HOURS", time);
		}

	}

	public void uurLoon(Player player) {
		String land = getPlayerLand(player);
		File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double uurloon = cfg.getDouble("uurloon");
		int taxper = cfg.getInt("belasting");

		double tax = uurloon / 100 * taxper;
		double aftertax = uurloon - tax;

		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));

		String money = df.format(uurloon);

		Main.economy.depositPlayer(player, aftertax);
		landM.addLandGeld(land, tax);

		player.sendMessage(ChatColor.GRAY + "Je hebt je inkomen van §c§ " + money + "§7 ontvangen,\nwaarvan §c" + taxper
				+ "%§7 belasting naar uw land is betaald.");
		new SideBarScoreboard().setOrUpdateScoreboard(player);

	}

	public void putAllOnlinePlayersInMap() { // IF SERVER OR PLUGIN IS RELOADED
		try {
			if (plugin.connection != null && !plugin.connection.isClosed()) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					sql.getPlayerData(player);
				}
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void landSwitch(CommandSender p, OfflinePlayer player, String land) {

		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			if (player instanceof Player) {
				UUID uuid = player.getUniqueId();
				try {
					PreparedStatement statement = plugin.getConnection()
							.prepareStatement("UPDATE " + plugin.table + " SET LAND=? WHERE UUID=?");
					statement.setString(1, land);
					statement.setString(2, uuid.toString());
					statement.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				plugin.playerCache.get(player).setLand(land);
			} else {
				try {
					String name = player.getName();
					PreparedStatement statement = plugin.getConnection()
							.prepareStatement("UPDATE " + plugin.table + " SET LAND=? WHERE NAME=?");
					statement.setString(1, land);
					statement.setString(2, name.toString());
					statement.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			Team team = scoreboard.getTeam(land);
			team.addPlayer(player);

			String color = getLandKleur(land);
			Bukkit.dispatchCommand(p, "nametagedit player " + player.getName() + " prefix " + color.replace('§', '&'));

			p.sendMessage(player.getName() + " geswitched naar " + land);
			if (player.isOnline()) {
				player.getPlayer().sendMessage("Je bent geswitched naar " + land);
			}
		});
	}

	public String getPlayerPrefix(Player player) {
		String landoo = getPlayerLand(player);
		String land = getLandPrefix(landoo);

		String rank = getPlayerRank(player);
		String subrank = getPlayerSubRank(player);

//		[LAND]_[RANK?]_[SUBRANK?]_
//		[LAND]_[RANK/SUBRANK]_
//		[LAND]_

		// LAND
		if (land.contains("none")) {
			land = "";
		}
		if (!land.contains("none")) {
			land = land + " ";
		}
		// RANK
		if (rank.contains("none")) {
			rank = "";
		}
		if (rank.contains("Officier") || rank.contains("Generaal")) {
//			rank = "§8[§2" + rank.replace(" ", "") + "§8]§r ";
			rank = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + rank + ChatColor.DARK_GRAY + "]" + ChatColor.RESET
					+ " ";
		}
		if (rank.contains("Koning") || rank.contains("Koningin") || rank.contains("President")
				|| rank.contains("Prins")) {
//			rank = "§8[§6" + rank.replace(" ", "") + "§8]§r ";
			rank = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + rank + ChatColor.DARK_GRAY + "]" + ChatColor.RESET
					+ " ";
		}

		if (rank.contains("Staff")) {
//			rank = "§8[§6" + rank.replace(" ", "") + "§8]§r ";
			rank = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + rank + ChatColor.DARK_GRAY + "]" + ChatColor.RESET
					+ " ";
		}

		// SUBRANK
		if (!subrank.contains("none")) {
			if (subrank.length() > 16) {
				subrank = subrank.substring(0, 15);
			}
//			subrank = "§8[§7" + subrank.replace(" ", "") + "§8]§r ";
			subrank = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + subrank + ChatColor.DARK_GRAY + "]" + ChatColor.RESET
					+ " ";
		}
		if (subrank.contains("none")) {
			subrank = "";
		}

		String fullprefix = land + rank + subrank;

		return fullprefix;
	}

	public String getPlayerLand(Player player) {
//		String land = sql.getPlayerDataString(player, "LAND");
		String land = plugin.playerCache.get(player).getLand();
		return land;
	}

	public String getPlayerRank(Player player) {
//		String rank = sql.getPlayerDataString(player, "RANK");
		String rank = plugin.playerCache.get(player).getRank();
		return rank;
	}

	public String getPlayerSubRank(Player player) {
//		String subrank = sql.getPlayerDataString(player, "SUBRANK");
		String subrank = plugin.playerCache.get(player).getSubrank();
		return subrank;
	}

	public String getLandPrefix(String land) {

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			String prefix = cfg.getString("prefix");

			return prefix;
		}

		return "none";
	}

	public String getLandKleur(String land) {

		boolean check = new File("plugins/TheEarthPlugin/landen", land + ".yml").exists();

		if (check == true) {

			File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			String color = cfg.getString("color");

			return color;
		}

		return null;
	}

	public ChatColor getPlayerChatColor(Player player) {
//		String rank = sql.getPlayerDataString(player, "RANK");
		String rank = plugin.playerCache.get(player).rank;
		String land = plugin.playerCache.get(player).land;

		if (rank.equalsIgnoreCase("Koning") || rank.equalsIgnoreCase("President") || rank.equalsIgnoreCase("Prins")
				|| rank.equalsIgnoreCase("Staff") || rank.equalsIgnoreCase("Officier")
				|| rank.equalsIgnoreCase("Generaal") || land.equalsIgnoreCase("Staff")
				|| land.equalsIgnoreCase("Rusland")) {
			return ChatColor.WHITE;
		}

		return ChatColor.GRAY;
	}

	public void randomTeam(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		Team rusland = scoreboard.getTeam("Rusland");
		Team usa = scoreboard.getTeam("USA");

		int ruslandsize = rusland.getSize();
		int usasize = usa.getSize();

		if (plugin.getConfig().getBoolean("simulatie") == true) {

//			if (rusland.hasPlayer(player) || usa.hasPlayer(player)) {
//				player.sendMessage("Je zat al in een team.");
//
//			} else {
			if (ruslandsize == usasize) {
				// join random
				Random r = new Random();
				if (r.nextBoolean() == true) {
					setLand(player, rusland.getName());
				} else {
					setLand(player, usa.getName());
				}
			}

			if (ruslandsize > usasize) {
				// join usa
				setLand(player, usa.getName());
			}

			if (ruslandsize < usasize) {
				// join rusland
				setLand(player, rusland.getName());
			}
			new SideBarScoreboard().setOrUpdateScoreboard(player);
//			}
		}

	}

	public void setLand(Player player, String land) {
		UUID uuid = player.getUniqueId();

		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("UPDATE " + plugin.table + " SET LAND=? WHERE UUID=?");
				statement.setString(1, land);
				statement.setString(2, uuid.toString());
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			plugin.playerCache.get(player).setLand(land);
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			Team team = scoreboard.getTeam(land);
			team.addPlayer(player);

			String color = new PlayerMethods().getLandKleur(land);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"nametagedit player " + player.getName() + " prefix " + color.replace('§', '&'));

//			player.sendMessage("Je bent " + land + " gejoined.");
//			new SideBarScoreboard().setOrUpdateScoreboard(player);
		});
	}

}
