package me.Gi0vanniM.TheEarth.Players;

import java.io.File;

import me.Gi0vanniM.TheEarth.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class playerPermissions {
	
	Main plugin = Main.getPlugin(Main.class);
	
	public void setupPermissions(Player player) {
		PermissionAttachment attachment = player.addAttachment(plugin);
		plugin.playerPermissions.put(player.getUniqueId(), attachment);
		permissionsSetter(player);
		
	}

	private void permissionsSetter(Player player) {
		String land = new PlayerMethods().getPlayerLand(player);
		PermissionAttachment attachment = plugin.playerPermissions.get(player.getUniqueId());
		
		File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		for (String permissions : cfg.getStringList("permissions")) {
			attachment.setPermission(permissions, true);
//			Bukkit.broadcastMessage(permissions + " SET");
		}
		
	}

}
