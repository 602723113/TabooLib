package me.skymc.taboolib.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.message.MsgUtils;
import pw.yumc.Yum.events.PluginNetworkEvent;

/**
 * @author sky
 * @since 2018��2��23�� ����11:10:03
 */
public class ListenerNetWork implements Listener {
	
	public static final String GG = "������ֻ��Ϊ�˷�ֹ������ĸ��¼�ⱻ YUM �����ֹ���������á�";
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onNetWork(PluginNetworkEvent e) {
		if (e.getPlugin() != null && e.getPlugin().equals(Main.getInst())) {
			// ȡ����ֹ
			e.setCancelled(false);
			// ��̨��ʾ
			MsgUtils.warn("��ȡ�� &4YUM &c�Ա����������ʵ���ֹ!");
		}
	}
}
