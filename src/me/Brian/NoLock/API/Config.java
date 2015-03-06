package me.Brian.NoLock.API;

import me.Brian.NoLock.Main;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class Config {
	static Plugin plugin = Main.getPlugin();

	public static boolean ExplotionProtectionAll() {
		return plugin.getConfig().getBoolean("Configs.explosion-protection-all", true);
	}

	public static boolean AdminSnoop() {
		return plugin.getConfig().getBoolean("Configs.allow-admin-snoop", true);
	}

	public static boolean AdminBreak() {
		return plugin.getConfig().getBoolean("Configs.allow-admin-break", true);
	}

	public static boolean EnableQuickProtection() {
		return plugin.getConfig().getBoolean("Configs.quick-protect.enable", false);
	}

	public static Material getQuickProtectMaterial() {
		return Material.getMaterial(plugin.getConfig().getString("Configs.quick-protect.item", "STICK"));
	}

	public static boolean EnableProtocollibNameOveride() {
		return plugin.getConfig().getBoolean("Configs.protocollib-name-overide.enable", true);
	}
	
	public static boolean EnableProtocollibNameOverideUsersName() {
		return plugin.getConfig().getBoolean("Configs.protocollib-name-overide.use_users_name", true);
	}

	public static boolean HopperBlocking() {
		return plugin.getConfig().getBoolean("Configs.enable-hopper-blocking", true);
	}
}
