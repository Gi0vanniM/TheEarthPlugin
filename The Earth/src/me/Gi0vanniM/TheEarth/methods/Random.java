package me.Gi0vanniM.TheEarth.methods;

public class Random {

	public static double RandomMinMax(double min, double max) {
		double r;
		r = min + (Math.random() * (max - min));
		return r;
	}
	
	public static float RandomMinMaxF(float min, float max) {
		float r;
		r = (float) (min + (Math.random() * (max - min)));
		return r;
	}
	
}