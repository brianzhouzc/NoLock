package me.Brian.NoLock.Listener;

import me.Brian.NoLock.API.NoLock;

import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMoveItemListener implements Listener {

	@EventHandler
	public void onMove(InventoryMoveItemEvent event) {
		InventoryHolder source = event.getSource().getHolder();
		InventoryHolder destination = event.getDestination().getHolder();
		if (source instanceof Chest || source instanceof Furnace || source instanceof Dispenser || source instanceof Dropper || source instanceof Hopper || source instanceof BrewingStand) {
			if (NoLock.isContainer((Block) source)) {
				if (destination instanceof Chest || destination instanceof Furnace || destination instanceof Dispenser || destination instanceof Dropper || destination instanceof Hopper
						|| destination instanceof BrewingStand) {
					if (NoLock.isContainer((Block) destination)) {
						NoLock sourcel = new NoLock((Block) source);
						NoLock destinationl = new NoLock((Block) destination);
						if (sourcel.getOwner().equalsIgnoreCase(destinationl.getOwner())) {
							return;
						}
					}
				}
			} else {
				return;
			}
		}
		event.setCancelled(true);
		// if (event.getSource().getHolder() instanceof Chest && event.getDestination().getHolder() instanceof HopperMinecart) {
		// if (NoLock.isContainer(((Chest) event.getSource().getHolder()).getBlock())) {
		// event.setCancelled(true);
		// }
		// }
	}
}
