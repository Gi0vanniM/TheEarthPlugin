package me.Gi0vanniM.TheEarth.methods;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public void saveconf(FileConfiguration cfg, File file) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getFiles(String path, String prefix) {
		
		File dir = new File(path);
		File[] files = dir.listFiles();   // all files in dir
		
		files = dir.listFiles((FileFilter) new PrefixFileFilter(prefix, IOCase.INSENSITIVE)); 
//		Bukkit.broadcastMessage(showFileNames(files) + "");
		
		List<String> mm = new ArrayList<String>();
		for (File file : files) {
			mm.add(file.getName());
		}
		return mm;
	}
	
	public List<String> getFiles2(String path, String suffix) {
		
		File dir = new File(path);
		File[] files = dir.listFiles();   // all files in dir
		
		files = dir.listFiles((FileFilter) new SuffixFileFilter(suffix, IOCase.INSENSITIVE));
//		Bukkit.broadcastMessage(showFileNames(files) + "");
		
		List<String> mm = new ArrayList<String>();
		for (File file : files) {
			mm.add(file.getName());
		}
		return mm;
	}
	
	public List<String> showFileNames(File[] files) {
		List<String> mm = new ArrayList<String>();
		for (File file : files) {
			mm.add(file.getName());
		}
		return mm;
	}
	
}