package me.Gi0vanniM.TheEarth.methods.Time;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class getPlayerPlayTime {
	
	public String getPlayTime(Player player) {
		
		new TimeFormat();
		String time = TimeFormat.getTime(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20);
		
		return time;
	}
	public String getPlayTimeOFF(OfflinePlayer player) {
		
		new TimeFormat();
		String time = TimeFormat.getTime(player.getPlayer().getStatistic(Statistic.PLAY_ONE_TICK) / 20);
		
		return time;
	}

}
