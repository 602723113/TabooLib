package me.skymc.taboolib.support;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.skymc.taboolib.database.GlobalDataManager;
import me.skymc.tlm.TLM;
import me.skymc.tlm.module.TabooLibraryModule;
import me.skymc.tlm.module.sub.ModuleKits;

public class SupportPlaceholder extends EZPlaceholderHook {
	
	public SupportPlaceholder(Plugin plugin, String identifier) {
		super(plugin, identifier);
	}

	@Override
	public String onPlaceholderRequest(Player player, String args) {
		if (args.startsWith("variable_")) {
			String[] value = args.split("_");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < value.length ; i++) {
				sb.append(value[i] + "_");
			}
			return GlobalDataManager.getVariableAsynchronous(sb.substring(0, sb.length() - 1), "<none>");
		}
		if (args.startsWith("tlm_kit_")) {
			// �Ƿ�����
			if (TabooLibraryModule.getInst().valueOf("Kits") == null) {
				return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.0").asString();
			}
			
			// ��ȡģ��
			ModuleKits moduleKits = (ModuleKits) TabooLibraryModule.getInst().valueOf("Kits");
			String kit = args.split("_")[2];
			
			// ���������
			if (!moduleKits.contains(kit)) {
				return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.1").asString();
			}
			
			// �Ƿ���ȡ
			if (moduleKits.isPlayerRewared(player, kit)) {
				// �Ƿ�ֻ����ȡһ��
				if (moduleKits.isDisposable(kit)) {
					return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.2").asString();
				}
				// �Ƿ���ȴ��
				if (moduleKits.isPlayerCooldown(player, kit)) {
					return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.4").asString();
				}
			}
			
			// �Ƿ���Ȩ����ȡ
			String permission = moduleKits.getPermission(kit);
			if (permission != null && !player.hasPermission(permission)) {
				return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.5").asString();
			}
			
			// ����ȡ
			return TLM.getInst().getLanguage().get("KIT-PLACEHOLDER.3").asString();
		}
		return null;
	}

}
