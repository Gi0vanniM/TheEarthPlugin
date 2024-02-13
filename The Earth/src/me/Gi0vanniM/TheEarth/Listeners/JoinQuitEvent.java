package me.Gi0vanniM.TheEarth.Listeners;

import java.util.UUID;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import org.apache.logging.log4j.core.Filter.Result;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class JoinQuitEvent implements Listener {

	MySQLDoStuff sql = new MySQLDoStuff();
	Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();

//		Bukkit.broadcastMessage(plugin.table);

		boolean check = sql.playerExists(player);
		boolean check2 = sql.playerExistsString(player.getName());

		if (check == true) {
			// EXISTS
			e.setJoinMessage(ChatColor.GRAY + player.getName() + " joined.");
			Log.info(player.getName() + " joined.");
			sql.getPlayerDataOnJoin(player);

		} else if (check == false && check2 == true) {
			// NEW INVITED
			sql.setTnvitedPlayerData(player);
			e.setJoinMessage(ChatColor.GRAY + "Welcome " + player.getName() + " to The Earth.");
			sql.getPlayerData(player);

		} else {
			// NEW
			e.setJoinMessage(ChatColor.GRAY + "Welcome " + player.getName() + " to The Earth.");
			sql.createPlayerData(player);

		}

	}
	
	@EventHandler
	public void spawn(PlayerSpawnLocationEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()) {
			Location loc = new Location(player.getWorld(), 0, 3, 0);
			e.setSpawnLocation(loc);
			player.teleport(loc);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		sql.savePlayerData(player);
		e.setQuitMessage(ChatColor.GRAY + player.getName() + " left.");
		Log.info(player.getName() + " left.");

		new BukkitRunnable() {

			@Override
			public void run() {
				plugin.playerCache.remove(player);
			}
		}.runTaskLater(plugin, 2400L);
	}

}
