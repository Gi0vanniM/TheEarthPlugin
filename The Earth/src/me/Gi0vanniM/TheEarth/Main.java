package me.Gi0vanniM.TheEarth;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import me.Gi0vanniM.TheEarth.Features.Lift.LiftBlockListener;
import me.Gi0vanniM.TheEarth.Features.Lift.LiftPlayerListener;
import me.Gi0vanniM.TheEarth.Features.SignTP.TPBlockListener;
import me.Gi0vanniM.TheEarth.Features.SignTP.TPPlayerListener;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.Menu.StatsMenu;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.Gi0vanniM.TheEarth.Commands.AdminCommands;
import me.Gi0vanniM.TheEarth.Commands.CommandsExecutor;
import me.Gi0vanniM.TheEarth.Commands.debugCommands;
import me.Gi0vanniM.TheEarth.Commands.payCommand;
import me.Gi0vanniM.TheEarth.Commands.Land.InviteCommand;
import me.Gi0vanniM.TheEarth.Commands.Land.KolonieCommands;
import me.Gi0vanniM.TheEarth.Commands.Land.LandCommands;
import me.Gi0vanniM.TheEarth.Commands.Land.SetRank;
import me.Gi0vanniM.TheEarth.Commands.Land.landDisband;
import me.Gi0vanniM.TheEarth.Commands.Land.landrekeningCommands;
import me.Gi0vanniM.TheEarth.Listeners.Bow;
import me.Gi0vanniM.TheEarth.Listeners.ChatManager;
import me.Gi0vanniM.TheEarth.Listeners.CobwebDmg;
import me.Gi0vanniM.TheEarth.Listeners.JoinQuitEvent;
import me.Gi0vanniM.TheEarth.Listeners.SignLetters;
import me.Gi0vanniM.TheEarth.Listeners.SpawnTeleport;
import me.Gi0vanniM.TheEarth.Listeners.blockEvents;
import me.Gi0vanniM.TheEarth.Listeners.onKillDeath;
import me.Gi0vanniM.TheEarth.Listeners.openDoors;
import me.Gi0vanniM.TheEarth.Listeners.playerDamageEvent;
import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import me.Gi0vanniM.TheEarth.Players.playerClass;
import me.Gi0vanniM.TheEarth.kolonies.kolonieClass;
import me.Gi0vanniM.TheEarth.kolonies.kolonieListnener;
import me.Gi0vanniM.TheEarth.kolonies.kolonieMethods;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static Main instance;
	public static Connection connection;
	public static String host;
	public static String database;
	public static String username;
	public static String password;
	public static String table;
	public static int port;
	public Map<Player, playerClass> playerCache = new HashMap<>();
	public Map<Block, kolonieClass> kolonieCache = new HashMap<>();
	public List<Player> openDoors = new ArrayList<Player>();
	public List<Player> socialspy = new ArrayList<Player>();
	public static Economy economy = null;
	public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

	Methods methods = new Methods();
