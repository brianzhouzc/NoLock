package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Container;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityChest;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		final CraftWorld craftworld = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = craftworld.getTileEntityAt(block.getX(), block.getY(), block.getZ());
		if (nmsTileEntity instanceof TileEntityChest) {
			Player player = event.getPlayer();
			if (Container.isContainer(event.getBlock())) {
				event.setCancelled(true);
				return;
			}
			World world = player.getWorld();
			Block block1 = world.getBlockAt(block.getX() + 1, block.getY(), block.getZ());
			Block block2 = world.getBlockAt(block.getX() - 1, block.getY(), block.getZ());
			Block block3 = world.getBlockAt(block.getX(), block.getY(), block.getZ() + 1);
			Block block4 = world.getBlockAt(block.getX(), block.getY(), block.getZ() - 1);
			if (Container.isContainer(block1)) {
				if (Container.isOwner(block1, player)) {
					Container.setRawData(block, Container.getRawData(block1));
				} else if (block.getType().equals(block1.getType())) {
					event.setCancelled(true);
				}
			} else if (Container.isContainer(block2)) {
				if (Container.isOwner(block2, player)) {
					Container.setRawData(block, Container.getRawData(block2));
				} else if (block.getType().equals(block2.getType())) {
					event.setCancelled(true);
				}
			} else if (Container.isContainer(block3)) {
				if (Container.isOwner(block3, player)) {
					Container.setRawData(block, Container.getRawData(block3));
				} else if (block.getType().equals(block3.getType())) {
					event.setCancelled(true);
				}
			} else if (Container.isContainer(block4)) {
				if (Container.isOwner(block4, player)) {
					Container.setRawData(block, Container.getRawData(block4));
				} else if (block.getType().equals(block4.getType())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
