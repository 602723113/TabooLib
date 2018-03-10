package me.skymc.taboolib.string.language2;

import java.io.File;
import java.io.FileNotFoundException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.skymc.taboolib.fileutils.ConfigUtils;

/**
 * @author sky
 * @since 2018��2��13�� ����2:37:07
 */
public class Language2 {
	
	@Getter
	private FileConfiguration configuration;
	
	@Getter
	private File languageFile;
	
	@Getter
	private File languageFolder;
	
	@Getter
	private Plugin plugin;
	
	@Getter
	private String languageName;
	
	/**
	 * ���췽��
	 * 
	 * @param plugin ���
	 */
	public Language2(Plugin plugin) {
		this("zh_CN", plugin);
	}
	
	/**
	 * ���췽��
	 * 
	 * @param languageName �����ļ�
	 * @param plugin ���
	 */
	public Language2(String languageName, Plugin plugin) {
		this.languageName = languageName;
		this.plugin = plugin;
		// ���������ļ�
		reload(languageName);
	}
	
	/**
	 * ��ȡ�����ļ�
	 * 
	 * @param key ��
	 * @return {@link Language2Value}
	 */
	public Language2Value get(String key) {
		return new Language2Value(this, key);
	}
	
	/**
	 * ��ȡ�����ļ�
	 * 
	 * @param key ��
	 * @param placeholder �滻�������� @$0 ��ʼ
	 * @return {@link Language2Value}
	 */
	public Language2Value get(String key, String... placeholder) {
		Language2Value value = new Language2Value(this, key);
		for (int i = 0 ; i < placeholder.length ; i++) {
			value.addPlaceholder("$" + i, placeholder[i]);
		}
		return value;
	}
	
	/**
	 * ���������ļ�
	 */
	public void reload() {
		reload(this.languageName);
	}
	
	/**
	 * ���������ļ�
	 * 
	 * @param languageName �������ļ�����
	 */
	public void reload(String languageName) {
		// ��ʼ���ļ���
		createFolder(plugin);
		// ��ʽ��������
		languageName = formatName(languageName);
		// ��ȡ�ļ�
		languageFile = new File(languageFolder, languageName);
		// �ļ�������
		if (!languageFile.exists()) {
			// ��������ļ�������
			if (plugin.getResource("Language2/" + languageName) == null) {
				try {
					throw new FileNotFoundException("�����ļ� " + languageName + " ������");
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
			else {
				// �ͷ���Դ
				plugin.saveResource("Language2/" + languageName, true);
			}
		}
		// ��������
		configuration = ConfigUtils.load(plugin, languageFile);
	}
	
	/**
	 * PlaceholderAPI ����ʶ��
	 * 
	 * @param player ���
	 * @param string �ı�
	 * @return String
	 */
	public String setPlaceholderAPI(Player player, String string) {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && player != null) {
			return PlaceholderAPI.setPlaceholders(player, string);
		}
		return string;
	}
	
	/**
	 * �����ļ����Ƹ�ʽ��
	 * 
	 * @param name �����ļ�����
	 * @return String
	 */
	private String formatName(String name) {
		return name.contains(".yml") ? name : name + ".yml";
	}
	
	/**
	 * �����ļ��г�ʼ��
	 * 
	 * @param plugin
	 */
	private void createFolder(Plugin plugin) {
		languageFolder = new File(plugin.getDataFolder(), "Language2");
		if (!languageFolder.exists()) {
			languageFolder.mkdir();
		}
	}
}
