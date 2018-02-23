package me.skymc.taboolib.update;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.scheduler.BukkitRunnable;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.fileutils.FileUtils;
import me.skymc.taboolib.message.MsgUtils;

/**
 * @author sky
 * @since 2018��2��23�� ����10:39:14
 */
public class UpdateTask {
	
	/**
	 * ������
	 */
	public UpdateTask() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// �Ƿ����
				if (!Main.getInst().getConfig().getBoolean("UPDATE-CHECK")) {
					return;
				}
				
				String value = FileUtils.getStringFromURL("https://github.com/Bkm016/TabooLib/releases", 1024);
				Pattern pattern = Pattern.compile("<a href=\"/Bkm016/TabooLib/releases/tag/(\\S+)\">");
				Matcher matcher = pattern.matcher(value);
				if (matcher.find()) {
					// ���°汾
					double newVersion = Double.valueOf(matcher.group(1));
					// ��������°�
					if (TabooLib.getPluginVersion() == newVersion) {
						MsgUtils.send("����������°�, �������!");
					}
					else {
						MsgUtils.send("&8####################################################");
						MsgUtils.send("��⵽���µİ汾����!");
						MsgUtils.send("��ǰ�汾: &f" + TabooLib.getPluginVersion());
						MsgUtils.send("���°汾: &f" + newVersion);
						MsgUtils.send("���ص�ַ: &fhttp://www.mcbbs.net/thread-773065-1-1.html");
						MsgUtils.send("��Դ��ַ: &fhttps://github.com/Bkm016/TabooLib/");
						MsgUtils.send("&8####################################################");
					}
				}
			}
		}.runTaskLaterAsynchronously(Main.getInst(), 100);
	}
}
