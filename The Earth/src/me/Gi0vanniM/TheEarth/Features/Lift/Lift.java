package me.Gi0vanniM.TheEarth.Features.Lift;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class Lift {
	public static void warpSign(Sign sign, Player player) {
		ArrayList lines = new ArrayList();

		for (int i = 0; i < 4; i++) {
			if (!sign.getLine(i).trim().equals(""))
				lines.add(sign.getLine(i).trim());
		}
		int yaw = (int) player.getLocation().getYaw();
		int pitch = (int) player.getLocation().getPitch();

		char mark = lines.get(1).toString().charAt(0);
		double y = Double.parseDouble(lines.get(1).toString());

		switch (mark) {

		case '+':
			Location locationP = new Location(player.getWorld(), player.getLocation().getX(),
					player.getLocation().getY() + y + 0.1, player.getLocation().getZ(), yaw, pitch);
			player.teleport(locationP);
			break;
			
		case '-':
			Location locationM = new Location(player.getWorld(), player.getLocation().getX(),
					player.getLocation().getY() + y + 0.1, player.getLocation().getZ(), yaw, pitch);
			player.teleport(locationM);
			break;

		default:
			Location location = new Location(player.getWorld(), player.getLocation().getX(),
					Float.parseFloat((String) lines.get(1)) + 0.1, player.getLocation().getZ(), yaw, pitch);
			player.teleport(location);
			break;
		}
	}

	public static boolean isSignWarp(Sign sign) {
		ArrayList lines = new ArrayList();

		for (int i = 0; i < 4; i++) {
			if (!sign.getLine(i).trim().equals("")) {
				lines.add(sign.getLine(i).trim());
			}
		}
		if (lines.size() == 2) {
			if ((((String) lines.get(0)).length() >= 2)
					&& (((String) lines.get(0)).equalsIgnoreCase('[' + "§bLift§0" + ']'))
					&& (LiftTP.isFloat((String) lines.get(1)))
					&& (LiftTP.rangeCheck(Float.parseFloat((String) lines.get(1)))))
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
					&& (((String) lines.get(0)).equalsIgnoreCase('[' + "§bLift§0" + ']'))
					&& (LiftTP.isFloat((String) lines.get(1)))
					&& (LiftTP.rangeCheck(Float.parseFloat((String) lines.get(1)))))
				return true;
		}
		return false;
	}
}
