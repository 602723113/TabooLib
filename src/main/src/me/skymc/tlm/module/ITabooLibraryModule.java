package me.skymc.tlm.module;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author sky
 * @since 2018��2��17�� ����11:22:42
 */
public abstract interface ITabooLibraryModule {
	
	default void onEnable() {}
	
	default void onDisable() {}
	
	default void onReload() {};
	
	abstract String getName();
	
	default FileConfiguration getConfig() {
		return TabooLibraryModule.getInst().getConfig(this);
	}
}
