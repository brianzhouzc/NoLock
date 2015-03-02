package me.Brian.NoLock;

import java.util.logging.Logger;

import me.Brian.NoLock.Listener.BlockPlaceListener;
import me.Brian.NoLock.Listener.CommandListener;
import me.Brian.NoLock.Listener.ContainersOpenListener;

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
		getServer().getLogger().info("[NoLock] Loading NoLock " + getDescription().getVersion() + "...");
		plugin = this;
		pm = getServer().getPluginManager();
		logger = getServer().getLogger();
		pm.registerEvents(new ContainersOpenListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		getCommand("nolock").setExecutor(new CommandListener());
		logger.info("[NoLock] NoLock " + getDescription().getVersion() + " successfuly loaded!");
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static PluginManager getPluginManager() {
		return pm;
	}
}