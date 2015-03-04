package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Container;
import me.Brian.NoLock.API.Config;

import net.minecraft.server.v1_8_R1.INamableTileEntity;
import net.minecraft.server.v1_8_R1.TileEntity;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			final CraftWorld world = (CraftWorld) block.getWorld();
			final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());
			if (event.getPlayer().isSneaking()) {
				event.getPlayer().sendMessage(Container.getRawData(event.getClickedBlock()));
				return;
			}
			if (nmsTileEntity instanceof INamableTileEntity) {
				if (Container.isContainer(block)) {
					Container container = new Container(block);
					if (!container.getOwner().equalsIgnoreCase(player.getUniqueId().toString()) && !container.getUsers().contains(player.getUniqueId().toString())) {
						event.setCancelled(true);
						player.sendMessage("[NoLock] You have no permission to open this container.");
					}
					if (container.isOwner(player)) {
						player.sendMessage("owner");
					}

					if (container.isUser(player)) {
						player.sendMessage("user");
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && player.isSneaking()) {
			Block block = event.getClickedBlock();
			final CraftWorld world = (CraftWorld) block.getWorld();
			final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());

			if (Config.EnableQuickProtection()) {
				if (player.getItemInHand().getType() == Config.getQuickProtectMaterial()) {
					if (!Container.isContainer(block) && nmsTileEntity instanceof INamableTileEntity) {
						Container.setRawData(block, player.getUniqueId().toString(), null, null);
						event.setCancelled(true);
						player.sendMessage("[NoLock] You protected this container!");
						// return;
					}
				}
			}

			if (event.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)) {
				if (Container.setRawData(event.getClickedBlock(), "{\"Owner\":\"" + event.getPlayer().getUniqueId().toString()
						+ "\",\"Users\":[\"69abcfbf-991d-42a3-8c1d-10787eae7949\",\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}")) {
					event.getPlayer().sendMessage("success! set owner to " + event.getPlayer().getUniqueId().toString());
					event.setCancelled(true);
				}
			} else if (event.getPlayer().getItemInHand().getType().equals(Material.STRING)) {
				if (Container.setRawData(event.getClickedBlock(), "{\"Owner\":\"" + "69abcfbf-991d-42a3-8c1d-10787eae7949" + "\",\"Users\":[\"" + event.getPlayer().getUniqueId().toString()
						+ "\",\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}")) {
					event.getPlayer().sendMessage("success! set users to " + event.getPlayer().getUniqueId().toString());
					event.setCancelled(true);
				}
			} else if (event.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR)) {
				if (Container.setRawData(event.getClickedBlock(), "{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04"
						+ "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}")) {
					event.getPlayer().sendMessage("success! set user and owner to someone else");
					event.setCancelled(true);
				}
			}
		}

	}
}
