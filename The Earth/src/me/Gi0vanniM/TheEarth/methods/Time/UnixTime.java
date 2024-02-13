package me.Gi0vanniM.TheEarth.methods.Time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UnixTime {

	public String unixToDateTime(Long unixTime) {

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

		final String time = Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT+1")).format(formatter) + " (CET)";

		return time;
	}

}