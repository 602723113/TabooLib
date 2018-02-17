package me.skymc.taboolib.inventory.speciaitem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author sky
 * @since 2018��2��17�� ����8:35:42
 */
public abstract interface AbstractSpecialItem {
	
	/**
	 * ���ӿڱ�����
	 */
	default void onEnable() {}
	
	/**
	 * ���ӿڱ�ж��
	 */
	default void onDisable() {}
	
	/**
	 * ��ȡʶ������
	 * 
	 * @return String
	 */
	abstract String getName();
	
	/**
	 * ��ȡ������
	 * 
	 * @return {@link Plugin}
	 */
	abstract Plugin getPlugin();
	
	/**
	 * �Ƿ���е���¼�
	 * 
	 * @param player ���
	 * @param currentItem �����Ʒ
	 * @param cursorItem ������Ʒ
	 * @return {@link SpecialItemResult[]}
	 */
	abstract SpecialItemResult[] isCorrectClick(Player player, ItemStack currentItem, ItemStack cursorItem);
}
