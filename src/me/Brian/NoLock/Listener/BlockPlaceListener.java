package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Container;
import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;
import net.minecraft.server.v1_8_R1.TileEntityChest;
import net.minecraft.server.v1_8_R1.TileEntityHopper;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
		if (nmsTileEntity instanceof INamableTileEntity) {
			Player player = event.getPlayer();

			if (Container.isContainer(event.getBlock())) {
				event.setCancelled(true);
				return;
			}

			if (nmsTileEntity instanceof TileEntityChest) {
				Block blockrelated[] = { block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.SOUTH), block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.WEST) };
				for (int i = 0; i < blockrelated.length; i++) {
					if (Container.isContainer(blockrelated[i])) {
						// player.sendMessage("isContainer");
						if (Container.isOwner(blockrelated[i], player)) {
							// player.sendMessage("isOwner");
							if (block.getType() == blockrelated[i].getType()) {
								// player.sendMessage("sameType");
								Container.setRawData(block, Container.getRawData(blockrelated[i]));
							}
						} else if (block.getType() == blockrelated[i].getType()) {
							// player.sendMessage("setCancelled");
							event.setCancelled(true);
						}
					}
				}
			}

			if (nmsTileEntity instanceof TileEntityHopper) {
				Block blockrelated[] = { block.getRelative(BlockFace.UP), block.getRelative(BlockFace.DOWN), block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.SOUTH),
						block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.WEST) };

				for (int i = 0; i < blockrelated.length; i++) {
					if (Container.isContainer(blockrelated[i])) {
						if (!Container.isOwner(blockrelated[i], player) && !Container.isUser(blockrelated[i], player)) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
