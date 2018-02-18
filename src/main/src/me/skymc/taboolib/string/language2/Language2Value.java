package me.skymc.taboolib.string.language2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.skymc.taboolib.string.language2.type.Language2Action;
import me.skymc.taboolib.string.language2.type.Language2Json;
import me.skymc.taboolib.string.language2.type.Language2Title;

/**
 * @author sky
 * @since 2018��2��13�� ����3:05:15
 */
public class Language2Value {
	
	@Getter
	private Language2 language;
	
	@Getter
	private String languageKey;
	
	@Getter
	private List<String> languageValue;
	
	@Getter
	private Language2Type languageType;
	
	@Getter
	private LinkedHashMap<String, String> placeholder = new LinkedHashMap<>();
	
	@Getter
	private boolean enablePlaceholderAPI = false;
	
	/**
	 * ���췽��
	 */
	public Language2Value(Language2 language, String languageKey) {
		// ��������ļ�������
		if (language == null || languageKey == null) {
			languageValue = Arrays.asList(ChatColor.DARK_RED + "[<ERROR-0>]");
			return;
		}
		
		// ��������ı�������
		if (!language.getConfiguration().contains(languageKey)) {
			languageValue = Arrays.asList(ChatColor.DARK_RED + "[<ERROR-1: " + languageKey + ">]");
			return;
		}
		
		// ������Ǽ�������
		if (language.getConfiguration().get(languageKey) instanceof List) {
			// �����ı�
			languageValue = asColored(language.getConfiguration().getStringList(languageKey));
			// ��ȡ����
			String type = languageValue.get(0).toLowerCase();
			
			// �Ƿ�������ע��
			boolean isType = true;
			
			// �Ƿ�����PAPI
			if (type.contains("[papi]")) {
				enablePlaceholderAPI = true;
			}
			
			// �ж�����
			if (type.contains("[json]")) {
				languageType = Language2Type.JSON;
			}
			else if (type.contains("[title]")) {
				languageType = Language2Type.TITLE;
			}
			else if (type.contains("[action]")) {
				languageType = Language2Type.ACTION;
			}
			else {
				languageType = Language2Type.TEXT;
				isType = false;
			}
			
			// �Ƿ���Ҫɾ������ע��
			if (isType) {
				languageValue.remove(0);
			}
		}
		else {
			// �����ı�
			languageValue = Arrays.asList(ChatColor.translateAlternateColorCodes('&', language.getConfiguration().getString(languageKey)));
			// ��������
			languageType = Language2Type.TEXT;
		}
		
		// ��ʼ������
		this.language = language;
		this.languageKey = languageKey;
	}
	
	/**
	 * ����ҷ�����Ϣ
	 * 
	 * @param player
	 */
	public void send(Player player) {
		// ��������
		if (languageType == Language2Type.TITLE) {
			// �����ı�
			new Language2Title(this).send(player);
		}
		// ����������
		else if (languageType == Language2Type.ACTION) {
			// �����ı�
			new Language2Action(this).send(player);
		}
		// JSON����
		else if (languageType == Language2Type.JSON) {
			// �����ı�
			new Language2Json(this, player).send(player);
		}
		else {
			// �����ı�
			for (String message : languageValue) {
				// ������Ϣ
				if (player != null) {
					player.sendMessage(setPlaceholder(message, player));
				}
				else {
					Bukkit.getConsoleSender().sendMessage(setPlaceholder(message, player));
				}
			}
		}
	}
	
	/**
	 * ����ҷ�����Ϣ
	 * 
	 * @param players ���
	 */
	public void send(List<Player> players) {
		// ��������
		if (languageType == Language2Type.TITLE) {
			// ʶ���ı�
			Language2Title title = new Language2Title(this);
			// �����ı�
			players.forEach(x -> title.send(x));
		}
		// ����������
		else if (languageType == Language2Type.ACTION) {
			// ʶ���ı�
			Language2Action action = new Language2Action(this);
			// �����ı�
			players.forEach(x -> action.send(x));
		}
		// JSON����
		else if (languageType == Language2Type.JSON) {
			for (Player player : players) {
				// ʶ���ı�
				Language2Json json = new Language2Json(this, player);
				// �����ı�
				json.send(player);
			}
		}
		else {
			for (Player player : players) {
				// �����ı�
				for (String message : languageValue) {
					// ������Ϣ
					if (player != null) {
						player.sendMessage(setPlaceholder(message, player));
					}
					else {
						Bukkit.getConsoleSender().sendMessage(setPlaceholder(message, player));
					}
				}
			}
		}
	}
	
	/**
	 * ��ָ����߷�����Ϣ
	 * 
	 * @param sender
	 */
	public void send(CommandSender sender) {
		if (sender instanceof Player) {
			send((Player) sender);
		}
		else {
			send(Bukkit.getPlayerExact(""));
		}
	}
	
	/**
	 * ��ȡ�ı�
	 * 
	 * @return
	 */
	public String asString() {
		// ��������
		if (languageType == Language2Type.TITLE) {
			return new Language2Title(this).getTitle();
		}
		// ����������
		else if (languageType == Language2Type.ACTION) {
			return new Language2Action(this).getText();
		}
		// JSON����
		else if (languageType == Language2Type.JSON) {
			return new Language2Json(this, null).getText().toString();
		}
		else {
			return languageValue.size() == 0 ? ChatColor.DARK_RED + "[<ERROR-1>]" : languageValue.get(0);
		}
	}
	
	/**
	 * �����滻
	 * 
	 * @param value �滻�ı�
	 * @param player ������
	 * @return String
	 */
	public String setPlaceholder(String value, Player player) {
		for (Entry<String, String> entry : placeholder.entrySet()) {
			value = value.replace(entry.getKey(), entry.getValue());
		}
		return isEnablePlaceholderAPI() ? this.language.setPlaceholderAPI(player, value) : value;
	}
	
	/**
	 * �����滻����
	 * 
	 * @param key ��
	 * @param value ֵ
	 * @return {@link Language2Value}
	 */
	public Language2Value addPlaceholder(String key, String value) {
		this.placeholder.put(key, value);
		return this;
	}
	
	/**
	 * �滻��ɫ
	 * 
	 * @param list
	 * @return
	 */
	public List<String> asColored(List<String> list) {
		for (int i = 0 ; i < list.size() ; i++) {
			list.set(i, ChatColor.translateAlternateColorCodes('&', list.get(i)));
		}
		return list;
	}
}
