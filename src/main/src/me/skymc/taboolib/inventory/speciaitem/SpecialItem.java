package me.skymc.taboolib.inventory.speciaitem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import me.skymc.taboolib.Main;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.message.MsgUtils;

/**
 * @author sky
 * @since 2018��2��17�� ����8:34:12
 */
public class SpecialItem implements Listener {
	
	private static SpecialItem specialItem = null;
	
	private final List<AbstractSpecialItem> ITEM_DATA = new CopyOnWriteArrayList<>();
	
	@Getter
	private boolean isLoaded;
	
	/**
	 * ���췽��
	 */
	private SpecialItem() {
		
	}
	
	/**
	 * ��ȡ���߶���
	 * 
	 * @return {@link SpecialItem}
	 */
	public static SpecialItem getInst() {
		if (specialItem == null) {
			synchronized (SpecialItem.class) {
				if (specialItem == null) {
					specialItem = new SpecialItem();
					// ע�������
					Bukkit.getPluginManager().registerEvents(specialItem, Main.getInst());
				}
			}
		}
		return specialItem;
	}
	
	/**
	 * ע��ӿ�
	 * 
	 * @param item �ӿڶ���
	 */
	public void register(AbstractSpecialItem item) {
		if (contains(item.getName())) {
			MsgUtils.warn("������Ʒ�ӿ��Ѵ���, ������� &4" + item.getName() + " &c�Ƿ��ظ�");
		} 
		else {
			ITEM_DATA.add(item);
			if (isLoaded) {
				item.onEnable();
			}
		}
	}
	
	/**
	 * ע���ӿ�
	 * 
	 * @param name ע������
	 */
	public void cancel(String name) {
		for (AbstractSpecialItem specialitem : ITEM_DATA) {
			if (specialitem.getName() != null && specialitem.getName().equals(specialitem)) {
				specialitem.onDisable();
				ITEM_DATA.remove(specialitem);
			}
		}
	}
	
	/**
	 * ע���ӿ�
	 * 
	 * @param plugin ע����
	 */
	public void cancel(Plugin plugin) {
		for (AbstractSpecialItem specialitem : ITEM_DATA) {
			if (specialitem.getPlugin() != null && specialitem.getPlugin().equals(plugin)) {
				specialitem.onDisable();
				ITEM_DATA.remove(specialitem);
			}
		}
	}
	
	/**
	 * �ж������Ƿ����
	 * 
	 * @param name ע������
	 * @return boolean
	 */
	public boolean contains(String name) {
		for (AbstractSpecialItem specialitem : ITEM_DATA) {
			if (specialitem.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ����������ע��ӿ�
	 */
	public void loadItems() {
		ITEM_DATA.forEach(x -> x.onEnable());
		isLoaded = true;
	}
	
	/**
	 * ע��������ע��ӿ�
	 */
	public void unloadItems() {
		ITEM_DATA.forEach(x -> x.onDisable());
		ITEM_DATA.clear();
	}
	
	@EventHandler
	public void onDisable(PluginDisableEvent e) {
		cancel(e.getPlugin());
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void click(InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (ItemUtils.isNull(e.getCurrentItem()) || ItemUtils.isNull(e.getCursor())) {
			return;
		}
		Player player = (Player) e.getWhoClicked();
		for (AbstractSpecialItem specialitem : ITEM_DATA) {
			for (SpecialItemResult result : specialitem.isCorrectClick(player, e.getCurrentItem(), e.getCursor())) {
				if (result == SpecialItemResult.CANCEL) {
					e.setCancelled(true);
				}
				else if (result == SpecialItemResult.BREAK) {
					return;
				}
				else if (result == SpecialItemResult.REMOVE_ITEM_CURRENT) {
					e.setCurrentItem(null);
				}
				else if (result == SpecialItemResult.REMOVE_ITEM_CURSOR) {
					e.getWhoClicked().setItemOnCursor(null);
				}
				else if (result == SpecialItemResult.REMOVE_ITEM_CURRENT_AMOUNT_1) {
					if (e.getCurrentItem().getAmount() > 1) {
						e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
					}
					else {
						e.setCurrentItem(null);
					}
				}
				else if (result == SpecialItemResult.REMOVE_ITEM_CURSOR_AMOUNT_1) {
					if (e.getCursor().getAmount() > 1) {
						e.getCursor().setAmount(e.getCursor().getAmount() - 1);
					}
					else {
						e.getWhoClicked().setItemOnCursor(null);
					}
				}
			}
		}
	}
}
