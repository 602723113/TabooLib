package me.skymc.taboolib.string.language2.type;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import me.skymc.taboolib.Main;
import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.display.ActionUtils;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.other.NumberUtils;
import me.skymc.taboolib.string.language2.Language2Value;

/**
 * @author sky
 * @since 2018��2��13�� ����3:58:07
 */
public class Language2Action {
	
	private static final String KEY_TEXT = "    text: ";
	private static final String KEY_STAY = "    repeat: ";
	
	@Getter
	private String text = "";
	
	@Getter
	private int repeat = 1;
	
	@Getter
	private Language2Value value;
	
	public Language2Action(Language2Value value) {
		// ������ʼ��
		this.value = value;
		
		// �����ı�
		for (String message : value.getLanguageValue()) {
			try {
				// ��������ʾ
				if (message.startsWith(KEY_TEXT)) {
					text = message.substring(KEY_TEXT.length());
				}
				// ����ʱ��
				if (message.startsWith(KEY_STAY)) {
					repeat = NumberUtils.getInteger(message.substring(KEY_STAY.length()));
				}
			}
			catch (Exception e) {
				// ʶ���쳣
				text = ChatColor.DARK_RED + "[<ERROR-11: " + value.getLanguageKey() + ">]";
			}
		}
		
		// ����ظ�����
		if (repeat < 0) {
			repeat = 1;
			text = ChatColor.DARK_RED + "[<ERROR-12: " + value.getLanguageKey() + ">]";
		}
	}
	
	/**
	 * ���͸����
	 * 
	 * @param player ���
	 */
	public void send(Player player) {
		// ���汾
		if (TabooLib.getVerint() < 10800) {
			player.sendMessage(ChatColor.DARK_RED + "[<ERROR-30: " + value.getLanguageKey() + ">]");
		}
		// ������
		else if (player != null) {
			new BukkitRunnable() {
				int times = 0;
				
				@Override
				public void run() {
					ActionUtils.send(player, value.setPlaceholder(text, player));
					if ((times += 1) >= repeat) {
						cancel();
					}
				}
			}.runTaskTimer(Main.getInst(), 0, 20);
		}
		else {
			Bukkit.getConsoleSender().sendMessage(value.setPlaceholder(text, player));
		}
	}
}
