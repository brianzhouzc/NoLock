package me.Brian.NoLock;

import java.util.UUID;
import java.util.logging.Logger;

import me.Brian.NoLock.API.Config;
import me.Brian.NoLock.API.Container;
import me.Brian.NoLock.Listener.BlockBreakListener;
import me.Brian.NoLock.Listener.BlockPlaceListener;
import me.Brian.NoLock.Listener.CommandListener;
import me.Brian.NoLock.Listener.PlayerInteractListener;
import me.Brian.NoLock.Listener.ExplosionListener;
import me.Brian.NoLock.Wrapper.WrapperPlayServerOpenWindow;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class Main extends JavaPlugin implements Listener {
	static Plugin plugin;
	static PluginManager pm = null;
	static Logger logger = null;

	public static void main(String[] args) {
		System.out.println("{\"Owner\":\"" + "fa3c1f7a-f18b-4629-b077-4e7a2c333f04" + "\",\"Users\":[\"9e550853-9826-40d4-b5d5-29f5653aaf0e\",\"fa3c1f7a-f18b-4629-b077-4e7a2c333f04\"]}");
	}

	public void onEnable() {
		plugin = this;
		pm = getServer().getPluginManager();
		logger = getServer().getLogger();
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new ExplosionListener(), this);
		pm.registerEvents(new BlockBreakListener(), this);
		getCommand("nolock").setExecutor(new CommandListener());
		saveDefaultConfig();
		logger.info(Config.EnableProtocollibNameOveride() + "");
		logger.info(Config.EnableProtocollibNameOverideUsersName() + "");
		// logger.info(org.bukkit.Bukkit.getOfflinePlayer(UUID.fromString("97f10862-7eef-4755-979e-238253cf4677")).getName());
		logger.info("[NoLock] NoLock " + getDescription().getVersion() + " successfuly loaded!");

		if (Config.EnableProtocollibNameOveride()) {
			ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener() {
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
				public void onPacketSending(PacketEvent e) {
					if (!(e.getPacket().getType() == PacketType.Play.Server.OPEN_WINDOW))
						return;

					WrapperPlayServerOpenWindow wp = new WrapperPlayServerOpenWindow(e.getPacket());
					String rawdata = wp.getWindowTitle().getJson().toString().replaceAll("\\\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
					Bukkit.broadcastMessage(rawdata);
					// Bukkit.broadcastMessage(wp.getInventoryType());
					if (Container.isContainer(rawdata)) {
						if (Container.getName(rawdata) != null) {
							wp.setWindowTitle(WrappedChatComponent.fromJson("\"" + Container.getName(rawdata) + "\""));
						} else {
							String invtype = wp.getInventoryType();
							String rawname = null;

							if (Config.EnableProtocollibNameOverideUsersName()) {
								rawname = "\"" + Bukkit.getOfflinePlayer(UUID.fromString(Container.getOwner(rawdata))).getName() + "\"";
							} else {
								if (invtype.equalsIgnoreCase("minecraft:chest")) {
									rawname = "{\"translate\":\"container.chest\"}";
								} else if (invtype.equalsIgnoreCase("minecraft:furnace")) {
									rawname = "{\"translate\":\"container.furnace\"}";
								} else if (invtype.equalsIgnoreCase("minecraft:dispenser")) {
									rawname = "{\"translate\":\"container.dispenser\"}";
								} else if (invtype.equalsIgnoreCase("minecraft:dropper")) {
									rawname = "{\"translate\":\"container.dropper\"}";
								} else if (invtype.equalsIgnoreCase("minecraft:brewing_stand")) {
									rawname = "{\"translate\":\"container.brewing\"}";
								} else if (invtype.equalsIgnoreCase("minecraft:enchanting_table")) {
									rawname = "{\"translate\":\"container.enchant\"}";
								} else {
									rawname = "\"Container\"";
								}
							}
							wp.setWindowTitle(WrappedChatComponent.fromJson(rawname));
						}
					}
				}
			});
		}
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static PluginManager getPluginManager() {
		return pm;
	}
}