package me.Gi0vanniM.TheEarth.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.EnumHand;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;

public class Bow implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void drawAnimation(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();

//		if (e.getPlayer().getItemInHand().getType() == Material.BOW) {
//			player.sendMessage("HOLDING BOW");

//			for (Player p : Bukkit.getOnlinePlayers()) {
//				if (p != player) {
//					((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(
//							((CraftPlayer) player).getHandle().getId(), 0, new ItemStack(Item.getById(261))));
//				}
//			}

		}

//	}

	public void bowDraw(Player player) {
		player.sendMessage("mmm");
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(player.getEntityId(),
				EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(new ItemStack(Material.BOW)));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

	}

	public void sendAnimation(Player player) {
		Bukkit.broadcastMessage("yep");
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != player) {
				DataWatcher watcher = new DataWatcher(((CraftPlayer) player).getHandle());
				watcher.register(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte) 16);
//				((CraftPlayer) p).getHandle().playerConnection.sendPacket(
//						new PacketPlayOutEntityMetadata(((CraftPlayer) player).getHandle().getId(), watcher, true));
				((CraftPlayer) p).getHandle().playerConnection.a(new PacketPlayInBlockPlace(EnumHand.MAIN_HAND));
			}
		}
	}

}
