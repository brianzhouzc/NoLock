package me.Brian.NoLock.Listener;

import java.util.ArrayList;
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

				switch (args.length) {
				case 0:
					sender.sendMessage("[NoLock] ====== NoLock ======");
					break;
				case 1:
					if (args[0].equalsIgnoreCase("lock")) {
						Block block = player.getTargetBlock(null, 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (!NoLock.isContainer(block)) {
								if (NoLock.setRawData(block, player.getUniqueId().toString(), null, null, null)) {
									player.sendMessage("[NoLock] You locked this container!");
								}
							} else {
								player.sendMessage("[NoLock] This container is already locked!");
							}
						} else {
							player.sendMessage("[NoLock] Target block can't be lock!");
						}

					} else if (args[0].equalsIgnoreCase("info")) {
						Block block = player.getTargetBlock(null, 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (NoLock.isContainer(block)) {
								NoLock container = new NoLock(block);
								player.sendMessage("[NoLock] This container is locked by " + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())));
							}
						}
					} else if (args[0].equalsIgnoreCase("user")) {
						if (args.length == 1) {

						} else if (args.length == 2) {
							if (args[1].equalsIgnoreCase("add")) {

							}
						}
					}
				default:
					break;
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
