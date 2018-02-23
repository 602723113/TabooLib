package me.skymc.taboolib;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.skymc.taboolib.playerdata.DataUtils;
import net.md_5.bungee.api.ChatColor;

public class TabooLib {
	
	/**
	 * ��ȡ����汾
	 * 
	 * @return
	 */
	public static double getPluginVersion() {
		try {
			return Double.valueOf(Main.getInst().getDescription().getVersion());
		} 
		catch (Exception e) {
			return 0D;
		}
	}
	
	/**
	 * ��ȡ NMS �汾
	 * 
	 * @return
	 */
	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	}
	
	/**
	 * ��ȡ������ UID
	 * 
	 * @return
	 */
	public static String getServerUID() {
		if (!DataUtils.getPluginData("TabooLibrary", null).contains("serverUID")) {
			DataUtils.getPluginData("TabooLibrary", null).set("serverUID", UUID.randomUUID().toString());
		}
		return DataUtils.getPluginData("TabooLibrary", null).getString("serverUID");
	}
	
	/**
	 * ���÷����� UID
	 */
	public static void resetServerUID() {
		DataUtils.getPluginData("TabooLibrary", null).set("serverUID", UUID.randomUUID().toString());
	}
	
	/**
	 * ���̨���� DEBUG ��Ϣ
	 * 
	 * @param plugin
	 * @param ss
	 */
	public static void debug(Plugin plugin, String... ss) {
		if (Main.getInst().getConfig().getBoolean("DEBUG")) {
			for (String s : ss) {
				// [00:42:41 INFO]: [TabooLib - DEBUG][TabooPlugin] Example bug message
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[TabooLib - DEBUG][" + plugin.getName() + "] " + ChatColor.RED + s);
			}
		}
	}
	
	/**
	 * ��ȡ NMS �汾�����֣�
	 * 
	 * @return
	 */
	public static int getVerint() {
		if (getVersion().startsWith("v1_7")) {
			return 10700;
		}
		else if (getVersion().startsWith("v1_8")) {
			return 10800;
		}
		else if (getVersion().startsWith("v1_9")) {
			return 10900;
		}
		else if (getVersion().startsWith("v1_10")) {
			return 11000;
		}
		else if (getVersion().startsWith("v1_11")) {
			return 11100;
		}
		else if (getVersion().startsWith("v1_12")) {
			return 11200;
		}
		else if (getVersion().startsWith("v1_13")) {
			return 11300;
		}
		return 0;
	}
}
