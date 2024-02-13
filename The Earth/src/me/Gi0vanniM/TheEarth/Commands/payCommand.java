package me.Gi0vanniM.TheEarth.Commands;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import me.Gi0vanniM.TheEarth.Main;
import me.Gi0vanniM.TheEarth.Menu.SideBarScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class payCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			if (cmd.getName().equalsIgnoreCase("payland")) {
				if (args.length <= 0) {
					sender.sendMessage("§6/payland [bedrag]");
					return false;
				}
				Player payer = (Player) sender;
				double amount = Double.parseDouble(args[0].replace(',', '.'));

				if (Main.economy.getBalance(payer) >= amount && amount >= 1) {
					String land = Main.plugin.playerCache.get(payer).getLand();

					File file = new File("plugins/TheEarthPlugin/landen", land + ".yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					double landrekening = cfg.getDouble("landrekening");

					Main.economy.withdrawPlayer(payer, amount);
					cfg.set("landrekening", landrekening + amount);

					DecimalFormat dff = new DecimalFormat("#,##0.00");
					dff.setMaximumIntegerDigits(9);
					dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
					String bedrag = dff.format(amount);

					Main.plugin.saveconf(cfg, file);

					sender.sendMessage("§7Je hebt §c§" + bedrag + "§7 betaald aan je §clandrekening§7.");
					new SideBarScoreboard().setOrUpdateScoreboard(payer);

				} else {
					sender.sendMessage("§cJe hebt niet genoeg geld om dit bedrag te betalen.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("pay")) {
				if (args.length <= 1) {
					sender.sendMessage("§6/pay [player] [bedrag]");
					return false;
				}

				Player payer = (Player) sender;
				double amount = Double.parseDouble(args[1].replace(',', '.'));
				OfflinePlayer payed = Bukkit.getOfflinePlayer(args[0]);

				if (payed.hasPlayedBefore()) {

					if (Main.economy.getBalance(payer) >= amount && amount > 99) {

						Main.economy.depositPlayer(payed, amount);
						Main.economy.withdrawPlayer(payer, amount);

						DecimalFormat dff = new DecimalFormat("#,##0.00");
						dff.setMaximumIntegerDigits(9);
						dff.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
						String bedrag = dff.format(amount);

						payer.sendMessage("§7Je hebt §c§" + bedrag + "§7 betaald aan §c" + payed.getName() + "§7.");

						new SideBarScoreboard().setOrUpdateScoreboard(payer);

						if (payed.isOnline() == true) {
							payed.getPlayer().sendMessage(
									"§7Je hebt §c§" + bedrag + "§7 ontvangen van §c" + payer.getName() + "§7.");
							new SideBarScoreboard().setOrUpdateScoreboard(payed.getPlayer());
						}

					} else {
						sender.sendMessage("§cJe hebt niet genoeg geld om dit bedrag te betalen, je moet minimaal §100,- sturen.");
					}
				} else {
					sender.sendMessage("§cKan speler niet vinden.");
				}
			}

		}
		return false;
	}

}
