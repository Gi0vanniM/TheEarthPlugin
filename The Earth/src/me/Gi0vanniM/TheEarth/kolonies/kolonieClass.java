package me.Gi0vanniM.TheEarth.kolonies;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class kolonieClass {
	String name;
	double uurloon;
	String land;
	Block block;
	String xyz;
	String filename;

	public kolonieClass(String name, double uurloon, String land, Block block, String xyz, String filename) {
		super();
		this.name = name;
		this.uurloon = uurloon;
		this.land = land;
		this.block = block;
		this.xyz = xyz;
		this.filename = filename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUurloon() {
		return uurloon;
	}

	public void setUurloon(double uurloon) {
		this.uurloon = uurloon;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getXyz() {
		return xyz;
	}

	public void setXyz(String xyz) {
		this.xyz = xyz;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(uurloon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((xyz == null) ? 0 : xyz.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		kolonieClass other = (kolonieClass) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(uurloon) != Double.doubleToLongBits(other.uurloon))
			return false;
		if (xyz == null) {
			if (other.xyz != null)
				return false;
		} else if (!xyz.equals(other.xyz))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "kolonieClass [name=" + name + ", uurloon=" + uurloon + ", land=" + land + ", block=" + block + ", xyz="
				+ xyz + ", filename=" + filename + "]";
	}

}
