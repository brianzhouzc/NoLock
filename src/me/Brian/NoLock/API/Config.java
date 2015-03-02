package me.Brian.NoLock.API;

import me.Brian.NoLock.Main;

import org.bukkit.plugin.Plugin;

public class Config {
	Plugin plugin = Main.getPlugin();

	public boolean ExplotionProtectionAll() {
		return plugin.getConfig().getBoolean("Configs.explosion-protection-all");
	}
	
	public boolean AdminSnoop(){
		return plugin.getConfig().getBoolean("Configs.allow-admin-snoop");
	}
	
	public boolean AdminBreak() {
		return plugin.getConfig().getBoolean("Configs.allow-admin-break");
	}
	
	public boolean EnableQuickProtection() {
		return plugin.getConfig().getBoolean("Configs.enable-quick-protect");
	}
	
	public boolean EnableProtocollibNameOveride() {
		return plugin.getConfig().getBoolean("Configs.enable-protocollib-name-overide");
	}
	
	public boolean HopperBlocking() {
		return plugin.getConfig().getBoolean("Configs.enable-hopper-blocking");
	}
}
