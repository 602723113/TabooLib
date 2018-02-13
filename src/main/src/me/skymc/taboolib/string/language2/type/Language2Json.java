package me.skymc.taboolib.string.language2.type;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.skymc.taboolib.jsonformatter.JSONFormatter;
import me.skymc.taboolib.jsonformatter.click.ClickEvent;
import me.skymc.taboolib.jsonformatter.click.RunCommandEvent;
import me.skymc.taboolib.jsonformatter.click.SuggestCommandEvent;
import me.skymc.taboolib.jsonformatter.hover.HoverEvent;
import me.skymc.taboolib.jsonformatter.hover.ShowTextEvent;
import me.skymc.taboolib.string.language2.Language2Value;

/**
 * @author sky
 * @since 2018��2��13�� ����4:11:33
 */
public class Language2Json {
	
	private static final String KEY_TEXT = "    text: ";
	private static final String KEY_COMMAND = "    command: ";
	private static final String KEY_SUGGEST = "    suggest: ";
	
	@Getter
	private Player player;
	
	@Getter
	private Language2Value value;
	
	@Getter
	private JSONFormatter json = new JSONFormatter();
	
	@Getter
	private StringBuffer text = new StringBuffer();
	
	public Language2Json(Language2Value value, Player player) {
		// �ı���ʼ��
		String current = ChatColor.DARK_RED + "[<ERROR-20: " + value.getLanguageKey() + ">]";
		
		// �״μ��
		boolean isFirst = true;
		boolean isBreak = false;
		
		// ������ʼ��
		this.value = value;
		this.player = player;
		
		// ������ʼ��
		ClickEvent clickEvent = null;
		HoverEvent hoverEvent = null;
		
		// �����ı�
		for (String message : value.getLanguageValue()) {
			try {
				// �������ʾ�ı�
				if (message.startsWith(KEY_TEXT)) {
					hoverEvent = new ShowTextEvent(message.replace("||", "\n").substring(KEY_TEXT.length()));
				}
				// ִ��ָ��
				else if (message.startsWith(KEY_COMMAND)) {
					clickEvent = new RunCommandEvent(message.substring(KEY_COMMAND.length()));
				}
				// ��ӡָ��
				else if (message.startsWith(KEY_SUGGEST)) {
					clickEvent = new SuggestCommandEvent(message.substring(KEY_SUGGEST.length()));
				}
				// ����
				else if (message.equals("[break]")) {
					append(current, clickEvent, hoverEvent);
					// ɾ������
					clickEvent = null;
					hoverEvent = null;
					// ����
					json.newLine();
					// ���
					isBreak = true;
				}
				// ������
				else {
					if (!isFirst && !isBreak) {
						append(current, clickEvent, hoverEvent);
						// ɾ������
						clickEvent = null;
						hoverEvent = null;
					}
					// ����
					current = message;
					// ���
					isFirst = false;
					isBreak = false;
				}
			}
			catch (Exception e) {
				// ʶ���쳣
				json.append(ChatColor.DARK_RED + "[<ERROR-21: " + value.getLanguageKey() + ">]");
			}
		}
		// ׷��
		append(current, clickEvent, hoverEvent);
	}
	
	/**
	 * ���͸����
	 * 
	 * @param player ���
	 */
	public void send(Player player) {
		if (player != null) {
			json.send(player);
		}
		else {
			Bukkit.getConsoleSender().sendMessage(text.toString());
		}
	}
	
	/**
	 * ׷�� JSON ����
	 * 
	 * @param current �ı�
	 * @param clickevent �������
	 * @param hoverEvent ��ʾ����
	 */
	private void append(String current, ClickEvent clickEvent, HoverEvent hoverEvent) {
		if (clickEvent == null && hoverEvent == null) {
			// ���ı�
			json.append(value.setPlaceholder(current, player));
		} else if (clickEvent != null && hoverEvent == null) {
			// �����
			json.appendClick(value.setPlaceholder(current, player), clickEvent);
		} else if (clickEvent == null && hoverEvent != null) {
			// ����ʾ
			json.appendHover(value.setPlaceholder(current, player), hoverEvent);
		} else {
			// ȫ��
			json.appendHoverClick(value.setPlaceholder(current, player), hoverEvent, clickEvent);
		}
		// ׷����ʾ�ı�
		text.append(current);
	}
}
