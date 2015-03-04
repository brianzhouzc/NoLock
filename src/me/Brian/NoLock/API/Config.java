package me.Brian.NoLock.API;

import me.Brian.NoLock.Main;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class Config {
	static Plugin plugin = Main.getPlugin();

	public static boolean ExplotionProtectionAll() {
		return plugin.getConfig().getBoolean("Configs.explosion-protection-all");
	}

	public static boolean AdminSnoop() {
		return plugin.getConfig().getBoolean("Configs.allow-admin-snoop");
	}

	public static boolean AdminBreak() {
		return plugin.getConfig().getBoolean("Configs.allow-admin-break");
	}

	public static boolean EnableQuickProtection() {
		return plugin.getConfig().getBoolean("Configs.enable-quick-protect.enable");
	}

	public static Material getQuickProtectMaterial() {
		return Material.getMaterial(plugin.getConfig().getString("Configs.enable-quick-protect.item"));
	}

	public static boolean EnableProtocollibNameOveride() {
		return plugin.getConfig().getBoolean("Configs.enable-protocollib-name-overide");
	}

	public static boolean HopperBlocking() {
		return plugin.getConfig().getBoolean("Configs.enable-hopper-blocking");
	}
}
