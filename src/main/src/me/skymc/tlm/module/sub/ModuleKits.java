package me.skymc.tlm.module.sub;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skymc.taboolib.database.PlayerDataManager;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.other.DateUtils;
import me.skymc.taboolib.other.NumberUtils;
import me.skymc.taboolib.playerdata.DataUtils;
import me.skymc.tlm.module.ITabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��18�� ����12:13:55
 */
public class ModuleKits implements ITabooLibraryModule {
	
	private FileConfiguration data;

	@Override
	public String getName() {
		return "Kits";
	}
	
	@Override
	public void onEnable() {
		data = DataUtils.addPluginData("ModuleKits", null);
	}
	
	/**
	 * ��������Ƿ���ȡ���
	 * 
	 * @param player ���
	 */
	public void setPlayerReward(Player player, String kit, boolean reward) {
		data.set(kit + "." + player.getName(), reward ? System.currentTimeMillis() : null);
	}
	
	/**
	 * ����������
	 * 
	 * @param kit ���
	 */
	public void resetKit(String kit) {
		data.set(kit, null);
	}
	
	/**
	 * ����Ƿ���ȡ���
	 * 
	 * @param player ���
	 * @param kit ���
	 * @return boolean
	 */
	public boolean isPlayerRewared(Player player, String kit) {
		return data.contains(kit + "." + player.getName());
	}
	
	/**
	 * ����Ƿ�����ȴ��
	 * 
	 * @param player
	 * @param kit
	 * @return
	 */
	public boolean isPlayerCooldown(Player player, String kit) {
		return System.currentTimeMillis() - data.getLong(kit + "." + player.getName()) < getCooldown(kit);
	}
	
	/**
	 * ����Ƿ����
	 * 
	 * @param kit �����
	 * @return boolean
	 */
	public boolean contains(String kit) {
		return getConfig().contains("Kits." + kit);
	}
	
	/**
	 * ��ȡ�����ȴʱ��
	 * 
	 * @param kit �����
	 * @return long
	 */
	public long getCooldown(String kit) {
		return DateUtils.formatDate(getConfig().getString("Kits." + kit + ".Cooldown"));
	}
	
	/**
	 * ��ȡ����ռ䲻��ʱ�Ĵ���ʽ
	 * 
	 * @param kit �����
	 * @return boolean
	 */
	public Boolean isFullDrop(String kit) {
		return getConfig().getBoolean("Kits." + kit + ".FullDrop");
	}
	
	/**
	 * ����Ƿ�ֻ����ȡһ��
	 * 
	 * @param kit �����
	 * @return boolean
	 */
	public boolean isDisposable(String kit) {
		return getConfig().getBoolean("Kits." + kit + ".Disposable");
	}
	
	/**
	 * ��ȡ���Ȩ��
	 * 
	 * @param kit �����
	 * @return String
	 */
	public String getPermission(String kit) {
		return getConfig().getString("Kits." + kit + ".Permission");
	}
	
	/**
	 * ��ȡ���Ȩ����ʾ
	 * 
	 * @param kit �����
	 * @return String
	 */
	public String getPermissionMessage(String kit) {
		return getConfig().getString("Kits." + kit + ".Permission-message").replace("&", "��");
	}
	
	/**
	 * ��ȡ�����Ʒ
	 * 
	 * @param kit �����
	 * @return {@link List}
	 */
	public List<ItemStack> getItems(String kit) {
		List<ItemStack> items = new ArrayList<>();
		for (String itemStr : getConfig().getStringList("Kits." + kit + ".Items")) {
			ItemStack item = ItemUtils.getCacheItem(itemStr.split(" ")[0]);
			if (item != null) {
				item = item.clone();
				try {
					item.setAmount(NumberUtils.getInteger(itemStr.split(" ")[1]));
					items.add(item);
				} catch (Exception e) {
					MsgUtils.warn("ģ�����������쳣: &4��Ʒ��������");
					MsgUtils.warn("ģ��: &4Kits");
					MsgUtils.warn("λ��: &4" + itemStr);
				}
			}
		}
		return items;
	}
}
