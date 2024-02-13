package me.Gi0vanniM.TheEarth.Players;

import org.bukkit.entity.Player;

public class playerClass {
	String uuid;
	String name;
	String ip;
	Double money;
	int kills;
	int deaths;
	int playtime;
	int hours;
	String land;
	String rank;
	String subrank;
	Long joined;
	String status;

	public playerClass(String uuid, String name, String ip, Double money, int kills, int deaths, int playtime,
			int hours, String land, String rank, String subrank, Long joined, String status) {
		this.uuid = uuid;
		this.name = name;
		this.ip = ip;
		this.money = money;
		this.kills = kills;
		this.deaths = deaths;
		this.playtime = playtime;
		this.hours = hours;
		this.land = land;
		this.rank = rank;
		this.subrank = subrank;
		this.joined = joined;
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getPlaytime() {
		return playtime;
	}

	public void setPlaytime(int playtime) {
		this.playtime = playtime;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSubrank() {
		return subrank;
	}

	public void setSubrank(String subrank) {
		this.subrank = subrank;
	}

	public Long getJoined() {
		return joined;
	}

	public void setJoined(Long joined) {
		this.joined = joined;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deaths;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((joined == null) ? 0 : joined.hashCode());
		result = prime * result + kills;
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + playtime;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subrank == null) ? 0 : subrank.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		playerClass other = (playerClass) obj;
		if (deaths != other.deaths)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (joined == null) {
			if (other.joined != null)
				return false;
		} else if (!joined.equals(other.joined))
			return false;
		if (kills != other.kills)
			return false;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playtime != other.playtime)
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subrank == null) {
			if (other.subrank != null)
				return false;
		} else if (!subrank.equals(other.subrank))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "playerClass [uuid=" + uuid + ", name=" + name + ", ip=" + ip + ", money=" + money + ", kills=" + kills
				+ ", deaths=" + deaths + ", playtime=" + playtime + ", land=" + land + ", rank=" + rank + ", subrank="
				+ subrank + ", joined=" + joined + ", status=" + status + "]";
	}

}
