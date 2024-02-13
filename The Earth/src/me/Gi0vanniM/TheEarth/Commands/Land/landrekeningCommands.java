package me.Gi0vanniM.TheEarth.Commands.Land;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import me.Gi0vanniM.TheEarth.methods.LandMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class landrekeningCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// @formatter:off
		String help ="§6Landrekening commands: \n"
					+ "/landrekening take [bedrag] \n"
					+ "/landrekening belasting [max 50%]";
		// @formatter:on
		if (sender instanceof Player) {
			if (Main.plugin.playerCache.get((Player) sender).getRank().equalsIgnoreCase("Koning")
					|| Main.plugin.playerCache.get((Player) sender).getRank().equalsIgnoreCase("President")) {

				if (sender instanceof Player) {
					if (cmd.getName().equalsIgnoreCase("landrekening")) {
						if (args.length <= 0) {
							sender.sendMessage(help);
							return false;
						}
						String land = Main.plugin.playerCache.get((Player) sender).getLand();

						if (args[0].equalsIgnoreCase("take")) {
							double bedrag = Double.parseDouble(args[1]);
							double landrekening = new LandMethods().getLandrekening(land);

							if (bedrag <= landrekening) {
								
								new LandMethods().takeLandGeld(land, bedrag);
								Main.plugin.economy.depositPlayer((Player) sender, bedrag);
								
								DecimalFormat dff = new DecimalFormat("#,##0.00");
								dff.setMaximumIntegerDigits(9);
								dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));

								sender.sendMessage("§7Je hebt §c§" + dff.format(bedrag) + "§7 van de landrekening af gehaald.");
								new SideBarScoreboard().setOrUpdateScoreboard((Player) sender);
							} else {
								sender.sendMessage("§cEr staat niet genoeg geld op de landrekening.");
							}

							return false;
						}

						if (args[0].equalsIgnoreCase("belasting")) {
							int per = Integer.parseInt(args[1]);
							if (per <= 50 && per >= 0) {
								new LandMethods().setLandbelasting(land, per);
								sender.sendMessage("§7Je hebt de land belasting op §c" + per + "%§7 gezet.");
							} else {
								sender.sendMessage("§cJe kunt de belasting maximaal op 50% zetten.");
							}
							return false;
						}

					}
				}
			} else {
				sender.sendMessage("§cJe hebt geen toegang tot dit command.");
			}
		}
		return false;
	}

}
