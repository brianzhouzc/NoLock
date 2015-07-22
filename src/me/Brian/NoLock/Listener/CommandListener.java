package me.Brian.NoLock.Listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import me.Brian.NoLock.API.NoLock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
					sender.sendMessage("§6[NoLock] ====== NoLock ======");
				} else {
					if (args[0].equalsIgnoreCase("lock")) {
						Block block = player.getTargetBlock(new HashSet<Material>(), 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (!NoLock.isContainer(block)) {
								if (NoLock.setRawData(block, player.getUniqueId().toString(), null, null, null)) {
									player.sendMessage("§6[NoLock] §cYou locked this container!");
								}
							} else {
								player.sendMessage("§6[NoLock] §cThis container is already locked!");
							}
						} else {
							player.sendMessage("§6[NoLock] §cTarget block can't be lock!");
						}

					} else if (args[0].equalsIgnoreCase("info")) {
						Block block = player.getTargetBlock(new HashSet<Material>(), 5);
						if (NoLock.isNamableTileEntity(block)) {
							if (NoLock.isContainer(block)) {
								NoLock container = new NoLock(block);
								player.sendMessage("§6[NoLock] §cShowing information of your target container..");
								player.sendMessage("§6[NoLock] §cThis container is locked by §a" + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName());
								if (container.getUsers() != null) {
									String currentmessage = "§6[NoLock] §cThere are currently §a" + container.getUsers().size() + " §cuser(s) on this container";
									String currentusers = null;
									if (container.getUsers().size() == 0) {
										currentmessage = currentmessage + ".";
									} else {
										currentmessage = currentmessage + ":";
									}
									player.sendMessage(currentmessage);
									for (String uuid : container.getUsers()) {
										currentusers = createString(currentusers, Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
									}
									if (currentusers != null) {
										player.sendMessage("§6[NoLock] §c" + currentusers);
									}
								} else {
									player.sendMessage("§6[NoLock] §cThere are currently no user on this container.");
								}
							} else {
								player.sendMessage("§6[NoLock] §cTarget container isn't locket yet!");
							}
						} else {
							player.sendMessage("§6[NoLock] §cTarget block can't be a container!");
						}
					} else if (args[0].equalsIgnoreCase("user")) {
						if (args.length == 1) {
							player.sendMessage("§6[NoLock] §cUnknown arguements. Please type §a/nolock user help §cto see the help menu!");
						} else if (args.length >= 2) {
							Block block = player.getTargetBlock(new HashSet<Material>(), 5);
							if (args[1].equalsIgnoreCase("add")) {
								if (args.length > 2) {
									if (NoLock.isNamableTileEntity(block)) {
										if (NoLock.isContainer(block)) {
											NoLock container = new NoLock(block);
											if (container.getOwner().equalsIgnoreCase(player.getUniqueId().toString())) {

												List<String> successuuid = new ArrayList<String>();
												String failusers = null;
												String successusers = null;

												for (int i = 2; i < args.length; i++) {
													OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(args[i]);
													if (offlineplayer != null && offlineplayer.hasPlayedBefore()) {
														if (container.getUsers() != null) {
															if (!container.getUsers().contains(offlineplayer.getUniqueId().toString())) {
																successusers = createString(successusers, args[i]);
																successuuid.add(offlineplayer.getUniqueId().toString());
															} else {
																failusers = createString(failusers, args[i]);
															}
														} else {
															successusers = createString(successusers, args[i]);
															successuuid.add(offlineplayer.getUniqueId().toString());
														}
													} else {
														failusers = createString(failusers, args[i]);
													}
												}
												if (successuuid.size() != 0) {
													container.addUsers(successuuid);
												}
												if (successusers != null) {
													player.sendMessage("§6[NoLock] §cSuccessfuly added player(s) " + successusers + "§c to container's users list!");
												}
												if (failusers != null) {
													player.sendMessage("§6[NoLock] §cFailed to add player(s) " + failusers
															+ "§c to container's users list, they might be already in users list, or they never played on this server before!");
												}
											} else {
												player.sendMessage("§6[NoLock] §cYou have no permission to edit §c" + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName()
														+ "'s container!");
											}
										} else {
											player.sendMessage("§6[NoLock] §cTarget container isn't locked yet!");
										}
									} else {
										player.sendMessage("§6[NoLock] §cTarget block can't be a container!");
									}
								} else {
									player.sendMessage("§6[NoLock] §cUnknown arguements. Please type §a/nolock user help §cto see the help menu!");
								}

							} else if (args[1].equalsIgnoreCase("remove")) {
								if (args.length == 1) {
									player.sendMessage("§6[NoLock] §cUnknown arguements. Please type §a/nolock user help §cto see the help menu!");
								}
								if (args.length > 2) {
									if (NoLock.isNamableTileEntity(block)) {
										if (NoLock.isContainer(block)) {
											NoLock container = new NoLock(block);
											if (container.getOwner().equalsIgnoreCase(player.getUniqueId().toString())) {

												List<String> successuuid = new ArrayList<String>();
												String failusers = null;
												String successusers = null;
												for (int i = 2; i < args.length; i++) {
													OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(args[i]);
													if (offlineplayer != null) {
														if (container.getUsers() != null) {
															if (container.getUsers().contains(offlineplayer.getUniqueId().toString())) {
																successusers = createString(successusers, args[i]);
																successuuid.add(offlineplayer.getUniqueId().toString());
															} else {
																failusers = createString(failusers, args[i]);
															}
														} else {
															failusers = createString(failusers, args[i]);
														}
													} else {
														failusers = createString(failusers, args[i]);
													}
												}
												if (successuuid.size() != 0) {
													container.removeUsers(successuuid);
												}
												if (successusers != null) {
													player.sendMessage("§6[NoLock] §cSuccessfuly removed player(s) " + successusers + "§c from container's users list!");
												}
												if (failusers != null) {
													player.sendMessage("§6[NoLock] §cFailed to remove player(s) " + failusers
															+ "§c from container's users list, they aren't in the container's users list!");
												}
											} else {
												player.sendMessage("§6[NoLock] §cYou have no permission to edit §c" + Bukkit.getOfflinePlayer(UUID.fromString(container.getOwner())).getName()
														+ "'s container!");
											}
										} else {
											player.sendMessage("§6[NoLock] §cTarget container isn't locked yet!");
										}
									} else {
										player.sendMessage("§6[NoLock] §cTarget block can't be a container!");
									}
								} else {
									player.sendMessage("§6[NoLock] §cUnknown arguements. Please type §a/nolock user help §cto see the help menu!");
								}
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

	public String createString(String oldstr, String newstr) {
		if (oldstr == null) {
			return ChatColor.GREEN + newstr + ChatColor.RESET;
		} else {
			return oldstr + ", " + ChatColor.GREEN + newstr + ChatColor.RESET;
		}
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
