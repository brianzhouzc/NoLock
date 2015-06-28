package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Config;
import me.Brian.NoLock.API.NoLock;
import net.minecraft.server.v1_8_R3.INamableTileEntity;
import net.minecraft.server.v1_8_R3.TileEntity;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		final CraftWorld craftworld = (CraftWorld) block.getWorld();
		final TileEntity nmsTileEntity = craftworld.getTileEntityAt(block.getX(), block.getY(), block.getZ());
		if (nmsTileEntity instanceof INamableTileEntity) {
			if (NoLock.isContainer(block)) {
				NoLock container = new NoLock(block);
				if (!container.isOwner(player)) {
					if (player.isOp()) {
						if (Config.AdminSnoop()) {
							// Bukkit.broadcastMessage("(Admin) [ADMIN] has broken [OWNER]'s container!".replace("[ADMIN]", player.getName()).replace("[OWNER]",
							// Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName()));
						} else {
							event.setCancelled(true);
							player.sendMessage("[NoLock] You have no permission to destroy this container.");
						}

					} else {
						event.setCancelled(true);
						player.sendMessage("[NoLock] You have no permission to destroy this container.");
					}
				}
			}

		}
	}
}
