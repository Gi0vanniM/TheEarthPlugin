package me.Gi0vanniM.TheEarth.methods.Time;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;

public class TimeFormat {

	public static String sec = " sec";			// X
	public static String min = " min, and";		// X
	public static String hr = " hours,";		// X
	public static String day = " days,";		// X
	
	public static String PTformat(int d, int h, int m, int s, Long l) {
		String days = d + "";
		String hours = h + "";
		String min = m + "";
		String sec = s + "";
		Long ahours = l / 3600;
		
		ChatColor gray = ChatColor.GRAY;
		ChatColor red = ChatColor.RED;
		String out = red + days + gray + " days, " + red + hours + gray + " hours, " + red + min + gray + " minutes and " + red + sec + gray + " seconds. Or " + red + ahours.longValue() + gray + " hours.";
		return out;
	}

	public static String getTime(long l) {

		int i_days = 0;
		int i_hours = 0;
		int i_minutes = 0;
		int i_seconds = 0;

		if (l < 60L) {
			i_seconds = i_seconds + (int) l; //
			String timey = PTformat(i_days, i_hours, i_minutes, i_seconds, l);
			return timey;
//			return l + sec;
		}
		int minutes = (int) (l / 60L);
		int s = 60 * minutes;
		int secondsLeft = (int) (l - s);

		if (minutes < 60) {
			if (secondsLeft > 0) {
				i_minutes = i_minutes + minutes; //
				i_seconds = i_seconds + secondsLeft; //
//				return String.valueOf(minutes + min + " " + secondsLeft + sec);
				String timey = PTformat(i_days, i_hours, i_minutes, i_seconds, l);
				return timey;
			}
			i_minutes = i_minutes + minutes; //
			String timey = PTformat(i_days, i_hours, i_minutes, i_seconds, l);
//			return String.valueOf(minutes + min);
			return timey;
		}
		if (minutes < 1440) {
			String time = "";
			int hours = minutes / 60;

			time = hours + hr;
			i_hours = i_hours + hours; //

			int inMins = 60 * hours;
			int left = minutes - inMins;
			if (left >= 1) {

				time = time + " " + left + min;
				i_minutes = i_minutes + left; //
			}
			if (secondsLeft > 0) {

				time = time + " " + secondsLeft + sec;
				i_seconds = i_seconds + secondsLeft; //
			}
			String timey = PTformat(i_days, i_hours, i_minutes, i_seconds, l);
			return timey;
		}
		String time = "";
		int days = minutes / 1440;

		time = days + day;
		i_days = days; //

		int inMins = 1440 * days;
		int leftOver = minutes - inMins;
		if (leftOver >= 1) {
			if (leftOver < 60) {

				time = time + " " + leftOver + min;
				i_minutes = i_minutes + leftOver; //

			} else {
				int hours = leftOver / 60;

				time = time + " " + hours + hr;
				i_hours = i_hours + hours; //

				int hoursInMins = 60 * hours;
				int minsLeft = leftOver - hoursInMins;
				if (leftOver >= 1) {

					time = time + " " + minsLeft + min;
					i_minutes = i_minutes + minsLeft; //
				}
			}
		}
		if (secondsLeft > 0) {

			time = time + " " + secondsLeft + sec;
			i_seconds = i_seconds + secondsLeft; //
		}
		String timey = PTformat(i_days, i_hours, i_minutes, i_seconds, l);
		return /* time + "\n" + ChatColor.AQUA + */timey;
	}

	public static String Uptime() {
		return getTime((int) TimeUnit.MILLISECONDS.toSeconds(ManagementFactory.getRuntimeMXBean().getUptime()));
	}

}