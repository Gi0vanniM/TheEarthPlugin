package me.Gi0vanniM.TheEarth.Listeners;

import me.Gi0vanniM.TheEarth.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

public class openDoors implements Listener {

	@EventHandler
	public void openDoor(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		if (Main.plugin.openDoors.contains(player)) {

			if (e.getHand() == EquipmentSlot.OFF_HAND) {

				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					
					if (block.getLocation().add(0, -1, 0).getBlock().getType() == Material.IRON_DOOR_BLOCK) {
						block = block.getLocation().add(0, -1, 0).getBlock();
					}

					if (block.getType() == Material.IRON_DOOR_BLOCK || block.getType() == Material.IRON_TRAPDOOR) {

						BlockState state = block.getState();
						Openable openable = (Openable) state.getData();

						if (!openable.isOpen()) {
							openable.setOpen(true);
							state.setData((MaterialData) openable);
							state.update();
							
							if (block.getType() == Material.IRON_DOOR_BLOCK) {
								block.getWorld().playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 0.5F, 1F);
							}
							if (block.getType() == Material.IRON_TRAPDOOR) {
								block.getWorld().playSound(block.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 0.5F, 1F);
							}
							
						} else {
							openable.setOpen(false);
							state.setData((MaterialData) openable);
							state.update();
							
							if (block.getType() == Material.IRON_DOOR_BLOCK) {
								block.getWorld().playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 0.5F, 1F);
							}
							if (block.getType() == Material.IRON_TRAPDOOR) {
								block.getWorld().playSound(block.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 0.5F, 1F);
							}
						}

					}
				}
			}
		}
	}

}
