package me.Gi0vanniM.TheEarth.Listeners;

import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import me.Gi0vanniM.TheEarth.methods.Random;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import net.milkbowl.vault.Vault.VaultListener;

public class onKillDeath implements Listener {

	MySQLDoStuff sql = new MySQLDoStuff();
	PlayerMethods pmethods = new PlayerMethods();

	@EventHandler
	public void onKillDeaths(PlayerDeathEvent e) {

		if ((e.getEntity() instanceof Player)) {
			Player dead = e.getEntity();
			if (dead.getKiller() != null) {

				Player killer = dead.getKiller();

				sql.addDeath(dead);
				sql.addKill(killer);

				String dLand = pmethods.getPlayerLand(dead);
				String kLand = pmethods.getPlayerLand(killer);

				String dLandPr = pmethods.getLandPrefix(dLand);
				String kLandPr = pmethods.getLandPrefix(kLand);

				e.setDeathMessage(dLandPr + " §r" + dead.getName() + " §7is gedood door " + kLandPr + " §r"
						+ killer.getName() + "§7.");
			} else {
				e.setDeathMessage("");
			}
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		String land = new PlayerMethods().getPlayerLand(player);
		Location loc = new LandMethods().getLandSpawn(land).add(new Random().RandomMinMax(-5, 5),
				new Random().RandomMinMax(0, 2), new Random().RandomMinMax(-5, 5));

		e.setRespawnLocation(loc);
	}

}
