package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.NoLock;
import net.minecraft.server.v1_8_R3.INamableTileEntity;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.TileEntityHopper;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
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

			if (NoLock.isContainer(event.getBlock())) {
				event.setCancelled(true);
				return;
			}

			if (nmsTileEntity instanceof TileEntityChest) {
				Block blockrelated[] = { block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.SOUTH), block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.WEST) };
				for (int i = 0; i < blockrelated.length; i++) {
					if (NoLock.isContainer(blockrelated[i])) {
						// player.sendMessage("isContainer");
						if (NoLock.isOwner(blockrelated[i], player)) {
							// player.sendMessage("isOwner");
							if (block.getType() == blockrelated[i].getType()) {
								// player.sendMessage("sameType");
								NoLock.setRawData(block, NoLock.getRawData(blockrelated[i]));
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
					if (NoLock.isContainer(blockrelated[i])) {
						if (!NoLock.isOwner(blockrelated[i], player) && !NoLock.isUser(blockrelated[i], player)) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
