package me.skymc.tlm.module.sub;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.other.DateUtils;
import me.skymc.taboolib.other.NumberUtils;
import me.skymc.taboolib.timecycle.TimeCycle;
import me.skymc.taboolib.timecycle.TimeCycleEvent;
import me.skymc.taboolib.timecycle.TimeCycleInitializeEvent;
import me.skymc.taboolib.timecycle.TimeCycleManager;
import me.skymc.tlm.module.ITabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��17�� ����11:23:38
 */
public class ModuleTimeCycle implements ITabooLibraryModule, Listener {
	
	@Override
	public String getName() {
		return "TimeCycle";
	}
	
	@Override
	public void onEnable() {
		// ��������
		loadCycles();
	}
	
	@Override
	public void onDisable() {
		// ע�������
		unloadCycles();
	}
	
	@Override
	public void onReload() {
		// ע�������
		unloadCycles();
		// ��������
		loadCycles();
	}
	
	@EventHandler
	public void onTimeCycleInitialize(TimeCycleInitializeEvent e) {
		if (e.getCycle().getName().contains("tlm|")) {
			// ��ȡ����
			String name = e.getCycle().getName().replace("tlm|", "");
			// ����г�ʼ��ʱ������
			if (getConfig().contains("TimeCycle." + name + ".Initialise.InitialiseDate")) {
				// ��ȡʱ��
				Calendar date = Calendar.getInstance();
				// ������ʼ������
				for (String typeStr : getConfig().getStringList("TimeCycle." + name + ".Initialise.InitialiseDate")) {
					try {
						int type = (int) Calendar.class.getField(typeStr.split("=")[0]).get(Calendar.class);
						date.set(type, NumberUtils.getInteger(typeStr.split("=")[1]));
					} catch (Exception err) {
						MsgUtils.warn("ģ�����������쳣: &4�������ʹ���");
						MsgUtils.warn("ģ��: &4TimeCycle");
						MsgUtils.warn("λ��: &4" + typeStr);
					}
				}
				e.setTimeLine(date.getTimeInMillis());
			}
			// ����г�ʼ������
			if (getConfig().contains("TimeCycle." + name + ".Initialise.InitialiseCommand")) {
				// ������ʼ������
				for (String command : getConfig().getStringList("TimeCycle." + name + ".Initialise.InitialiseCommand")) {
					// ִ������
					Bukkit.getScheduler().runTask(Main.getInst(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
				}
			}
		}
	}
	
	@EventHandler
	public void onTimeCycle(TimeCycleEvent e) {
		if (e.getCycle().getName().contains("tlm|")) {
			// ��ȡ����
			String name = e.getCycle().getName().replace("tlm|", "");
			// ����и�������
			if (getConfig().contains("TimeCycle." + name + ".UpdateCommand")) {
				// ������������
				for (String command : getConfig().getStringList("TimeCycle." + name + ".UpdateCommand")) {
					// ִ������
					Bukkit.getScheduler().runTask(Main.getInst(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
				}
			}
		}
	}
	
	private void loadCycles() {
		for (String name : getConfig().getConfigurationSection("TimeCycle").getKeys(false)) {
			TimeCycleManager.register(new TimeCycle("tlm|" + name, DateUtils.formatDate(getConfig().getString("TimeCycle." + name + ".Cycle")), Main.getInst()));
		}
	}
	
	private void unloadCycles() {
		for (TimeCycle cycle : TimeCycleManager.getTimeCycles()) {
			if (cycle.getName().startsWith("tlm|")) {
				TimeCycleManager.cancel(cycle.getName());
			}
		}
	}
}
