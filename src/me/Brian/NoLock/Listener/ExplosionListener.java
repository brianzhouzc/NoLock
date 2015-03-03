package me.Brian.NoLock.Listener;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		List<Block> list = event.blockList();
		for (int i = 0; i < list.size(); i++) {

		}
	}
}
