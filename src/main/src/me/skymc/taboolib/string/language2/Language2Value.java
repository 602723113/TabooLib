package me.skymc.taboolib.string.language2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.skymc.taboolib.string.language2.value.Language2Text;

/**
 * @author sky
 * @since 2018��2��13�� ����3:05:15
 */
public class Language2Value extends Object {
	
	@Getter
	private Language2 language;
	
	@Getter
	private String languageKey;
	
	@Getter
	private List<String> languageValue;
	
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
			// ׷�ӽ�β
			languageValue.add("[return]");
			// �Ƿ�����PAPI
			if (languageValue.get(0).contains("[papi]")) {
				enablePlaceholderAPI = true;
			}
		}
		else {
			// �����ı�
			languageValue = Arrays.asList(ChatColor.translateAlternateColorCodes('&', language.getConfiguration().getString(languageKey)), "[return]");
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
		new Language2Format(player, this).send(player);
	}
	
	/**
	 * ����ҷ�����Ϣ
	 * 
	 * @param players ���
	 */
	public void send(List<Player> players) {
		for (Player player : players) {
			send(player);
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
			console();
		}
	}
	
	/**
	 * ȫ������
	 */
	public void broadcast() {
		send(new ArrayList<>(Bukkit.getOnlinePlayers()));
	}
	
	/**
	 * ���͵���̨
	 */
	public void console() {
		new Language2Format(null, this).console();
	}
	
	/**
	 * ��ȡ�ı�
	 * 
	 * @return
	 */
	public String asString() {
		Language2Format format = new Language2Format(null, this);
		if (format.getLanguage2Lines().get(0) instanceof Language2Text) {
			Language2Text text = (Language2Text) format.getLanguage2Lines().get(0);
			return setPlaceholder(text.getText().get(0), null);
		}
		else {
			return languageValue.size() == 0 ? ChatColor.DARK_RED + "[<ERROR-1>]" : setPlaceholder(languageValue.get(0), null);
		}
	}
	
	/**
	 * ��ȡ�ı�����
	 * 
	 * @return
	 */
	public List<String> asStringList() {
		Language2Format format = new Language2Format(null, this);
		if (format.getLanguage2Lines().get(0) instanceof Language2Text) {
			Language2Text text = (Language2Text) format.getLanguage2Lines().get(0);
			return setPlaceholder(text.getText(), null);
		}
		else {
			return Arrays.asList(languageValue.size() == 0 ? ChatColor.DARK_RED + "[<ERROR-1>]" : setPlaceholder(languageValue.get(0), null));
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
	 * �����滻
	 * 
	 * @param list �滻����
	 * @param player ������
	 * @return {@link List}
	 */
	public List<String> setPlaceholder(List<String> list, Player player) {
		List<String> _list = new ArrayList<>(list);
		for (int i = 0 ; i < _list.size() ; i++) {
			_list.set(i, setPlaceholder(_list.get(i), player));
		}
		return _list;
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
	
	@Override
	public String toString() {
		return asString();
	}
}
