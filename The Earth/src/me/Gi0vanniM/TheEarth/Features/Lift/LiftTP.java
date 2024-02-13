package me.Gi0vanniM.TheEarth.Features.Lift;

public class LiftTP {
	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean rangeCheck(float num) {
		float maxCoord = 500;
		float minCoord = -500;

		if ((num >= maxCoord) || (num <= minCoord))
			return false;
		return true;
	}
}
