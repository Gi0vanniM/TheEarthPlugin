package me.Gi0vanniM.TheEarth.methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Methods {

	public Scoreboard sb;

	public ChatColor getColorfromCode(String code) {

		switch (code) {
		case "&4":
			return ChatColor.DARK_RED;
		case "&c":
			return ChatColor.RED;
		case "&6":
			return ChatColor.GOLD;
		case "&e":
			return ChatColor.YELLOW;
		case "&2":
			return ChatColor.DARK_GREEN;
		case "&a":
			return ChatColor.GREEN;
		case "&b":
			return ChatColor.AQUA;
		case "&3":
			return ChatColor.DARK_AQUA;
		case "&1":
			return ChatColor.DARK_BLUE;
		case "&9":
			return ChatColor.BLUE;
		case "&d":
			return ChatColor.LIGHT_PURPLE;
		case "&5":
			return ChatColor.DARK_PURPLE;
		case "&f":
			return ChatColor.WHITE;
		case "&7":
			return ChatColor.GRAY;
		case "&8":
			return ChatColor.DARK_GRAY;
		case "&0":
			return ChatColor.BLACK;
		case "&r":
			return ChatColor.RESET;
		case "&l":
			return ChatColor.BOLD;
		case "&o":
			return ChatColor.ITALIC;
		case "&n":
			return ChatColor.UNDERLINE;
		case "&m":
			return ChatColor.STRIKETHROUGH;
		case "&k":
			return ChatColor.MAGIC;

		}

		return null;
	}

	public String getMCcolorfromCode(String code) {

		switch (code) {
		case "&4":
			return "DARK_RED";
		case "&c":
			return "RED";
		case "&6":
			return "GOLD";
		case "&e":
			return "YELLOW";
		case "&2":
			return "DARK_GREEN";
		case "&a":
			return "GREEN";
		case "&b":
			return "AQUA";
		case "&3":
			return "DARK_AQUA";
		case "&1":
			return "DARK_BLUE";
		case "&9":
			return "BLUE";
		case "&d":
			return "LIGHT_PURPLE";
		case "&5":
			return "DARK_PURPLE";
		case "&f":
			return "WHITE";
		case "&7":
			return "GRAY";
		case "&8":
			return "DARK_GRAY";
		case "&0":
			return "BLACK";
		case "&r":
			return "RESET";
		case "&l":
			return "BOLD";
		case "&o":
			return "ITALIC";
		case "&n":
			return "UNDERLINE";
		case "&m":
			return "STRIKETHROUGH";
		case "&k":
			return "MAGIC";

		}

		return null;
	}

}
