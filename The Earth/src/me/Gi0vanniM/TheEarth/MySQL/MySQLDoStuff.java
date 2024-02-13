
package me.Gi0vanniM.TheEarth.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import me.Gi0vanniM.TheEarth.Players.playerClass;
import me.Gi0vanniM.TheEarth.Players.playerPermissions;
import net.md_5.bungee.api.ChatColor;

public class MySQLDoStuff {

	Main plugin = Main.getPlugin(Main.class);

	public void Update(final String qry) {
		recon();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try {
				PreparedStatement stnt = plugin.getConnection().prepareStatement(qry);
				stnt.executeUpdate(qry);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

	}

	public void updateOnDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Main.plugin.playerCache.containsKey(player)) {
				playerClass pl = plugin.playerCache.get(player);
				int kills = pl.getKills();
				int deaths = pl.getDeaths();
				int playtime = player.getStatistic(Statistic.PLAY_ONE_TICK);
//			int hours = player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 3600;
				String land = pl.getLand();
				String rank = pl.getRank();
				String subrank = pl.getSubrank();
				String status = pl.getStatus();

				try {
					PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table
							+ " SET KILLS=?, DEATHS=?, PLAYTIME=?, LAND=?, RANK=?, SUBRANK=?, STATUS=?, NAME=? WHERE UUID=?");
					statement.setInt(1, kills);
					statement.setInt(2, deaths);
					statement.setInt(3, playtime);
//				statement.setInt(4, hours);
					statement.setString(4, land);
					statement.setString(5, rank);
					statement.setString(6, subrank);
					statement.setString(7, status);
					statement.setString(8, player.getName().toLowerCase());

					statement.setString(9, player.getUniqueId().toString());
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (Bukkit.getOnlinePlayers().size() >= 1) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SAVED PLAYER DATA TO SQL");
		}
	}

	public void updateAllPlayerDatatoSQL() {
		recon();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Main.plugin.playerCache.containsKey(player)) {
					playerClass pl = plugin.playerCache.get(player);
					int kills = pl.getKills();
					int deaths = pl.getDeaths();
					int playtime = player.getStatistic(Statistic.PLAY_ONE_TICK);
//				int hours = player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 3600;
					String land = pl.getLand();
					String rank = pl.getRank();
					String subrank = pl.getSubrank();
					String status = pl.getStatus();

					try {
						PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table
								+ " SET KILLS=?, DEATHS=?, PLAYTIME=?, LAND=?, RANK=?, SUBRANK=?, STATUS=?, NAME=? WHERE UUID=?");
						statement.setInt(1, kills);
						statement.setInt(2, deaths);
						statement.setInt(3, playtime);
//					statement.setInt(4, hours);
						statement.setString(4, land);
						statement.setString(5, rank);
						statement.setString(6, subrank);
						statement.setString(7, status);
						statement.setString(8, player.getName().toLowerCase());

						statement.setString(9, player.getUniqueId().toString());
						statement.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (Bukkit.getOnlinePlayers().size() >= 1) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "SAVED PLAYER DATA TO SQL");
			}
		});
	}

	public void savePlayerData(Player player) {
		recon();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			if (Main.plugin.playerCache.containsKey(player)) {
				playerClass pl = plugin.playerCache.get(player);
				int kills = pl.getKills();
				int deaths = pl.getDeaths();
				int playtime = player.getStatistic(Statistic.PLAY_ONE_TICK);
//			int hours = player.getStatistic(Statistic.PLAY_ONE_TICK) / 20 / 3600;
				String land = pl.getLand();
				String rank = pl.getRank();
				String subrank = pl.getSubrank();
				String status = pl.getStatus();

				try {
					PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.table
							+ " SET KILLS=?, DEATHS=?, PLAYTIME=?, LAND=?, RANK=?, SUBRANK=?, STATUS=?, NAME=? WHERE UUID=?");
					statement.setInt(1, kills);
					statement.setInt(2, deaths);
					statement.setInt(3, playtime);
//				statement.setInt(4, hours);
					statement.setString(4, land);
					statement.setString(5, rank);
					statement.setString(6, subrank);
					statement.setString(7, status);
					statement.setString(8, player.getName().toLowerCase());

					statement.setString(9, player.getUniqueId().toString());
					statement.executeUpdate();
					Bukkit.getConsoleSender().sendMessage(
							ChatColor.GREEN + "SAVED " + player.getName().toLowerCase() + "'s PLAYER DATA TO SQL");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addKill(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				// UPDATE test_table SET KILLS = KILLS + 1 WHERE
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("UPDATE " + plugin.table + " SET KILLS = KILLS + 1 WHERE UUID=?");
				statement.setString(1, uuid.toString());
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		playerClass plo = plugin.playerCache.get(player);
		plo.setKills(plo.getKills() + 1);
	}

	public void addDeath(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				// UPDATE test_table SET KILLS = KILLS + 1 WHERE
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("UPDATE " + plugin.table + " SET DEATHS = DEATHS + 1 WHERE UUID=?");
				statement.setString(1, uuid.toString());
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		playerClass plo = plugin.playerCache.get(player);
		plo.setDeaths(plo.getDeaths() + 1);
	}

	public void createPlayerData(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
				statement.setString(1, uuid.toString());
				ResultSet result = statement.executeQuery();
				result.next();
				if (playerExists(player) != true) {
					PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.table
							+ "(UUID, NAME, IP, MONEY, KILLS, DEATHS, PLAYTIME, RANK, SUBRANK, JOINED, STATUS) VALUE (?,?,?,?,?,?,?,?,?,?,?)");
					insert.setString(1, uuid.toString()); // UUID
					insert.setString(2, player.getName().toLowerCase()); // NAME
					insert.setString(3, player.getAddress().getHostName()); // IP
					insert.setDouble(4, 5000); // MONEY
					insert.setInt(5, 0); // KILLS
					insert.setInt(6, 0); // DEATHS
					insert.setInt(7, 0); // PLAYTIME
//					insert.setString(8, "none"); // LAND
					insert.setString(8, "none"); // RANK
					insert.setString(9, "none"); // SUBRANK
					Long unixTime = Instant.now().getEpochSecond();
					insert.setLong(10, unixTime); // JOINED IN UNIX
					insert.setString(11, "none"); // STATUS
					insert.executeUpdate();

					playerClass pl = new playerClass(uuid.toString(), player.getName().toLowerCase(),
							player.getAddress().getHostName(), 5000.0, 0, 0, 0, 0, "none", "none", "none", unixTime,
							"none");
					plugin.playerCache.put(player, pl);
					new playerPermissions().setupPermissions(player);
//					new JoinQuitEvent().randomTeam(player);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		new BukkitRunnable() {
			@Override
			public void run() {
				new SideBarScoreboard().setOrUpdateScoreboard(player);
			}
		}.runTaskLater(plugin, 20L);
	}

	public void invitePlayerData(String player, String land) {
		recon();
//		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
				statement.setString(1, player);
				ResultSet result = statement.executeQuery();
				result.next();
				if (playerExistsString(player) != true) {
					PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.table
							+ "(UUID, NAME, LAND, RANK, SUBRANK, STATUS) VALUE (?,?,?,?,?,?)");
					insert.setString(1, "none"); // UUID
					insert.setString(2, player.toLowerCase()); // NAME
//					insert.setString(3, "0"); // IP
//					insert.setInt(4, 5000); // MONEY
//					insert.setInt(4, 0); // KILLS
//					insert.setInt(5, 0); // DEATHS
//					insert.setInt(6, 0); // PLAYTIME
					insert.setString(3, land); // LAND
					insert.setString(4, "none"); // RANK
					insert.setString(5, "none"); // SUBRANK
//					Long unixTime = Instant.now().getEpochSecond();
//					insert.setLong(10, unixTime); // JOINED IN UNIX
					insert.setString(6, "none"); // STATUS
					insert.executeUpdate();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void setTnvitedPlayerData(Player player) {
		recon();
		String pName = player.getName().toLowerCase();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
				statement.setString(1, pName);
				ResultSet result = statement.executeQuery();
				result.next();
//			if (playerExistsString(pName) != true) {
				PreparedStatement insert = plugin.getConnection().prepareStatement("UPDATE " + plugin.table
						+ " SET UUID=?, NAME=?, IP=?, MONEY=?, KILLS=?, DEATHS=?, PLAYTIME=?, RANK=?, SUBRANK=?, JOINED=?, STATUS=? WHERE NAME=?");
				insert.setString(1, uuid.toString()); // UUID
				insert.setString(2, player.getName().toLowerCase()); // NAME
				insert.setString(3, player.getAddress().getHostName()); // IP
				insert.setInt(4, 5000); // MONEY
				insert.setInt(5, 0); // KILLS
				insert.setInt(6, 0); // DEATHS
				insert.setInt(7, 0); // PLAYTIME
//			insert.setString(8, "none"); // LAND
				insert.setString(8, "none"); // RANK
				insert.setString(9, "none"); // SUBRANK
				Long unixTime = Instant.now().getEpochSecond();
				insert.setLong(10, unixTime); // JOINED IN UNIX
				insert.setString(11, "none"); // STATUS
				insert.setString(12, pName);
				insert.executeUpdate();

				getPlayerData(player);
//			}
				/*
				 * UPDATE `test_table` SET `UUID`=[value-1], `NAME`=[value-2], `IP`=[value-3],
				 * `MONEY`=[value-4], `KILLS`=[value-5], `DEATHS`=[value-6],
				 * `PLAYTIME`=[value-7], `LAND`=[value-8], `RANK`=[value-9],
				 * `SUBRANK`=[value-10], `JOINED`=[value-11], `STATUS`=[value-12] WHERE NAME=?
				 */

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String land = getPlayerDataString(player, "LAND");
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			Team team = scoreboard.getTeam(land);
			team.addPlayer(player);
			String color = new PlayerMethods().getLandKleur(land);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"nametagedit player " + player.getName().toLowerCase() + " prefix " + color.replace('§', '&'));
//			new PlayerMethods().updatePlayerLandColorAll();

			player.sendMessage("§3Je bent automatisch in " + color.replace('&', '§') + " " + land + "§3 gezet.");

//			new PlayerMethods().randomTeam(player);
		});
	}

	public void getPlayerData(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
				statement.setString(1, uuid.toString());

				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String id = result.getString("UUID");
					String name = result.getString("NAME");
					String ip = result.getString("IP");
					Double monet = result.getDouble("MONEY");
					int kills = result.getInt("KILLS");
					int deaths = result.getInt("DEATHS");
					int playtime = result.getInt("PLAYTIME");
					int hours = result.getInt("HOURS");
					String land = result.getString("LAND");
					String rank = result.getString("RANK");
					String subrank = result.getString("SUBRANK");
					Long joined = result.getLong("JOINED");
					String status = result.getString("STATUS");
					String s = " ";

//					Main.plugin.playerCache.put(player, new playerClass(id, name, ip, monet, kills, deaths, playtime,
//							land, rank, subrank, joined, status));
					playerClass pl = new playerClass(id, name, ip, monet, kills, deaths, playtime, hours, land, rank,
							subrank, joined, status);
					plugin.playerCache.put(player, pl);

					Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
					Team team = scoreboard.getTeam(land);
					team.addPlayer(player);

					String color = new PlayerMethods().getLandKleur(land);
//					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player " + player.getName().toLowerCase() + " prefix " + color.replace('§', '&'));
//					new PlayerMethods().updatePlayerLandColorAll();
					
					new PlayerMethods().setLand(player, land);
					new playerPermissions().setupPermissions(player);

//					new JoinQuitEvent().randomTeam(player);
//					playerClass plo = plugin.playerCache.get(player);
//					plo.setRank(subrank);
//				new playa(uuid, name, ip, money, kills, deaths, playtime, land, rank, subrank, joined, status).put
//				return id + s + name + s + ip + s + monet + s + kills + s + deaths + s + playtime + s + land + s + rank
//						+ joined + s + status;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

//		return null;
		});
		new BukkitRunnable() {
			@Override
			public void run() {
				new SideBarScoreboard().setOrUpdateScoreboard(player);
			}
		}.runTaskLater(plugin, 20L);
	}

	public void getPlayerDataOnJoin(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try {
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
				statement.setString(1, uuid.toString());

				ResultSet result = statement.executeQuery();
				if (result.next()) {
					String id = result.getString("UUID");
					String name = result.getString("NAME");
					String ip = result.getString("IP");
					Double monet = result.getDouble("MONEY");
					int kills = result.getInt("KILLS");
					int deaths = result.getInt("DEATHS");
					int playtime = result.getInt("PLAYTIME");
					int hours = result.getInt("HOURS");
					String land = result.getString("LAND");
					String rank = result.getString("RANK");
					String subrank = result.getString("SUBRANK");
					Long joined = result.getLong("JOINED");
					String status = result.getString("STATUS");
					String s = " ";

					playerClass pl = new playerClass(id, name, ip, monet, kills, deaths, playtime, hours, land, rank,
							subrank, joined, status);
					plugin.playerCache.put(player, pl);

//					Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
//					Team team = scoreboard.getTeam(land);
//					team.addPlayer(player);

					String color = new PlayerMethods().getLandKleur(land);
//					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player " + player.getName().toLowerCase() + " prefix " + color.replace('§', '&'));
//					new PlayerMethods().updatePlayerLandColorAll();

					new PlayerMethods().setLand(player, land);
					new playerPermissions().setupPermissions(player);
//					new JoinQuitEvent().randomTeam(player);
//					new SideBarScoreboard().setOrUpdateScoreboard(player);
					
					Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
					Team team = scoreboard.getPlayerTeam(player);
					new MySQLDoStuff().setDataStringOffline(player, "LAND", team.getName());
					new PlayerMethods().setLand(player, team.getName());

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

//		return null;
		});

		new BukkitRunnable() {
			@Override
			public void run() {
				new SideBarScoreboard().setOrUpdateScoreboard(player);
			}
		}.runTaskLater(plugin, 20L);

	}

	public String getPlayerDataString(Player player, String cate) {
		recon();
		UUID uuid = player.getUniqueId();
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet result = statement.executeQuery();
//			if (result.next()) {
			result.next();
			String y = result.getString(cate);
			return y;
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getOfflinePlayerDataString(String player, String cate) {
		recon();
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
			statement.setString(1, player.toString().toLowerCase());
			ResultSet result = statement.executeQuery();
//			if (result.next()) {
			result.next();
			String y = result.getString(cate);
			return y;
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public double getPlayerDataNum(Player player, String cate) {
		recon();
		UUID uuid = player.getUniqueId();
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet result = statement.executeQuery();
//			if (result.next()) {
			result.next();
			double y = result.getInt(cate);
			return y;
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void setDataString(Player player, String column, Object input) {
		recon();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			UUID uuid = player.getUniqueId();
			try {
				// UPDATE test_table SET KILLS = KILLS + 1 WHERE
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("UPDATE " + plugin.table + " SET " + column + "=? WHERE UUID=?");
				statement.setObject(1, input);
				statement.setString(2, uuid.toString());
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void setDataStringOffline(OfflinePlayer player, String column, Object input) {
		recon();
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			try {
				// UPDATE test_table SET KILLS = KILLS + 1 WHERE
				PreparedStatement statement = plugin.getConnection()
						.prepareStatement("UPDATE " + plugin.table + " SET " + column + "=? WHERE NAME=?");
				statement.setObject(1, input);
				statement.setString(2, player.getName().toLowerCase().toString());
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public boolean playerExists(Player player) {
		recon();
		UUID uuid = player.getUniqueId();
		try {
//			Bukkit.broadcastMessage(plugin.table);
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
//				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
				Log.info(player.getName().toLowerCase() + " found in database.");
				return true;
			}
//			plugin.getServer().broadcastMessage(ChatColor.RED + "Player NOT Found");
			Log.info(player.getName().toLowerCase() + " not found in database.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean playerExistsString(String player) {
		recon();
//		UUID uuid = player.getUniqueId();
		try {
//			Bukkit.broadcastMessage(plugin.table);
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
			statement.setString(1, player.toLowerCase());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
//				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
				Log.info(player + " found in database.");
				return true;
			}
//			plugin.getServer().broadcastMessage(ChatColor.RED + "Player NOT Found");
			Log.info(player + " not found in database.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void recon() {
		try {
			plugin.reconnect();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
