package me.Gi0vanniM.TheEarth.Listeners;

import me.Gi0vanniM.TheEarth.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CobwebDmg implements Listener {
	
	Main plugin = Main.getPlugin(Main.class);
	
	@EventHandler
	public void cobweb(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location loc = player.getLocation();
		
		if (loc.getBlock().getType().equals(Material.WEB) || loc.add(0, 1, 0).getBlock().getType().equals(Material.WEB)) {
			player.damage(2);
		}
		
	}
	
}
