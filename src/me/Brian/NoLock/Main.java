package me.Brian.NoLock;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.Brian.NoLock.API.Container;
import me.Brian.NoLock.Listener.ContainersOpenListener;
import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityBrewingStand;
import net.minecraft.server.v1_8_R1.TileEntityChest;
import net.minecraft.server.v1_8_R1.TileEntityDispenser;
import net.minecraft.server.v1_8_R1.TileEntityDropper;
import net.minecraft.server.v1_8_R1.TileEntityEnchantTable;
import net.minecraft.server.v1_8_R1.TileEntityFurnace;
import net.minecraft.server.v1_8_R1.TileEntityHopper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	static Plugin plugin;
	static PluginManager pm = null;
	static Logger logger = null;

	public static void main(String[] args) {

		// Container container = new Container(
		// "{\"Owner\":\"97f10862-7eef-4755-979e-238253cf4677\",\"Users\":[\"69abcfbf-991d-42a3-8c1d-10787eae7949\",\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}");
		// // System.out.println(container.getRawData());
		// System.out.println(container.getOwner());
		// System.out.println(container.getUsers());
		List<String> users = new ArrayList<String>();
		users.add("test");
		users.add("test2");
		Container.addUsers(null, "{\"Owner\":\"97f10862-7eef-4755-979e-238253cf4677\"}", users);
	}

	public void onEnable() {
		plugin = this;
		pm = getServer().getPluginManager();
		logger = getServer().getLogger();
		pm.registerEvents(new ContainersOpenListener(), this);
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static PluginManager getPluginManager() {
		return pm;
	}
}