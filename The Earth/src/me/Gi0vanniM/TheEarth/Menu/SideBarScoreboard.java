package me.Gi0vanniM.TheEarth.Menu;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import me.Gi0vanniM.TheEarth.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.sun.glass.ui.Pixels.Format;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Players.PlayerMethods;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.IScoreboardCriteria;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_12_R1.ScoreboardObjective;
import net.minecraft.server.v1_12_R1.ScoreboardScore;
import net.minecraft.server.v1_12_R1.ScoreboardTeam;

public class SideBarScoreboard {

	Main plugin = Main.getPlugin(Main.class);
	Methods method = new Methods();

	public void setOrUpdateScoreboard(Player player) {
		if (plugin.playerCache.containsKey(player)) {
			if (player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
				setScoreboard(player);
//				player.sendMessage("set sb");
			} else {
				updateScoreboard(player);
//				player.sendMessage("update sb");
			}
		}
	}

	public static void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

	}

	public void setScoreboard(Player player) {
		Team board1 = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("TheEarth", "Dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "The Earth");

		String landSt = new PlayerMethods().getPlayerLand(player);
		File file = new File("plugins/TheEarthPlugin/landen", landSt + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

//		Scoreboard b = Bukkit.getScoreboardManager().getMainScoreboard();
//		Set<Team> t = b.getTeams();
//
//		int size = t.size();
//
//		for (int i = 0; i < size; i++) {
//			Team tt = (Team) t.toArray()[i];
//			Team sbt = null;
//			sbt = board.registerNewTeam("sb_" + tt.getName());
//			sbt.setAllowFriendlyFire(false);
//			ChatColor cc = tt.getColor();
//			sbt.setColor(cc);
////			String colt = new method.getMCcolorfromCode(color);
//			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
//					"scoreboard teams option " + tt.getName() + " color " + tt.getColor().name());
//
//			Set<OfflinePlayer> pl = tt.getPlayers();
//			int psize = tt.getPlayers().size();
//			for (int i1 = 0; i1 < psize; i1++) {
//				OfflinePlayer pla = (OfflinePlayer) pl.toArray()[i1];
//				sbt.addPlayer(pla);
//			}
//		}

		// SEASON
		Score season = obj.getScore(ChatColor.WHITE + "" + ChatColor.ITALIC + "      Season 2");
		season.setScore(13);

		// LAND
		Score land = obj.getScore(ChatColor.RED + "Land:");
		land.setScore(12);

		Team pland = board.registerNewTeam("pland");
		pland.addEntry("§1§1");
		pland.setPrefix("  " + ChatColor.GRAY + plugin.playerCache.get(player).getLand());
		obj.getScore("§1§1").setScore(11);

		// RANG
		Score rang = obj.getScore(ChatColor.RED + "Rang:");
		rang.setScore(10);

		Team prang = board.registerNewTeam("prang");
		prang.addEntry("§9");
		String rank = plugin.playerCache.get(player).getRank();
		if (rank.equals("none")) {
			rank = plugin.playerCache.get(player).getSubrank();
		}
		
		if (rank.length() > 12) {
			rank = rank.substring(0, 11);
		}
		prang.setPrefix("  " + ChatColor.GRAY + rank);
		obj.getScore("§9").setScore(9);

		// LANDKRACHT
		Score kracht = obj.getScore(ChatColor.RED + "Landkracht:");
		kracht.setScore(8);

		Team pkracht = board.registerNewTeam("pkracht");
		pkracht.addEntry("§7");
		pkracht.setPrefix("  " + ChatColor.GRAY + cfg.getInt("kracht") + "/1000");
		obj.getScore("§7").setScore(7);

		// LANDREKENING
		Score landrekening = obj.getScore(ChatColor.RED + "Landrekening:");//////////////////////////////////////////////////////////////////////
		landrekening.setScore(6);

		Team plandrekeing = board.registerNewTeam("plandrekening");
		plandrekeing.addEntry("§5");
		double landgeld = cfg.getDouble("landrekening");
		DecimalFormat dff = new DecimalFormat("#,##0.00");
		dff.setMaximumIntegerDigits(9);
		dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		plandrekeing.setPrefix("  " + ChatColor.GRAY + "§ ");
		plandrekeing.setSuffix(ChatColor.GRAY + dff.format(landgeld));
		obj.getScore("§5").setScore(5);

		// BANKREKENING
		Score rekening = obj.getScore(ChatColor.RED + "Bankrekening:");
		rekening.setScore(4);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		Team geld = board.registerNewTeam("prekening");
		geld.addEntry("§3");
		double bal = Main.economy.getBalance(player);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setMaximumIntegerDigits(9);
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		geld.setPrefix("  " + ChatColor.GRAY + "§ ");
		geld.setSuffix(ChatColor.GRAY + df.format(bal));
		obj.getScore("§3").setScore(3);

		// OORLOG
		Score oorlog = obj.getScore(ChatColor.RED + "Oorlog:");
		oorlog.setScore(2);

		Team poorlog = board.registerNewTeam("poorlog");
		poorlog.addEntry("§1");
		poorlog.setPrefix("  " + ChatColor.GRAY + "Nee");
		obj.getScore("§1").setScore(1);

		// IP
		Score ip = obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "TheEarthMC.nl");
		ip.setScore(0);

		((Player) player).setScoreboard(board);

	}

	public void updateScoreboard(Player player) {

		Scoreboard board = player.getScoreboard();

		String landSt = new PlayerMethods().getPlayerLand(player);
		File file = new File("plugins/TheEarthPlugin/landen", landSt + ".yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		board.getTeam("pland").setPrefix("  " + ChatColor.GRAY + plugin.playerCache.get(player).getLand());

		String rank = plugin.playerCache.get(player).getRank();
		if (rank.equals("none")) {
			rank = plugin.playerCache.get(player).getSubrank();
		}
		if (rank.length() > 12) {
			rank = rank.substring(0, 11);
		}
		board.getTeam("prang").setPrefix("  " + ChatColor.GRAY + rank);

		board.getTeam("pkracht").setPrefix("  " + ChatColor.GRAY + cfg.getInt("kracht") + "/1000");

		double landgeld = cfg.getDouble("landrekening");
		DecimalFormat dff = new DecimalFormat("#,##0.00");
		dff.setMaximumIntegerDigits(9);
		dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		board.getTeam("plandrekening").setPrefix("  " + ChatColor.GRAY + "§ ");
		board.getTeam("plandrekening").setSuffix(ChatColor.GRAY + dff.format(landgeld));

		double bal = Main.economy.getBalance(player);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setMaximumIntegerDigits(9);
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
		board.getTeam("prekening").setPrefix("  " + ChatColor.GRAY + "§ ");
		board.getTeam("prekening").setSuffix(ChatColor.GRAY + df.format(bal));

		board.getTeam("poorlog").setPrefix("  " + ChatColor.GRAY + "Nee");

	}

}
