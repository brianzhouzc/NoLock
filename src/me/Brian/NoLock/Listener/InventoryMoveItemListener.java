package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.Container;

import org.bukkit.block.Chest;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMoveItemListener implements Listener {

	@EventHandler
	public void onMove(InventoryMoveItemEvent event) {
		if (event.getSource().getHolder() instanceof Chest && event.getDestination().getHolder() instanceof HopperMinecart) {
			if (Container.isContainer(((Chest)event.getSource().getHolder()).getBlock())) {
				event.setCancelled(true);
			}
		}
	}
}
