package me.skymc.taboolib.timecycle;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.database.GlobalDataManager;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.playerdata.DataUtils;

public class TimeCycleManager {
	
	/**
	 * ���һ�θ��£� 2018��1��16��21:07:49
	 * 
	 * @author sky
	 */

	private static ConcurrentHashMap<String, TimeCycle> cycles = new ConcurrentHashMap<>();
	
	/**
	 * ��ȡ���ڹ�����
	 * 
	 * @param name
	 * @return
	 */
	public static TimeCycle getTimeCycle(String name) {
		return cycles.get(name);
	}
	
	/**
	 * ��ȡ�������ڹ�����
	 * 
	 * @return
	 */
	public static Collection<TimeCycle> getTimeCycles() {
		return cycles.values();
	}
	
	/**
	 * ����ɾ����������
	 * 
	 * @param name
	 */
	public static void deleteCycleData(String name) {
		HashMap<String, String> map = GlobalDataManager.getVariables();
		for (String _name : map.keySet()) {
			if (_name.startsWith("timecycle")) {
				GlobalDataManager.setVariable(name, null);
			}
		}
	}
	
	/**
	 * ע�����ڹ�����
	 * 
	 * @param cycle
	 */
	public static void register(TimeCycle cycle) {
		if (!cycles.containsKey(cycle.getName())) {
			cycles.put(cycle.getName(), cycle);
		}
		else {
			MsgUtils.warn("ע�����ڹ����� ��8" + cycle.getName() + "��c ʧ��, ԭ��: &4�����ظ�");
		}
	}
	
	/**
	 * ע�����ڹ�����
	 * 
	 * @param name
	 * @return
	 */
	public static TimeCycle cancel(String name) {
		return cycles.remove(name);
	}
	
	/**
	 * ע������������ڹ�����
	 * 
	 * @param plugin
	 */
	public static void cancel(Plugin plugin) {
		cycles.values().forEach(x -> {
			if (x.getPlugin().equals(plugin)) {
				cycles.remove(x.getName());
			}
		});
	}
	
	/**
	 * ������һ�θ����¼�
	 * 
	 * @param name
	 * @param time
	 */
	public static boolean setTimeline(String name, Long time) {
		if (cycles.containsKey(name)) {
			GlobalDataManager.setVariable("timecycle:" + name, time.toString());
			return true;
		}
		return false;
	}
	
	/**
	 * ��ȡ��һ��ˢ��ʱ��
	 * 
	 * @param name
	 * @return
	 */
	public static long getAfterTimeline(String name) {
		if (cycles.containsKey(name)) {
			Long value = Long.valueOf(GlobalDataManager.getVariable("timecycle:" + name, "0"));
			return value + cycles.get(name).getCycle();
		}
		return 0L;
	}
	
	/**
	 * ��ȡ��һ��ˢ��ʱ��
	 * 
	 * @param name
	 * @return
	 */
	public static long getBeforeTimeline(String name) {
		if (cycles.containsKey(name)) {
			return Long.valueOf(GlobalDataManager.getVariable("timecycle:" + name, "0"));
		}
		return 0L;
	}
	
	public static void load() {
		// ע�������
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (TimeCycle cycle : cycles.values()) {
					// ������û�б�ִ�й�
					if (!GlobalDataManager.contains("timecycle:" + cycle.getName())) {
						long time = new TimeCycleInitializeEvent(cycle, System.currentTimeMillis()).call().getTimeline();
						// ��ʼ��
						GlobalDataManager.setVariable("timecycle:" + cycle.getName(), String.valueOf(time));
						// ������
						Bukkit.getPluginManager().callEvent(new TimeCycleEvent(cycle));
					}
					// �������ˢ��ʱ��
					else if (System.currentTimeMillis() >= getAfterTimeline(cycle.getName())) {
						long time = System.currentTimeMillis();
						// ���ʱ������ 30 ��
						if (time - getAfterTimeline(cycle.getName()) > 30000) {
							// ��ʼ��
							time = new TimeCycleInitializeEvent(cycle, time).call().getTimeline();
						}
						// ����
						GlobalDataManager.setVariable("timecycle:" + cycle.getName(), String.valueOf(time));
						// ������
						Bukkit.getPluginManager().callEvent(new TimeCycleEvent(cycle));
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getInst(), 0, 20);
	}
}
