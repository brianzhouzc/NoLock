package me.Brian.NoLock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityBrewingStand;
import net.minecraft.server.v1_8_R1.TileEntityChest;
import net.minecraft.server.v1_8_R1.TileEntityCommand;
import net.minecraft.server.v1_8_R1.TileEntityDispenser;
import net.minecraft.server.v1_8_R1.TileEntityDropper;
import net.minecraft.server.v1_8_R1.TileEntityEnchantTable;
import net.minecraft.server.v1_8_R1.TileEntityFurnace;
import net.minecraft.server.v1_8_R1.TileEntityHopper;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		// String version = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		// System.out.println(version);
		// Class<?> c = null;
		// try {
		// c = Class.forName(version + "INamableTileEntity");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		this.getServer().getPluginManager().registerEvents(this, this);

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			setName("Test", event.getClickedBlock());
			event.getPlayer().sendMessage("success!");
			event.setCancelled(true);
		} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			event.getPlayer().sendMessage(getName(event.getClickedBlock()));
			event.setCancelled(true);
		}
	}

	public static void setName(String name, Block block) {
		final CraftWorld world = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

		if (nmsTileEntity instanceof INamableTileEntity) {
			if (nmsTileEntity instanceof TileEntityChest) {
				((TileEntityChest) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityFurnace) {
				((TileEntityFurnace) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityDispenser) {
				((TileEntityDispenser) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityDropper) {
				((TileEntityDropper) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityHopper) {
				((TileEntityHopper) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityBrewingStand) {
				((TileEntityBrewingStand) nmsTileEntity).a(name);
			} else if (nmsTileEntity instanceof TileEntityEnchantTable) {
				((TileEntityEnchantTable) nmsTileEntity).a(name);
			}
			nmsTileEntity.update();
		}
	}

	public static String getName(Block block) {
		final CraftWorld world = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

		if (nmsTileEntity instanceof INamableTileEntity) {
			if (nmsTileEntity instanceof TileEntityChest) {
				return ((TileEntityChest) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityFurnace) {
				return ((TileEntityFurnace) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityDispenser) {
				return ((TileEntityDispenser) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityDropper) {
				return ((TileEntityDropper) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityHopper) {
				return ((TileEntityHopper) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityBrewingStand) {
				return ((TileEntityBrewingStand) nmsTileEntity).getName();
			} else if (nmsTileEntity instanceof TileEntityEnchantTable) {
				return ((TileEntityEnchantTable) nmsTileEntity).getName();
			}
		}
		return null;
	}
}
