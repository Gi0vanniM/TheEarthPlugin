package me.Gi0vanniM.TheEarth.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class blockEvents implements Listener {

	@EventHandler
	public void misc(PlayerInteractEvent e) {

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if (b.getType().equals(Material.ENCHANTMENT_TABLE) || b.getType().equals(Material.BREWING_STAND )
					|| b.getType().equals(Material.BEACON) || b.getType().equals(Material.ANVIL)) {
				if (!e.getPlayer().hasPermission("theearth.use.misc")) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void beaconBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (block.getType() == Material.BEACON) {
			if (!e.getPlayer().hasPermission("TheEarth.*")) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void playerRenameItem(InventoryClickEvent event) {
		if (event.getView().getType() == InventoryType.ANVIL) {
			if (event.getRawSlot() == 2) {
				if (event.getView().getItem(0).getType() != Material.AIR
						&& event.getView().getItem(2).getType() != Material.AIR) {
					if (/*
						 * event.getView().getItem(0).getItemMeta().getDisplayName() !=
						 * event.getView().getItem(2) .getItemMeta().getDisplayName() ||
						 */ event.getView().getItem(2).getType() == Material.CLAY_BRICK) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

//	@EventHandler
//	public void diamondaxe(PlayerInteractEvent e) {
//
//		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
//			Player pl = (Player) e.getPlayer();
//			if (pl.getItemInHand().getType() == Material.DIAMOND_AXE) {
//				e.setCancelled(true);
//			}
//		}
//
//	}

}
