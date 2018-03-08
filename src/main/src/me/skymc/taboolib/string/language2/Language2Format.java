package me.skymc.taboolib.string.language2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.skymc.taboolib.string.language2.value.Language2Action;
import me.skymc.taboolib.string.language2.value.Language2Json;
import me.skymc.taboolib.string.language2.value.Language2Sound;
import me.skymc.taboolib.string.language2.value.Language2Text;
import me.skymc.taboolib.string.language2.value.Language2Title;

/**
 * @author sky
 * @since 2018-03-08 22:45:56
 */
public class Language2Format implements Language2Line {
	
	@Getter
	private Language2Value language2Value = null;
	
	@Getter
	private List<Language2Line> language2Lines = new ArrayList<>();
	
	/**
	 * ���췽��
	 * 
	 * @param value ����
	 */
	public Language2Format(Player player, Language2Value value) {
		language2Value = value;
		// ��������
		Language2Type type = Language2Type.TEXT;
		// �ݽ�����
		List<String> values = new LinkedList<>();
		
		// ��������
		for (String line : value.getLanguageValue()) {
			// �ı�����
			if (line.contains("[text]")) {
				// �ݽ�����
				parseValue(player, values, type);
				// ��������
				type = Language2Type.TEXT;
			}
			// �����
			else if (line.contains("[title]")) {
				// �ݽ�����
				parseValue(player, values, type);
				// ��������
				type = Language2Type.TITLE;
			}
			// С����
			else if (line.contains("[action]")) {
				// �ݽ�����
				parseValue(player, values, type);
				// ��������
				type = Language2Type.ACTION;
			}
			// JSON
			else if (line.contains("[json]")) {
				// �ݽ�����
				parseValue(player, values, type);
				// ��������
				type = Language2Type.JSON;
			}
			// ��Ч
			else if (line.contains("[sound]")) {
				// �ݽ�����
				parseValue(player, values, type);
				// ��������
				type = Language2Type.SOUND;
			}
			else if (line.contains("[return]")) {
				// �ݽ�����
				parseValue(player, values, type);
			}
			// Ĭ��
			else {
				// ׷������
				values.add(line);
			}
		}
	}
	
	/**
	 * ʶ������
	 * 
	 * @param player ���
	 * @param list ����
	 * @param type ����
	 */
	private void parseValue(Player player, List<String> list, Language2Type type) {
		if (list.size() == 0) {
			return;
		}
		
		// �����
		if (type == Language2Type.TITLE) {
			language2Lines.add(new Language2Title(this, list));
		}
		// С����
		else if (type == Language2Type.ACTION) {
			language2Lines.add(new Language2Action(this, list));
		}
		// JSON
		else if (type == Language2Type.JSON) {
			language2Lines.add(new Language2Json(this, list, player));
		}
		// ��Ч
		else if (type == Language2Type.SOUND) {
			language2Lines.add(new Language2Sound(this, list));
		}
		else {
			language2Lines.add(new Language2Text(this, list));
		}
		
		// ��������
		list.clear();
	}

	@Override
	public void send(Player player) {
		for (Language2Line line : language2Lines) {
			line.send(player);
		}
	}

	@Override
	public void console() {
		for (Language2Line line : language2Lines) {
			line.console();
		}
	}
}
