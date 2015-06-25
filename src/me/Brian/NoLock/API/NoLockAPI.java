package me.Brian.NoLock.API;

import org.bukkit.block.Block;

public class NoLockAPI {

	/**
	 * @param Target block to inspect
	 * @return If that block is recognized by NoLock
	 */
	public static boolean isContainer(Block block){
		return NoLock.isContainer(block);
	}
	
	/**
	 * @param Target block to get owner
	 * @return Owner name if it is a NoLock supported container, null otherwise.
	 */
	public static String getOwner(Block block){
		if (!isContainer(block)) return null;
		NoLock nolock = new NoLock(block);
		return nolock.getOwner();
	}
	
	/**
	 * @param Target block to check owner
	 * @return True if he is the owner of the block, false otherwise.
	 */
	public static boolean isOwner(Block block, String name){
		return name.equals(getOwner(block));
	}
	
	
	
	
	
	
	
	
	
	
}
