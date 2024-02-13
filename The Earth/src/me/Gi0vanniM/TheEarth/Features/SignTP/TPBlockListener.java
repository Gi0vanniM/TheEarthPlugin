package me.Gi0vanniM.TheEarth.Features.SignTP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class TPBlockListener implements Listener {
	
	@EventHandler
	  public void onSignChange(SignChangeEvent e) {
		  if (e.getLine(0).equalsIgnoreCase("[TP]")) {
			  e.setLine(0, '[' + "§bTeleporter§0" + ']');
		  }
	  }
	
  @EventHandler(priority=EventPriority.NORMAL)
  public void onSignChange1(SignChangeEvent event) {
    Player player = event.getPlayer();

    if (event != null) {
      if (CoordinateSign.isSignWarp(event)) {
        if (player.hasPermission("SignTP.Create")) {
          player.sendMessage(ChatColor.GRAY + "Created a teleporter sign");
        } else {
          player.sendMessage(ChatColor.RED + "You do not have the permission to do that.");
          event.setCancelled(true);
        }
      }
    }
  }
}