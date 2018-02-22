package me.skymc.tlm;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import me.skymc.taboolib.Main;
import me.skymc.taboolib.fileutils.ConfigUtils;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.string.language2.Language2;
import me.skymc.tlm.module.TabooLibraryModule;
import me.skymc.tlm.module.sub.ModuleCommandChanger;
import me.skymc.tlm.module.sub.ModuleInventorySave;
import me.skymc.tlm.module.sub.ModuleKits;
import me.skymc.tlm.module.sub.ModuleTimeCycle;

/**
 * @author sky
 * @since 2018��2��17�� ����10:28:05
 */
public class TLM {
	
	private static TLM inst = null;
	
	@Getter
	private FileConfiguration config;
	
	@Getter
	private Language2 language;
	
	/**
	 * ���췽��
	 */
	private TLM() {
		// ���������ļ�
		reloadConfig();
		
		// ����ģ��
		if (isEnableModule("TimeCycle")) {
			TabooLibraryModule.getInst().register(new ModuleTimeCycle());
		}
		if (isEnableModule("Kits")) {
			TabooLibraryModule.getInst().register(new ModuleKits());
		}
		if (isEnableModule("CommandChanger")) {
			TabooLibraryModule.getInst().register(new ModuleCommandChanger());
		}
		if (isEnableModule("InventorySave")) {
			TabooLibraryModule.getInst().register(new ModuleInventorySave());
		}
		
		// ����ģ��
		TabooLibraryModule.getInst().loadModules();
		
		// ��ʾ
		MsgUtils.send("���� &f" + TabooLibraryModule.getInst().getSize() + " &7�� &fTLM &7ģ��");
	}
	
	/**
	 * ��ȡ TLM ����
	 * 
	 * @return TLM
	 */
	public static TLM getInst() {
		if (inst == null) {
			synchronized (TLM.class) {
				if (inst == null) {
					inst = new TLM();
				}
			}
		}
		return inst;
	}
	
	/**
	 * ���������ļ�
	 */
	public void reloadConfig() {
		config = ConfigUtils.saveDefaultConfig(Main.getInst(), "module.yml");
		// ���������ļ�
		try {
			language = new Language2(config.getString("Language"), Main.getInst());
		} catch (Exception e) {
			MsgUtils.warn("�����ļ�������: &4" + config.getString("Language"));
			return;
		}
	}
	
	/**
	 * ģ���Ƿ�����
	 * 
	 * @param name ����
	 * @return boolean
	 */
	public boolean isEnableModule(String name) {
		return config.getStringList("EnableModule").contains(name);
	}
}
