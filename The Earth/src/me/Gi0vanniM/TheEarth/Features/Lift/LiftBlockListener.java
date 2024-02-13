package me.Gi0vanniM.TheEarth.Features.Lift;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class LiftBlockListener implements Listener{
	@EventHandler
	  public void onSignChange(SignChangeEvent e) {
		  if (e.getLine(0).equalsIgnoreCase("[lift]")) {
			  e.setLine(0, '[' + "§bLift§0" + ']');
		  }
	  }
	
@EventHandler(priority=EventPriority.NORMAL)
public void onSignChange1(SignChangeEvent event) {
  Player player = event.getPlayer();

  if (event != null) {
    if (Lift.isSignWarp(event)) {
      if (player.hasPermission("Lift.Create")) {
        player.sendMessage(ChatColor.GRAY + "Je hebt een lift/verdieping aangemaakt.");
      } else {
        player.sendMessage(ChatColor.RED + "Je hebt geen toegang tot deze actie.");
        event.setCancelled(true);
      }
    }
  }
}
}
