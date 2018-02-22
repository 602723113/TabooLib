package me.skymc.tlm.module.sub;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.playerdata.DataUtils;
import me.skymc.tlm.annotation.DisableConfig;
import me.skymc.tlm.inventory.TLMInventoryHolder;
import me.skymc.tlm.module.ITabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��22�� ����2:48:27
 */
@DisableConfig
public class ModuleInventorySave implements ITabooLibraryModule, Listener {
	
	private FileConfiguration conf;

	@Override
	public String getName() {
		return "InventorySave";
	}
	
	@Override
	public void onEnable() {
		reloadConfig();
	}
	
	@Override
	public void onReload() {
		reloadConfig();
	}
	
	public void reloadConfig() {
		conf = DataUtils.addPluginData("InventorySave", Main.getInst());
	}
	
	/**
	 * ������ұ���
	 * 
	 * @param player ���
	 * @param name ����
	 */
	public void saveInventory(Player player, String name) {
		// ������Ʒ
		for (int i = 0 ; i < (TabooLib.getVerint() > 10800 ? 41 : 40) ; i++) {
			ItemStack item = player.getInventory().getItem(i);
			conf.set(name + "." + i, item == null ? new ItemStack(Material.AIR) : item.clone());
		}
	}
	
	/**
	 * ������ұ���
	 * 
	 * @param player ���
	 * @param name ����
	 */
	public void pasteInventory(Player player, String name) {
		// �������������
		if (!conf.contains(name)) {
			MsgUtils.warn("ģ��ִ���쳣: &4����������");
			MsgUtils.warn("ģ��: &4InventorySave");
			MsgUtils.warn("λ��: &4" + name);
			return;
		}
		
		// ������Ʒ
		for (int i = 0 ; i < (TabooLib.getVerint() > 10800 ? 41 : 40) ; i++) {
			try {
				ItemStack item = (ItemStack) conf.get(name + "." + i);
				player.getInventory().setItem(i, item);
			}
			catch (Exception e) {
				MsgUtils.warn("ģ��ִ���쳣: &4��Ʒ���ǳ���");
				MsgUtils.warn("ģ��: &4InventorySave");
				MsgUtils.warn("λ��: &4" + name + ":" + i);
			}
		}
	}
	
	/**
	 * ��ȡ������������Ʒ
	 * 
	 * @param name ��������
	 * @return {@link List}
	 */
	public List<ItemStack> getItems(String name) {
		// �������������
		if (!conf.contains(name)) {
			MsgUtils.warn("ģ��ִ���쳣: &4����������");
			MsgUtils.warn("ģ��: &4InventorySave");
			MsgUtils.warn("λ��: &4" + name);
			return new LinkedList<>();
		}
		
		List<ItemStack> items = new LinkedList<>();
		// ������Ʒ
		for (int i = 0 ; i < (TabooLib.getVerint() > 10800 ? 41 : 40) ; i++) {
			try {
				ItemStack item = (ItemStack) conf.get(name + "." + i);
				items.add(item);
			}
			catch (Exception e) {
				MsgUtils.warn("ģ��ִ���쳣: &4��Ʒ��ȡ����");
				MsgUtils.warn("ģ��: &4InventorySave");
				MsgUtils.warn("λ��: &4" + name + ":" + i);
			}
		}
		return items;
	}
	
	/**
	 * ��ȡ���б���
	 * 
	 * @return {@link Set}
	 */
	public Set<String> getInventorys() {
		return conf.getConfigurationSection("").getKeys(false);
	}
	
	/**
	 * ɾ������
	 * 
	 * @param name ����
	 */
	public void deleteInventory(String name) {
		conf.set(name, null);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof TLMInventoryHolder)) {
			return;
		}
		
		TLMInventoryHolder holder = (TLMInventoryHolder) e.getInventory().getHolder();
		if (holder.getModule().equals(getName())) {
			e.setCancelled(true);
		} 
	}
}
