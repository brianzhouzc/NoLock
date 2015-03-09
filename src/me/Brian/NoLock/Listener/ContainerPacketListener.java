package me.Brian.NoLock.Listener;

import java.util.List;
import java.util.UUID;

import me.Brian.NoLock.API.Config;
import me.Brian.NoLock.API.NoLock;
import me.Brian.NoLock.Wrapper.WrapperPlayServerOpenWindow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class ContainerPacketListener implements PacketListener {

	public Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("NoLock");
	}

	public ListeningWhitelist getReceivingWhitelist() {
		return ListeningWhitelist.newBuilder().gamePhase(GamePhase.PLAYING).highest().types(PacketType.Play.Server.OPEN_WINDOW).build();
	}

	public ListeningWhitelist getSendingWhitelist() {
		return ListeningWhitelist.newBuilder().gamePhase(GamePhase.PLAYING).highest().types(PacketType.Play.Server.OPEN_WINDOW).build();
	}

	public void onPacketReceiving(PacketEvent e) {
		return;
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if (!(event.getPacket().getType() == PacketType.Play.Server.OPEN_WINDOW))
			return;

		WrapperPlayServerOpenWindow wp = new WrapperPlayServerOpenWindow(event.getPacket());
		String rawdata = wp.getWindowTitle().getJson().toString().replaceAll("\\\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
		Bukkit.broadcastMessage(rawdata);
		if (NoLock.isContainer(rawdata)) {
			if (NoLock.getName(rawdata) != null) {
				wp.setWindowTitle(WrappedChatComponent.fromJson("\"" + NoLock.getName(rawdata) + "\""));
			} else {
				String invtype = wp.getInventoryType();
				String rawname = null;

				if (Config.EnableProtocollibNameOverideUsersName()) {
					String owner = ChatColor.RED + Bukkit.getOfflinePlayer(UUID.fromString(NoLock.getOwner(rawdata))).getName();
					String titile = owner;
					List<String> users = NoLock.getUsers(rawdata);
					if (users != null) {
						titile = titile + ChatColor.RESET;
						for (int i = 0; i < users.size(); i++) {
							if (titile.length() + Bukkit.getOfflinePlayer(UUID.fromString(users.get(i))).getName().toString().length() <= 31) {
								titile = titile + ", " + Bukkit.getOfflinePlayer(UUID.fromString(users.get(i))).getName();
							} else {
								if (titile.length() <= 28) {
									titile = titile + "...";
								} else {
									titile = titile.substring(0, 28) + "...";
								}
								i = users.size();
							}
						}
					}
					rawname = "\"" + titile + "\"";
				} else {
					switch (invtype) {
					case "minecraft:chest":
						rawname = "{\"translate\":\"container.chest\"}";
						break;
					case "minecraft:furnace":
						rawname = "{\"translate\":\"container.furnace\"}";
						break;
					case "minecraft:dispenser":
						rawname = "{\"translate\":\"container.dispenser\"}";
						break;
					case "minecraft:dropper":
						rawname = "{\"translate\":\"container.dropper\"}";
						break;
					case "minecraft:brewing_stand":
						rawname = "{\"translate\":\"container.brewing\"}";
						break;
					case "minecraft:enchanting_table":
						rawname = "{\"translate\":\"container.enchant\"}";
						break;
					default:
						rawname = "\"Container\"";
						break;
					}
				}
				wp.setWindowTitle(WrappedChatComponent.fromJson(rawname));
			}
		}
	}
}