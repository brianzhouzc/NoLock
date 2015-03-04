package me.Brian.NoLock;

import java.util.UUID;
import java.util.logging.Logger;

import me.Brian.NoLock.Listener.BlockBreakListener;
import me.Brian.NoLock.Listener.BlockPlaceListener;
import me.Brian.NoLock.Listener.CommandListener;
import me.Brian.NoLock.Listener.PlayerInteractListener;
import me.Brian.NoLock.Listener.ExplosionListener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	static Plugin plugin;
	static PluginManager pm = null;
	static Logger logger = null;

	public static void main(String[] args) {
		System.out.println("{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04" + "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}");
	}

	public void onEnable() {
		plugin = this;
		pm = getServer().getPluginManager();
		logger = getServer().getLogger();
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new ExplosionListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		getCommand("nolock").setExecutor(new CommandListener());
		saveDefaultConfig();
//		logger.info(org.bukkit.Bukkit.getOfflinePlayer(UUID.fromString("97f10862-7eef-4755-979e-238253cf4677")).getName());
		logger.info("[NoLock] NoLock " + getDescription().getVersion() + " successfuly loaded!");
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static PluginManager getPluginManager() {
		return pm;
	}
}