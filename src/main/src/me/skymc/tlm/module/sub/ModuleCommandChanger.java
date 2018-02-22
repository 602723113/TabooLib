package me.skymc.tlm.module.sub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import me.skymc.tlm.module.ITabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��22�� ����1:32:29
 */
public class ModuleCommandChanger implements ITabooLibraryModule, Listener {

	@Override
	public String getName() {
		return "CommandChanger";
	}
	
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		// ѭ������
		for (String id : getConfig().getConfigurationSection("Commands").getKeys(false)) {
			// ��ȡ����
			String key = getConfig().getString("Commands." + id + ".Input");
			// �ж�����
			if (e.getMessage().startsWith(key)) {
				// �ж�ִ�з�ʽ
				if (!getConfig().contains("Commands." + id + ".ReplaceMode") || getConfig().getString("Commands." + id + ".ReplaceMode").equals("PLAYER")) {
					// �滻����
					e.setMessage(e.getMessage().replace(key, getConfig().getString("Commands." + id + ".Replace")));
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void command(ServerCommandEvent e) {
		// ѭ������
		for (String id : getConfig().getConfigurationSection("Commands").getKeys(false)) {
			// ��ȡ����
			String key = getConfig().getString("Commands." + id + ".Input");
			// �ж�����
			if (e.getCommand().startsWith(key)) {
				// �ж�ִ�з�ʽ
				if (!getConfig().contains("Commands." + id + ".ReplaceMode") || getConfig().getString("Commands." + id + ".ReplaceMode").equals("CONSOLE")) {
					// �滻����
					e.setCommand(e.getCommand().replace(key, getConfig().getString("Commands." + id + ".Replace")));
					return;
				}
			}
		}
	}
}
