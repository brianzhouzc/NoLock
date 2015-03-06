package me.Brian.NoLock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

//import net.minecraft.server.v1_8_R1.INamableTileEntity;
//import net.minecraft.server.v1_8_R1.TileEntity;
//import net.minecraft.server.v1_8_R1.TileEntityBrewingStand;
//import net.minecraft.server.v1_8_R1.TileEntityChest;
//import net.minecraft.server.v1_8_R1.TileEntityDispenser;
//import net.minecraft.server.v1_8_R1.TileEntityDropper;
//import net.minecraft.server.v1_8_R1.TileEntityEnchantTable;
//import net.minecraft.server.v1_8_R1.TileEntityFurnace;
//import net.minecraft.server.v1_8_R1.TileEntityHopper;

import org.bukkit.block.Block;
//import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Reflect extends JavaPlugin implements Listener {
	static String version;
	static String minecraft = "net.minecraft.server.";
	static String bukkit = "org.bukkit.craftbukkit.";

	public void onEnable() {
		Reflect.version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			// setName("Test", event.getClickedBlock());
			event.getPlayer().sendMessage("success!");
			event.setCancelled(true);
		} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			event.getPlayer().sendMessage(getName(event.getClickedBlock()));
			event.setCancelled(true);
		}
	}

	public static void setName(String name, Block block) {
		block.getZ();
		block.getWorld();
		try {
			Class.forName(bukkit + version + ".CraftWorld");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// final CraftWorld world = (CraftWorld) block.getWorld();
		// final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

		// if (nmsTileEntity instanceof INamableTileEntity) {
		// if (nmsTileEntity instanceof TileEntityChest) {
		// ((TileEntityChest) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityFurnace) {
		// ((TileEntityFurnace) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityDispenser) {
		// ((TileEntityDispenser) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityDropper) {
		// ((TileEntityDropper) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityHopper) {
		// ((TileEntityHopper) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityBrewingStand) {
		// ((TileEntityBrewingStand) nmsTileEntity).a(name);
		// } else if (nmsTileEntity instanceof TileEntityEnchantTable) {
		// ((TileEntityEnchantTable) nmsTileEntity).a(name);
		// }
		// nmsTileEntity.update();
		// }
	}

	public static String getName(Block block) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int x = block.getX(), y = block.getY(), z = block.getZ();
		World world = block.getWorld();
		try {
			Class<?> clCraftWorld = Class.forName(bukkit + version + ".CraftWorld");
			Method mGetTile = clCraftWorld.getMethod("getTileEntityAt", Integer.TYPE, Integer.TYPE, Integer.TYPE);

			Class<?> clTileEntity = Class.forName(minecraft + version + ".INamableTileEntity");
			Method mGetName = clTileEntity.getMethod("getName");

			// if (mGetTile.invoke(world, x, y, z) instanceof INamableTileEntity) {
			try {
				return (String) mGetName.invoke(mGetTile.invoke(world, x, y, z));
			} catch (Exception e) {
			}
			// }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
