package me.Brian.NoLock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityBrewingStand;
import net.minecraft.server.v1_8_R1.TileEntityChest;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	static Plugin plugin;

	public static void main(String[] args) {
		Container container = new Container(
				"{\"Owner\":\"97f10862-7eef-4755-979e-238253cf4677\",\"Users\":[\"69abcfbf-991d-42a3-8c1d-10787eae7949\",\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}");
		// System.out.println(container.getRawData());
		// System.out.println(container.getOwner());
		// System.out.println(container.getUsers());
		List users = new ArrayList<>();
		users.add("test");
		users.add("test2");
		Container.addUsers(null, "{\"Owner\":\"97f10862-7eef-4755-979e-238253cf4677\"}", users);
	}

	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(this, this);
		// Gson gson = new Gson();
		// User user = gson.fromJson("{\"name\":\"test\",\"UUID\":\"test1\"}", User.class);
		// System.out.println(user);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			if (Container.addUsers(event.getClickedBlock(), event.getPlayer().getName(), null)) {
				event.getPlayer().sendMessage("success!");
				event.setCancelled(true);
			}
			// Container.setRawData(event.getClickedBlock(), event.getPlayer().getUniqueId().toString(), null, null);
			// event.getPlayer().sendMessage("success!");
			// event.setCancelled(true);
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
			return ((INamableTileEntity) nmsTileEntity).getName();
		}
		return null;
	}

	public static Plugin getPlugin() {
		return plugin;
	}
}