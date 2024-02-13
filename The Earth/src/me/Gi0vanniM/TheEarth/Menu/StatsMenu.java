package me.Gi0vanniM.TheEarth.Menu;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import me.Gi0vanniM.TheEarth.MySQL.MySQLDoStuff;
import me.Gi0vanniM.TheEarth.methods.Time.UnixTime;
import me.Gi0vanniM.TheEarth.methods.Time.getPlayerPlayTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Gi0vanniM.TheEarth.Main;

public class StatsMenu implements Listener {

	MySQLDoStuff sql = new MySQLDoStuff();
	Main plugin = Main.getPlugin(Main.class);

	public void openStatMenu(Player player, Player opener) {
//		int kills = (int) sql.getPlayerDataNum(player, "KILLS");
		int kills = plugin.playerCache.get(player).getKills();
//		int deaths = (int) sql.getPlayerDataNum(player, "DEATHS");
		int deaths = plugin.playerCache.get(player).getDeaths();
//		Double money = sql.getPlayerDataNum(player, "MONEY");
		double bal = Main.economy.getBalance(player);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));

		String money = df.format(bal);
//		String land = sql.getPlayerDataString(player, "LAND");
		String land = plugin.playerCache.get(player).getLand();
//		String rank = sql.getPlayerDataString(player, "RANK");
		String rank = plugin.playerCache.get(player).getRank();
//		String subrank = sql.getPlayerDataString(player, "SUBRANK");
		String subrank = plugin.playerCache.get(player).getSubrank();
		String time = new getPlayerPlayTime().getPlayTime(player);
//		String jtime = ChatColor.RED + new UnixTime().unixToDateTime((int) sql.getPlayerDataNum(player, "JOINED"));
		String jtime = ChatColor.RED + new UnixTime().unixToDateTime((Long) plugin.playerCache.get(player).getJoined());

		Inventory inv = Bukkit.createInventory(opener, 54, ChatColor.RED + player.getName() + "'s stats");

		ItemStack killSW = new ItemStack(Material.IRON_SWORD);
		ItemMeta killSWIM = killSW.getItemMeta();
		killSWIM.setDisplayName(ChatColor.RED + "Kills");
		killSWIM.setLore(Arrays.asList(new String[] { "§7Kills: " + kills }));
		killSW.setItemMeta(killSWIM);

		ItemStack skull = new ItemStack(Material.SKULL_ITEM);
		ItemMeta skullM = killSW.getItemMeta();
		skullM.setDisplayName(ChatColor.RED + "Deaths");
		skullM.setLore(Arrays.asList(new String[] { "§7Deaths: " + deaths }));
		skull.setItemMeta(skullM);

		ItemStack gold = new ItemStack(Material.GOLD_INGOT);
		ItemMeta goldM = killSW.getItemMeta();
		goldM.setDisplayName(ChatColor.RED + "Bankrekening");
		goldM.setLore(Arrays.asList(new String[] { "§7Money: § " + money }));
		gold.setItemMeta(goldM);

		ItemStack pap = new ItemStack(Material.PAPER);
		ItemMeta papM = killSW.getItemMeta();
		papM.setDisplayName(ChatColor.RED + "Land");
		papM.setLore(Arrays.asList(new String[] { "§7Land: " + land }));
		pap.setItemMeta(papM);

		ItemStack tag = new ItemStack(Material.NAME_TAG);
		ItemMeta tagM = killSW.getItemMeta();
		tagM.setDisplayName(ChatColor.RED + "Rang");
		tagM.setLore(Arrays.asList(new String[] { "§7Rang: " + rank, "§7Sub-rang: " + subrank }));
		tag.setItemMeta(tagM);

		ItemStack clock = new ItemStack(Material.WATCH);
		ItemMeta clockM = killSW.getItemMeta();
		clockM.setDisplayName(ChatColor.RED + "Time");
		clockM.setLore(Arrays.asList(new String[] { "§7Time played: " + time, "§7Time joined: " + jtime }));
		clock.setItemMeta(clockM);

		inv.setItem(11, killSW);
		inv.setItem(13, skull);
		inv.setItem(15, gold);
		inv.setItem(29, pap);
		inv.setItem(31, tag);
		inv.setItem(33, clock);

		opener.openInventory(inv);
	}

	@EventHandler
	public void onInvDragEvent(InventoryDragEvent e) {
		if (e.getInventory().getTitle().contains("'s stats")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInvClickEvent(InventoryClickEvent e) {
		if (e.getInventory().getTitle().contains("'s stats")) {
			e.setCancelled(true);
		}
	}
}
