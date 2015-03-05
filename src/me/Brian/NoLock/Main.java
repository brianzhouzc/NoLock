package me.Brian.NoLock;

import java.util.ArrayList;
import java.util.List;
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
import org.bukkit.ChatColor;
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
		List users = new ArrayList<>();
		String titile;
		String owner = ChatColor.RED + "030";
		users.add("Brian14617481234567890");
		users.add("Raider_Soap");
		users.add("Zergpvz_141");
		titile = owner;
		if (users != null) {
			titile = titile + ChatColor.RESET;
			for (int i = 0; i < users.size(); i++) {
				if (titile.length() + users.get(i).toString().length() <= 31) {
					titile = titile + ", " + users.get(i);
				} else {
					if (titile.length() <= 28) {
						titile = titile + "...";
						System.out.println("1");
					} else {
						titile = titile.substring(0, 28) + "...";
						System.out.println("2");
					}
					i = users.size();
				}

			}
			System.out.println(titile);
			System.out.println(titile.length());
		}
		System.out.println("¡ìr".length());
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

		if (Config.EnableProtocollibNameOveride()) {
			if (getPluginManager().getPlugin("ProtocolLib") != null) {
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
									String titile;
									String owner = ChatColor.RED + Bukkit.getOfflinePlayer(UUID.fromString(Container.getOwner(rawdata))).getName();
									List<String> users = Container.getUsers(rawdata);
									titile = owner;
									if (users != null) {
										titile = titile + ChatColor.RESET;
										for (int i = 0; i < users.size(); i++) {
											if (titile.length() + Bukkit.getOfflinePlayer(UUID.fromString(users.get(i))).toString().length() <= 31) {
												titile = titile + ", " + Bukkit.getOfflinePlayer(UUID.fromString(users.get(i)));
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
			} else {
				logger.warning("[NoLock] Can't found ProtocolLib! Disabling plugin... Please install ProtocolLib or change settings in config file.");
				getPluginManager().disablePlugins();
				return;
			}
			logger.info("[NoLock] NoLock " + getDescription().getVersion() + " successfuly loaded!");
		}
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static PluginManager getPluginManager() {
		return pm;
	}
}