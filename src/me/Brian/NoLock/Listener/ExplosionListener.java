package me.Brian.NoLock.Listener;

import java.util.ArrayList;
import java.util.List;

import me.Brian.NoLock.API.NoLock;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		List<Block> list = event.blockList();
		List<Block> deniedblocklist = new ArrayList<Block>();
		for (int i = 0; i < list.size(); i++) {
			Block block = list.get(i);
			if (NoLock.isContainer(block)) {
				deniedblocklist.add(block);
			}
		}
		list.removeAll(deniedblocklist);
	}
}
