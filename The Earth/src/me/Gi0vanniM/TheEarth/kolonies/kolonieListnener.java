package me.Gi0vanniM.TheEarth.kolonies;

import java.util.HashMap;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BlockIterator;

public class kolonieListnener implements Listener {
	public HashMap<String, Long> cooldowns = new HashMap();

	Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onFlag(PlayerInteractEvent e) {
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return;
		}
		Player player = e.getPlayer();
//		Block block = player.getTargetBlock((Set<Material>) null, 5);
		Block block = e.getClickedBlock();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location loc = block.getLocation();
			if (block.getType() == Material.SPONGE) {
				if (block.getData() == (byte) 1) {

					player.sendMessage("right click");

					boolean islead = new PlayerMethods().isLead(player);
					if (islead) {
						player.sendMessage("lead");

						if (new kolonieMethods().isKolonie(block)) {
							player.sendMessage("is kolonie");

							String land = plugin.playerCache.get(player).getLand();
							String kolonie = new kolonieMethods().getKolonie(block);

							if (!new kolonieMethods().hasKolonie(land, kolonie)) {
								
								if (new kolonieMethods().hasKracht(land)) {
									
									  // COOLDOWN
								      int cooldownTime = 60;
								      if (this.cooldowns.containsKey(land)) {
								        long secondsLeft = ((Long)this.cooldowns.get(land)).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
								        if (secondsLeft > 0L) {
								        	player.sendMessage("§7Je moet nog §c" + secondsLeft + "§7 seconden wachten tot je weer een kolonie probeer over te nemen.");
								          return;
								        }
								      }
								      this.cooldowns.put(land, Long.valueOf(System.currentTimeMillis()));
									                //  END COOLDOWN
									
									new kolonieMethods().claimKolonie(new kolonieMethods().getKolonieFile(block), land,
											block);
								} else {
									player.sendMessage(
											"§cJe land heeft niet genoeg kracht om deze kolonie over te nemen.");
								}
							} else {
								player.sendMessage("§7Je land heeft deze kolonie al overgenomen.");
							}

						}
					} else {
						player.sendMessage("§cJe hebt geen toestemming om een kolonie over te nemen.");
					}
				}
			}
		}
	}

	@EventHandler
	public void mineFlag(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (block.getType() == Material.SPONGE) {
			if (block.getData() == (byte) 1) {
				if (!e.getPlayer().hasPermission("TheEarth.*")) {
					e.setCancelled(true);
				}
			}
		}
	}
}