//	MySQLDoStuff sql = new MySQLDoStuff();
//	PlayerMethods pmethods = new PlayerMethods();

	public void onEnable() {

		if (!setupEconomy()) {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "NO VAULT");
			Log.info("{}{}{}{}{}{}{}NO VAULT");
			Bukkit.shutdown();
		}

		plugin = this;
		instance = this;

		loadConfig();
		new kolonieMethods().cacheAllKolonies();
		
		// LISTENERS
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new JoinQuitEvent(), this);
		Bukkit.getPluginManager().registerEvents(new onKillDeath(), this);
		Bukkit.getPluginManager().registerEvents(new StatsMenu(), this);
		Bukkit.getPluginManager().registerEvents(new SignLetters(), this);
		Bukkit.getPluginManager().registerEvents(new ChatManager(), this);
		Bukkit.getPluginManager().registerEvents(new Bow(), this);
		Bukkit.getPluginManager().registerEvents(new CobwebDmg(), this);
		Bukkit.getPluginManager().registerEvents(new playerDamageEvent(), this);
		Bukkit.getPluginManager().registerEvents(new blockEvents(), this);
		Bukkit.getPluginManager().registerEvents(new openDoors(), this);
		Bukkit.getPluginManager().registerEvents(new TPPlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new TPBlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new LiftBlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new LiftPlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpawnTeleport(), this);
		Bukkit.getPluginManager().registerEvents(new kolonieListnener(), this);
		// COMMANDS
		getCommand("theearth").setExecutor(new CommandsExecutor());
		getCommand("stats").setExecutor(new CommandsExecutor());
		getCommand("playtime").setExecutor(new CommandsExecutor());
		getCommand("land").setExecutor(new LandCommands());
		getCommand("kolonie").setExecutor(new KolonieCommands());
		getCommand("setrank").setExecutor(new SetRank());
		getCommand("setsubrank").setExecutor(new SetRank());
		getCommand("landdisban").setExecutor(new landDisband());
		getCommand("admin").setExecutor(new AdminCommands());
		getCommand("invite").setExecutor(new InviteCommand());
		getCommand("uninvite").setExecutor(new InviteCommand());
		getCommand("pay").setExecutor(new payCommand());
		getCommand("payland").setExecutor(new payCommand());
		getCommand("landrekening").setExecutor(new landrekeningCommands());
		getCommand("tedebug").setExecutor(new debugCommands());

		// MYSQL ASYNCHRONOUS
		BukkitRunnable r = new BukkitRunnable() {
			@Override
			public void run() {
				try {
					openConnection();
//					Statement statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		r.runTaskAsynchronously(this);

		// RECONNECT IF CONNECTION BROKEN
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						if (connection == null || connection.isClosed()) {
							return;
						}
						openConnection();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			}.runTaskTimer(plugin, 0L, 200L);
		});

		new BukkitRunnable() {
			@Override
			public void run() {
				new MySQLDoStuff().updateAllPlayerDatatoSQL();
			}
		}.runTaskTimer(plugin, 200L, 2400L);

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (playerCache.containsKey(player)) {
						new PlayerMethods().addLandKrachtPlayer(player, 1);
					}
				}
			}
		}.runTaskTimer(plugin, 1200L, 6000L);

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (playerCache.containsKey(player)) {
						new PlayerMethods().checkLoon(player);
						new SideBarScoreboard().setOrUpdateScoreboard(player);
					}
				}
			}
		}.runTaskTimer(plugin, 100L, 1000L);

	}

//	public void 
	public void onDisable() {
		new MySQLDoStuff().updateOnDisable();
		closeConnection();
		playerCache.clear();
	}

//	private void load() {
//		methods.sb = Bukkit.getScoreboardManager().getNewScoreboard();
//	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public static void openConnection() throws SQLException, ClassNotFoundException {
		host = Main.plugin.getConfig().getString("mysql.host");
		port = Main.plugin.getConfig().getInt("mysql.port");
		database = Main.plugin.getConfig().getString("mysql.database");
		username = Main.plugin.getConfig().getString("mysql.username");
		password = Main.plugin.getConfig().getString("mysql.password");
		table = Main.plugin.getConfig().getString("mysql.table");

		if (connection != null && !connection.isClosed()) {
			return;
		}

		try {
			if (connection != null && !connection.isClosed()) {
				return;
			}
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);

			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
			new PlayerMethods().putAllOnlinePlayersInMap();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void reconnect() throws SQLException, ClassNotFoundException {
		host = Main.plugin.getConfig().getString("mysql.host");
		port = Main.plugin.getConfig().getInt("mysql.port");
		database = Main.plugin.getConfig().getString("mysql.database");
		username = Main.plugin.getConfig().getString("mysql.username");
		password = Main.plugin.getConfig().getString("mysql.password");
		table = Main.plugin.getConfig().getString("mysql.table");

		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {

			try {
				if (connection != null && !connection.isClosed()) {
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					return;
				}
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
						password);

				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL RECONNECTED");

				new PlayerMethods().putAllOnlinePlayersInMap();
				new kolonieMethods().cacheAllKolonies();
				// TODO
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}

	public String moneyFormat(double geld) {
		DecimalFormat dff = new DecimalFormat("#,##0.00");
		dff.setMaximumIntegerDigits(9);
		dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		return dff.format(geld);
	}

	public static void closeConnection() {
//		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
		try {
			connection.close();
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "MYSQL DISCONNECTED");
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		});
	}

	public Connection getConnection() {
		return connection;
	}

	@SuppressWarnings("static-access")
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void saveconf(FileConfiguration cfg, File file) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface FindOneCallback {
		public void onQueryDone(boolean b);
	}

}
