package me.Brian.NoLock.Listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import me.Brian.NoLock.API.NoLock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandListener implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("nolock")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				if (args.length == 0) {

					sender.sendMessage("��6[NoLock] ====== NoLock ======");
				} else {
					if (args[0].equalsIgnoreCase("lock")) {
						Block block = player.getTargetBlock(null, 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (!NoLock.isContainer(block)) {
								if (NoLock.setRawData(block, player.getUniqueId().toString(), null, null, null)) {
									player.sendMessage("��6[NoLock] ��cYou locked this container!");
								}
							} else {
								player.sendMessage("��6[NoLock] ��cThis container is already locked!");
							}
						} else {
							player.sendMessage("��6[NoLock] ��cTarget block can't be lock!");
						}

					} else if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage("yo3");

						Block block = player.getTargetBlock(null, 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (NoLock.isContainer(block)) {
								NoLock container = new NoLock(block);
								player.sendMessage("��6[NoLock] ��cShowing information of your target container..");
								player.sendMessage("��6[NoLock] ��cThis container is locked by ��a" + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName());
								if (container.getUsers() != null) {
									player.sendMessage("��6[NoLock] ��cThere are currently ��a" + container.getUsers().size() + " ��cuser(s) on this container:");
									for (String uuid : container.getUsers()) {
										player.sendMessage("��6[NoLock] ��a" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
									}
								} else {
									player.sendMessage("��6[NoLock] ��cThere are currently no user on this container.");
								}
							} else {
								player.sendMessage("��6[NoLock] ��cTarget container isn't locket yet!");
							}
						} else {
							player.sendMessage("��6[NoLock] ��cTarget block can't be a container!");
						}
					} else if (args[0].equalsIgnoreCase("user")) {
						if (args.length == 1) {

						} else if (args.length >= 2) {
							Block block = player.getTargetBlock(null, 5);
							if (args[1].equalsIgnoreCase("add")) {
								// if (NoLock.isNamableTileEntity(block)) {
								// if (NoLock.isContainer(block)) {
								// NoLock container = new NoLock(block);
								// if (container.getOwner().equalsIgnoreCase(player.getUniqueId().toString())) {
								// List<String> unknowusers = new ArrayList<String>();
								// List<String> successusers = new ArrayList<String>();
								// List<String> successuuid = new ArrayList<String>();
								//
								// for (int i = 2; i < args.length; i++) {
								// if (Bukkit.getPlayerExact(args[i]) != null) {
								// if (container.getUsers() != null) {
								// if (!container.getUsers().contains(Bukkit.getPlayerExact(args[i]).getUniqueId().toString())) {
								// successusers.add(args[i]);
								// successuuid.add(Bukkit.getPlayerExact(args[i]).getUniqueId().toString());
								// } else {
								// unknowusers.add(args[i]);
								// }
								// } else {
								// successusers.add(args[i]);
								// successuuid.add(Bukkit.getPlayerExact(args[i]).getUniqueId().toString());
								// }
								//
								// } else {
								// unknowusers.add(args[i]);
								// }
								// }
								// if (successuuid.size() != 0) {
								// if (container.addUsers(successuuid)) {
								// player.sendMessage("w");
								// }
								// }
								// player.sendMessage(successusers.toString());
								// player.sendMessage(successuuid.toString());
								// player.sendMessage(unknowusers.toString());
								//
								// } else {
								// player.sendMessage("��6[NoLock] ��cYou have no permission to edit ��c" + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName()
								// + "'s container!");
								// }
								// } else {
								// player.sendMessage("��6[NoLock] ��cTarget container isn't locket yet!");
								// }
								// } else {
								// player.sendMessage("��6[NoLock] ��cTarget block can't be a container!");
								// }
							}
						}
					}

				}
				// Player p = (Player) sender;
				// p.getInventory().setItem(
				// 0,
				// createItem(Material.CHEST, (short) 0, "{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04"
				// + "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}", ""));
			}
		}
		return true;
	}

	public ItemStack createItem(Material material, short damage, String name, String lore) {
		ItemStack item = new ItemStack(material, 1, damage);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.equals("") && lore != null) {
			ArrayList<String> Lore = new ArrayList<String>();
			if (lore.contains("|")) {
				for (String alore : lore.split("\\|")) {
					Lore.add(alore);
				}
			} else {
				Lore.add(lore);
			}
			meta.setLore(Lore);
		}
		item.setItemMeta(meta);
		return item;
	}

}
