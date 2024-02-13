package me.Gi0vanniM.TheEarth.Features.Lift;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

public class LiftPlayerListener implements Listener {
	public HashMap<String, Long> cooldowns = new HashMap();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if ((event.getPlayer() == null) || (event.getClickedBlock() == null)) {
			return;
		}
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if ((block.getState() instanceof Sign)) {
				Sign sign = (Sign) block.getState();
				if ((Lift.isSignWarp(sign)) && (player.hasPermission("Lift.Use.Click"))) {
					String warpName = sign.getLine(0).substring(1, sign.getLine(0).length() - 1);
					event.setCancelled(true);
					Lift.warpSign((Sign) block.getState(), event.getPlayer());
//					player.sendMessage(ChatColor.DARK_AQUA + "You have been teleported.");
				}
			}
		}
	}

	@EventHandler
	public void pressureplate(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.PHYSICAL)) {
			if (event.getClickedBlock().getType() == Material.IRON_PLATE) {
				Player player = event.getPlayer();
				Block block = event.getClickedBlock().getRelative(BlockFace.UP);
				if ((block.getState() instanceof Sign)) {
					Sign sign = (Sign) block.getState();

					if ((Lift.isSignWarp(sign)) && (player.hasPermission("Lift.Use"))) {

						// BEGIN COOLDOWN ========================
						int cooldownTime = 5;
						if (this.cooldowns.containsKey(player.getName())) {
							long secondsLeft = ((Long) this.cooldowns.get(player.getName())).longValue() / 1000L
									+ cooldownTime - System.currentTimeMillis() / 1000L;
							if (secondsLeft > 0L) {
//								player.sendMessage(ChatColor.GRAY + "You can teleport again in: §c" + ChatColor.RED
//										+ secondsLeft + ChatColor.GRAY + " second(s).");
								// Main.sendActionBar(player, Main.plugin.title + ChatColor.GRAY + "Wacht §c" +
								// ChatColor.RED + secondsLeft + ChatColor.GRAY + " seconden.");
								// player.getWorld().playSound(player.getLocation(), Sound.BLAZE_BREATH, 1.0F,
								// 1.0F);

								return;
							}
						}
						this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
						final BlockIterator a = new BlockIterator(player);

						int countUp = 0;
						while ((countUp++ < 0) && (a.hasNext())) {
							a.next();
						} // END COOLDOWN ==========================

						String warpName = sign.getLine(0).substring(1, sign.getLine(0).length() - 1);
						event.setCancelled(true);
						Lift.warpSign((Sign) block.getState(), event.getPlayer());
//						player.sendMessage(ChatColor.DARK_AQUA + "You have been teleported.");
					}
				}
			}
		}
	}
}
