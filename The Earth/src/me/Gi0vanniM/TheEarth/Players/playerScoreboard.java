package me.Gi0vanniM.TheEarth.Players;

public class playerScoreboard {

	String land;
	String rang;
	int landkracht;
	double landrekening;
	double bankrekening;
	String oorlog;

	public playerScoreboard(String land, String rang, int landkracht, double landrekening, double bankrekening,
			String oorlog) {
		this.land = land;
		this.rang = rang;
		this.landkracht = landkracht;
		this.landrekening = landrekening;
		this.bankrekening = bankrekening;
		this.oorlog = oorlog;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public int getLandkracht() {
		return landkracht;
	}

	public void setLandkracht(int landkracht) {
		this.landkracht = landkracht;
	}

	public double getLandrekening() {
		return landrekening;
	}

	public void setLandrekening(double landrekening) {
		this.landrekening = landrekening;
	}

	public double getBankrekening() {
		return bankrekening;
	}

	public void setBankrekening(double bankrekening) {
		this.bankrekening = bankrekening;
	}

	public String getOorlog() {
		return oorlog;
	}

	public void setOorlog(String oorlog) {
		this.oorlog = oorlog;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bankrekening);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + landkracht;
		temp = Double.doubleToLongBits(landrekening);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((oorlog == null) ? 0 : oorlog.hashCode());
		result = prime * result + ((rang == null) ? 0 : rang.hashCode());
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
		playerScoreboard other = (playerScoreboard) obj;
		if (Double.doubleToLongBits(bankrekening) != Double.doubleToLongBits(other.bankrekening))
			return false;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (landkracht != other.landkracht)
			return false;
		if (Double.doubleToLongBits(landrekening) != Double.doubleToLongBits(other.landrekening))
			return false;
		if (oorlog == null) {
			if (other.oorlog != null)
				return false;
		} else if (!oorlog.equals(other.oorlog))
			return false;
		if (rang == null) {
			if (other.rang != null)
				return false;
		} else if (!rang.equals(other.rang))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "playerScoreboard [land=" + land + ", rang=" + rang + ", landkracht=" + landkracht + ", landrekening="
				+ landrekening + ", bankrekening=" + bankrekening + ", oorlog=" + oorlog + "]";
	}

}
