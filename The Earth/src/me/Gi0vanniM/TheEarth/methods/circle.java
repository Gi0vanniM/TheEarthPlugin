package me.Gi0vanniM.TheEarth.methods;

import org.bukkit.Location;

public class circle {
    public Location makeCircle(Location center, double radius, int points, int i) {
  	  double angle = 2 * Math.PI * i / points;
  	  Location point = center.clone().add(radius * Math.sin(angle), 0.0d, radius * Math.cos(angle));

        return point;
    }
}
