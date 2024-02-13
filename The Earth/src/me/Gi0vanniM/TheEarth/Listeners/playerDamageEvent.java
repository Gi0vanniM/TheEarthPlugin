package me.Gi0vanniM.TheEarth.Listeners;

import me.Gi0vanniM.TheEarth.Main;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.minecraft.server.v1_12_R1.EntityBoat;

public class playerDamageEvent implements Listener {

	Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {

		if (e.getCause() == DamageCause.PROJECTILE) {
			if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
				Player damager = (Player) ((Projectile) e.getDamager()).getShooter();
				Player player = (Player) e.getEntity();
				
				if (plugin.playerCache.get(player).getLand().equalsIgnoreCase(plugin.playerCache.get(damager).getLand())) {
					e.setCancelled(true);
//					damager.sendMessage("Je kan je eigen land leden niet doden.");
				}
			}
		}

		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player))) {
			Player player = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();

			if (plugin.playerCache.get(player).getLand().equalsIgnoreCase(plugin.playerCache.get(damager).getLand())) {
				e.setCancelled(true);
//				damager.sendMessage("Je kan je eigen land leden niet slaan.");
			}

		}

	}

//	@EventHandler
//	public void onArrow(EntityDamageByEntityEvent event) {
//	    if((event.getEntity() instanceof Player) && (event.getDamager() instanceof Projectile) && (((Projectile) event.getDamager()).getShooter() instanceof Player)) {
//	        Player victim = (Player) event.getEntity();
//	        Player damager = ((Player) ((Projectile) event.getDamager()).getShooter());
//	        User user1 = main.userselect.get(victim.getUniqueId());
//	        User user2 = main.userselect.get(damager.getUniqueId());
//
//	        if (UserUtil.instance.isInSameCrew(user1, user2) || UserUtil.instance.isAlly(user1, user2) || shooter.equals(damager)) {
//	            damager.sendMessage(pref + ChatColor.GREEN + "You can't hit " + victim.getName());
//	            event.setCancelled(true);
//	        }
//	    }
//	}

}
