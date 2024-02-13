package me.Gi0vanniM.TheEarth.Features.SignTP;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class CoordinateSign {
	public static void warpSign(Sign sign, Player player) {
		ArrayList lines = new ArrayList();

		for (int i = 0; i < 4; i++) {
			if (!sign.getLine(i).trim().equals(""))
				lines.add(sign.getLine(i).trim());
		}
		int yaw = (int) player.getLocation().getYaw();
		int pitch = (int) player.getLocation().getPitch();

		Location location = new Location(player.getWorld(), Float.parseFloat((String) lines.get(1) + ".500"),
				Float.parseFloat((String) lines.get(2)), Float.parseFloat((String) lines.get(3) + ".500"), yaw, pitch);
		player.teleport(location);
	}

	public static boolean isSignWarp(Sign sign) {
		ArrayList lines = new ArrayList();

		for (int i = 0; i < 4; i++) {
			if (!sign.getLine(i).trim().equals("")) {
				lines.add(sign.getLine(i).trim());
			}
		}
		if (lines.size() == 4) {
			if ((((String) lines.get(0)).length() >= 2)
					&& (((String) lines.get(0)).equalsIgnoreCase('[' + "§bTeleporter§0" + ']'))
					&& (CoordinateSignTP.isFloat((String) lines.get(1) + ".500"))
					&& (CoordinateSignTP.isFloat((String) lines.get(2)))
					&& (CoordinateSignTP.isFloat((String) lines.get(3) + ".500"))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(1))))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(2))))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(3)))))
				return true;
		}
		return false;
	}

	public static boolean isSignWarp(SignChangeEvent sign) {
		ArrayList lines = new ArrayList();

		for (int i = 0; i < 4; i++) {
			if (!sign.getLine(i).trim().equals(""))
				lines.add(sign.getLine(i).trim());
		}
		if (lines.size() == 4) {
			String firstLine = (String) lines.get(0);

			if (firstLine.length() >= 2) {
				firstLine = firstLine.substring(1, firstLine.length() - 1);
			}
			if ((((String) lines.get(0)).length() >= 2)
					&& (((String) lines.get(0)).equalsIgnoreCase('[' + "§bTeleporter§0" + ']'))
					&& (CoordinateSignTP.isFloat((String) lines.get(1) + ".500"))
					&& (CoordinateSignTP.isFloat((String) lines.get(2)))
					&& (CoordinateSignTP.isFloat((String) lines.get(3) + ".500"))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(1))))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(2))))
					&& (CoordinateSignTP.rangeCheck(Float.parseFloat((String) lines.get(3)))))
				return true;
		}
		return false;
	}
}
