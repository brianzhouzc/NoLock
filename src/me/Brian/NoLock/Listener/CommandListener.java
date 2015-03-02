package me.Brian.NoLock.Listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandListener implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("nolock")) {
			Player p = (Player) sender;
			p.getInventory().setItem(
					0,
					createItem(Material.CHEST, (short) 0, "{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04"
							+ "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}", ""));
		}
		return false;
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
