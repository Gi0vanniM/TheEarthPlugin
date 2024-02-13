package me.Gi0vanniM.TheEarth.Menu;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import me.Gi0vanniM.TheEarth.Main;
import net.md_5.bungee.api.ChatColor;

public class LandMenu implements Listener {

	MySQLDoStuff sql = new MySQLDoStuff();
	Main plugin = Main.getPlugin(Main.class);

	public void openLandMenu(Player player) {
		String land = plugin.playerCache.get(player).getLand();
		File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		String landBal = df.format(cfg.getDouble("rekening"));

		Inventory inv = Bukkit.createInventory(player, 54, ChatColor.GRAY + "Land menu: §c" + land);
		

	}

}
