package me.Gi0vanniM.TheEarth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Gi0vanniM.TheEarth.kolonies.kolonieMethods;

public class debugCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("dev.debug")) {
			if (cmd.getName().equalsIgnoreCase("tedebug")) {

				if (args[0].equalsIgnoreCase("koloniecache")) {
					new kolonieMethods().showAllCachedKolonies(sender);
				}

			}
		} else if (cmd.getName().equalsIgnoreCase("tedebug")) {
			if (args[0].equalsIgnoreCase("giovanni")) {
				sender.sendMessage("You found an easter egg. You must be so smart!");
			}
		}
		return false;
	}

}
