package me.Gi0vanniM.TheEarth.Features.SignTP;

public class CoordinateSignTP {

	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean rangeCheck(float num) {
		int maxCoord = 30000000;
		int minCoord = -30000000;

		if ((num >= 30000000.0F) || (num <= -30000000.0F))
			return false;
		return true;
	}
}
