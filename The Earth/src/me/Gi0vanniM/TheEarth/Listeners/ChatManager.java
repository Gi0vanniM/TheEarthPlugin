package me.Gi0vanniM.TheEarth.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.Gi0vanniM.TheEarth.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Gi0vanniM.TheEarth.Players.PlayerMethods;

public class ChatManager implements Listener {

	PlayerMethods pmethods = new PlayerMethods();

//	int bD1 = 32;
//	int bD2 = bD1 * 3;
//	int bD3 = bD1 * 6;

	public void sendToSocialSpies(String msg) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Main.plugin.socialspy.contains(player)) {
				player.sendMessage(msg);
			}
		}
	}

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		if (Main.plugin.getConfig().getBoolean("chat.disabled") == true) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cDe chat is momenteel uitgeschakeld.");
		} else {

			boolean range = false;

			String rawmsg = e.getMessage();
			String msgF = rawmsg.replace(":blank:", "████").replace(":b:", "§4§lB§l§r");
			String msgmd = parseMarkdown(e.getPlayer(), msgF);

			Player player = e.getPlayer();

			String prefix = pmethods.getPlayerPrefix(player);
			String name = player.getName();
			ChatColor color = pmethods.getPlayerChatColor(player);

			String rawLog = name + ": " + rawmsg;

			String socialspy = ChatColor.stripColor(prefix) + ChatColor.stripColor(name) + ": ";

//		[LAND] [RANK?] [SUBRANK?] 

			char mark = rawmsg.charAt(0);

			switch (mark) {
			case '!':
				ShoutChannel(prefix + color + name + ": " + msgmd.replaceFirst("!", ""), e);
				Log.info("[SHOUT] " + rawLog.replaceFirst("!", ""));
				break;
			case '#':
				RangeChannel(e, prefix + color + name + ": ", msgmd.replaceFirst("#", ""));
				Log.info("[RANGE] " + rawLog.replaceFirst("#", ""));
				sendToSocialSpies("§8[SOCIALSPY] " + "[RANGE] §7" + socialspy + msgmd.replaceFirst("#", ""));
				break;
			default:
				LandChannel(prefix + color + name + ": " + msgmd, e);
				Log.info("[LAND] " + rawLog);
				sendToSocialSpies("§8[SOCIALSPY] " + "[LAND] §7" + socialspy + msgmd.replaceFirst("!", ""));
				break;
			}

//		if (range == false) {
//			String msg1 = prefix + color + name + ": " + msgmd;
//
//			for (Player pl : e.getRecipients()) {
//				pl.sendMessage(msg);
//			}
//			Log.info(rawLog);
//			e.getRecipients().clear();
//		}
		}
	}

	public void ShoutChannel(String msg, AsyncPlayerChatEvent e) {
		// SHOUT

		for (Player pl : e.getRecipients()) {
			pl.sendMessage("§0[§4§l!§r§0]§r " + msg);
		}
		e.getRecipients().clear();
	}

	public void RangeChannel(AsyncPlayerChatEvent e, String pre, String msg) {
		// RANGE

		// block Distance ADD OPTIONS TO CONFIG
		int bD1 = 32;
		int bD2 = bD1 * 3;
		int bD3 = bD1 * 6;

		Location playerLocation = e.getPlayer().getLocation();

		for (Player pl : e.getRecipients()) {

			if (pl.getWorld().equals(e.getPlayer().getWorld())) {
				if (pl.getLocation().distance(playerLocation) <= bD1) {
					pl.sendMessage("§0[§f#§0]§r " + pre + ChatColor.WHITE + msg);
				}
			}
		}

		for (Player pl : e.getRecipients()) {

			if (pl.getWorld().equals(e.getPlayer().getWorld())) {
				double distance = (pl.getLocation().distance(playerLocation));
				if (distance <= bD2 && distance >= bD1++) {
					pl.sendMessage("§0[§f#§0]§r " + pre + ChatColor.GRAY + msg);
				}
			}
		}

		for (Player pl : e.getRecipients()) {

			if (pl.getWorld().equals(e.getPlayer().getWorld())) {
				double distance = (pl.getLocation().distance(playerLocation));
				if (distance <= bD3 && distance >= bD2++) {
					pl.sendMessage("§0[§f#§0]§r " + pre + ChatColor.DARK_GRAY + msg);
				}
			}
		}
		e.getRecipients().clear();
	}

	@SuppressWarnings("deprecation")
	public void LandChannel(String msg, AsyncPlayerChatEvent e) {
		// LAND CHAT
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
//		Team team = scoreboard.getPlayerTeam((Player)e.getPlayer());
		Team team = scoreboard.getPlayerTeam(e.getPlayer());

		for (OfflinePlayer pl : team.getPlayers()) {
			if (pl.isOnline()) {
				Player pland = pl.getPlayer();
				pland.sendMessage(msg);
			}
		}
		e.getRecipients().clear();
	}

	public String chatFilter(Player player, String msg) {

		return msg;
	}

	// MD_, source code:
	// https://github.com/Aeternum-Studios/MarkdownForMinecraft/blob/master/m/d/MD_.java
	public static String parseMarkdown(Player player, String message) {
		String translated = message;

//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.bold")) {
		translated = replaceWith(translated, "(?<!\\\\)\\*\\*", ChatColor.COLOR_CHAR + "z", ChatColor.COLOR_CHAR + "Z");
//		}
//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.italic")) {
		translated = replaceWith(translated, "(?<!\\\\)\\*", ChatColor.COLOR_CHAR + "x", ChatColor.COLOR_CHAR + "X");
//		}
//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.underline")) {
		translated = replaceWith(translated, "(?<!\\\\)__", ChatColor.COLOR_CHAR + "v", ChatColor.COLOR_CHAR + "V");
//		}
//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.italic")) {
		translated = replaceWith(translated, "(?<!\\\\)_", ChatColor.COLOR_CHAR + "q", ChatColor.COLOR_CHAR + "Q");
//		}
//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.strikethrough")) {
		translated = replaceWith(translated, "(?<!\\\\)~~", ChatColor.COLOR_CHAR + "m", ChatColor.COLOR_CHAR + "M");
//		}
//		if (player.hasPermission("md.parse.all") || player.hasPermission("md.parse.blank")) {
//			translated = replaceWith(translated, "(?<!\\\\)~", ChatColor.COLOR_CHAR + "w", ChatColor.COLOR_CHAR + "W");
//		}

		translated = translated.replace("\\*", "*").replace("\\_", "_").replace("\\~", "~");

		String[] parts = (" " + translated).split("" + ChatColor.COLOR_CHAR);
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			if (part.isEmpty()) {
				continue;
			}
			char colorCharacter = part.charAt(0);
			ChatColor color = ChatColor.getByChar(colorCharacter);

			String colors = ChatColor.getLastColors(builder.toString());
			if (color != null) {
				StringBuilder colorBuilder = new StringBuilder();
				for (String cc : colors.split(ChatColor.COLOR_CHAR + "")) {
					if (cc.isEmpty()) {
						continue;
					}
					if (ChatColor.getByChar(cc.charAt(0)).isFormat()) {
						colorBuilder.append(ChatColor.COLOR_CHAR + cc);
					}
				}
				builder.append(color + colorBuilder.toString());
			} else {
				if (colorCharacter == 'z') {
					builder.append(ChatColor.BOLD);
				} else if (colorCharacter == 'x') {
					builder.append(ChatColor.ITALIC);
				} else if (colorCharacter == 'v') {
					builder.append(ChatColor.UNDERLINE);
				} else if (colorCharacter == 'q') {
					builder.append(ChatColor.ITALIC);
				} else if (colorCharacter == 'm') {
					builder.append(ChatColor.STRIKETHROUGH);
				} else if (colorCharacter == 'w') {
					builder.append("[b]");
				} else if (colorCharacter == 'Z') {

					colors = colors.replace(ChatColor.BOLD.toString(), "");
				} else if (colorCharacter == 'X') {
					colors = colors.replace(ChatColor.ITALIC.toString(), "");
				} else if (colorCharacter == 'V') {
					colors = colors.replace(ChatColor.UNDERLINE.toString(), "");
				} else if (colorCharacter == 'Q') {
					colors = colors.replace(ChatColor.ITALIC.toString(), "");
				} else if (colorCharacter == 'M') {
					colors = colors.replace(ChatColor.STRIKETHROUGH.toString(), "");
				} else if (colorCharacter == 'W') {
					colors = colors.replace("[B]", "");
				}
				if (Character.isUpperCase(colorCharacter)) {
					builder.append(ChatColor.RESET + colors);
				}
			}
			if (part.length() > 1) {
				builder.append(part.substring(1));
			}
		}

		return builder.toString();
	}

	private static String replaceWith(String message, String quot, String pre, String suf) {
		String part = message;
		for (String str : getMatches(message, quot + "(.+?)" + quot)) {
			part = part.replaceFirst(quot + Pattern.quote(str) + quot, pre + str + suf);
		}
		return part;
	}

	public static List<String> getMatches(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		List<String> matches = new ArrayList<String>();
		while (matcher.find()) {
			matches.add(matcher.group(1));
		}
		return matches;
	}
}