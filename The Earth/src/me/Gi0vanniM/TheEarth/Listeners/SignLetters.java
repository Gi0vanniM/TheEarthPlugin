package me.Gi0vanniM.TheEarth.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignLetters implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent e) {

		if (!e.getLine(0).equals(null)) {

			String line = e.getLine(0).toUpperCase();
			String color = LetterColor(e.getLine(1).toUpperCase());

			// █
			switch (line) {
			case ":A:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "######");
				e.setLine(3, color + "##   ##");
				break;
			case ":B:":
				e.setLine(0, color + "####    ");
				e.setLine(1, color + "#####  ");
				e.setLine(2, color + "##   ## ");
				e.setLine(3, color + "#####  ");
				break;
			case ":C:":
				e.setLine(0, color + "#####");
				e.setLine(1, color + "##      ");
				e.setLine(2, color + "##      ");
				e.setLine(3, color + "####");
				break;
			case ":D:":
				e.setLine(0, color + "#####  ");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "#####  ");
				break;
			case ":E:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "####   ");
				e.setLine(2, color + "##      ");
				e.setLine(3, color + "######");
				break;
			case ":F:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "####   ");
				e.setLine(2, color + "##      ");
				e.setLine(3, color + "##      ");
				break;
			case ":G:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##      ");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "####");
				break;
			case ":H:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "######");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "##   ##");
				break;
			case ":I:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##");
				e.setLine(2, color + "##");
				e.setLine(3, color + "####");
				break;
			case ":J:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "      ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "####");
				break;
			case ":K:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "####   ");
				e.setLine(2, color + "## ##  ");
				e.setLine(3, color + "##   ##");
				break;
			case ":L:":
				e.setLine(0, color + "##      ");
				e.setLine(1, color + "##      ");
				e.setLine(2, color + "##      ");
				e.setLine(3, color + "######");
				break;
			case ":M:":
				e.setLine(0, color + "## ##");
				e.setLine(1, color + "## # ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "##   ##");
				break;
			case ":N:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "### ##");
				e.setLine(2, color + "## ###");
				e.setLine(3, color + "##   ##");
				break;
			case ":O:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "####");
				break;
			case ":P:":
				e.setLine(0, color + "#####  ");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "#####  ");
				e.setLine(3, color + "##      ");
				break;
			case ":Q:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "  #####");
				break;
			case ":R:":
				e.setLine(0, color + "#####  ");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "#####  ");
				e.setLine(3, color + "##   ##");
				break;
			case ":S:":
				e.setLine(0, color + "  #####");
				e.setLine(1, color + "###   ");
				e.setLine(2, color + "   ###");
				e.setLine(3, color + "#####   ");
				break;
			case ":T:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "##");
				e.setLine(2, color + "##");
				e.setLine(3, color + "##");
				break;
			case ":U:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "####");
				break;
			case ":V:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "####");
				e.setLine(3, color + "##");
				break;
			case ":W:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "## # ##");
				e.setLine(3, color + "## ##");
				break;
			case ":X:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "##");
				e.setLine(2, color + "##");
				e.setLine(3, color + "##   ##");
				break;
			case ":Y:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "####");
				e.setLine(2, color + "##");
				e.setLine(3, color + "##");
				break;
			case ":Z:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "   ##");
				e.setLine(2, color + "##   ");
				e.setLine(3, color + "######");
				break;
			case ":0:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "## ###");
				e.setLine(2, color + "### ##");
				e.setLine(3, color + "####");
				break;
			case ":1:":
				e.setLine(0, color + "##");
				e.setLine(1, color + "### ");
				e.setLine(2, color + "##");
				e.setLine(3, color + "##");
				break;
			case ":2:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "#   ##");
				e.setLine(2, color + "##");
				e.setLine(3, color + "######");
				break;
			case ":3:":
				e.setLine(0, color + "#####");
				e.setLine(1, color + "    ####");
				e.setLine(2, color + "       ##");
				e.setLine(3, color + "#####");
				break;
			case ":4:":
				e.setLine(0, color + "##   ##");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + "  #####");
				e.setLine(3, color + "      ##");
				break;
			case ":5:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "####   ");
				e.setLine(2, color + "     ###");
				e.setLine(3, color + "##### ");
				break;
			case ":6:":
				e.setLine(0, color + "    ######");
				e.setLine(1, color + "####    ");
				e.setLine(2, color + "##    ##");
				e.setLine(3, color + "####");
				break;
			case ":7:":
				e.setLine(0, color + "######");
				e.setLine(1, color + "   ##");
				e.setLine(2, color + "##");
				e.setLine(3, color + "##");
				break;
			case ":8:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "#####");
				e.setLine(2, color + "##   ##");
				e.setLine(3, color + "####");
				break;
			case ":9:":
				e.setLine(0, color + "####");
				e.setLine(1, color + "##   ##");
				e.setLine(2, color + " ###");
				e.setLine(3, color + "###   ");
				break;
			}

		}

	}

	public String LetterColor(String string) {

		switch (string) {
		case "0":
			return "§0";
		case "1":
			return "§1";
		case "2":
			return "§2";
		case "3":
			return "§3";
		case "4":
			return "§4";
		case "5":
			return "§5";
		case "6":
			return "§6";
		case "7":
			return "§7";
		case "8":
			return "§8";
		case "9":
			return "§9";
		case "A":
			return "§A";
		case "B":
			return "§B";
		case "C":
			return "§C";
		case "D":
			return "§D";
		case "E":
			return "§E";
		case "F":
			return "§F";
		case "K":
			return "§K";

		default:
return "§r";
		}

	}

}