package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Container;
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

public class ContainersOpenListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().isSneaking()) {
				event.getPlayer().sendMessage(Container.getRawData(event.getClickedBlock()));
				return;
			}
			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			final CraftWorld world = (CraftWorld) block.getWorld();
			final TileEntity nmsTileEntity = world.getTileEntityAt(block.getX(), block.getY(), block.getZ());
			if (nmsTileEntity instanceof INamableTileEntity) {
				if (Container.isContainer(block)) {
					Container container = new Container(block);
					if (!container.getOwner().equalsIgnoreCase(player.getUniqueId().toString()) && !container.getUsers().contains(player.getUniqueId().toString())) {
						event.setCancelled(true);
						player.sendMessage("[NoLock] You have no permission to open this container.");
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
			if (event.getPlayer().getItemInHand().getType().equals(Material.STICK)) {
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
			} else {
				if (Container.setRawData(event.getClickedBlock(), "{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04"
						+ "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}")) {
					event.getPlayer().sendMessage("success! set user and owner to someone else");
					event.setCancelled(true);
				}
			}
		}

	}
}
