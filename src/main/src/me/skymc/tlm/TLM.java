package me.skymc.tlm;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import me.skymc.taboolib.Main;
import me.skymc.taboolib.fileutils.ConfigUtils;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.tlm.module.TabooLibraryModule;
import me.skymc.tlm.module.sub.ModuleTimeCycle;

/**
 * @author sky
 * @since 2018��2��17�� ����10:28:05
 */
public class TLM {
	
	private static TLM inst = null;
	
	@Getter
	private FileConfiguration config;
	
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
	}
	
	/**
	 * ģ���Ƿ�����
	 * 
	 * @param name ����
	 * @return boolean
	 */
	private boolean isEnableModule(String name) {
		return config.getStringList("EnableModule").contains(name);
	}
}
