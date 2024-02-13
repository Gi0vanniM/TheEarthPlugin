package me.Gi0vanniM.TheEarth.Listeners;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnTeleport implements Listener {

	@EventHandler
	public void onSpawnLoc(PlayerMoveEvent e) {
		if (Main.plugin.playerCache.containsKey(e.getPlayer())) {
			if (Main.plugin.playerCache.get(e.getPlayer()).getLand() != "none") {
				
				Location teleporter = new Location(e.getPlayer().getWorld(), 0.5, 3, -18.5);
				Location loc = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(),
						e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
				
				if (e.getPlayer().getLocation().distance(teleporter) < 1) {

					Location landspawn = new LandMethods()
							.getLandSpawn(Main.plugin.playerCache.get(e.getPlayer()).getLand());
					e.getPlayer().teleport(landspawn);
				}
			}
		}
	}

}
