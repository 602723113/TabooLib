package me.skymc.tlm.command.sub;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skymc.taboolib.commands.SubCommand;
import me.skymc.tlm.TLM;
import me.skymc.tlm.module.TabooLibraryModule;
import me.skymc.tlm.module.sub.ModuleKits;

/**
 * @author sky
 * @since 2018��2��18�� ����2:53:58
 */
public class TLMKitCommand extends SubCommand {

	/**
	 * @param sender
	 * @param args
	 */
	public TLMKitCommand(CommandSender sender, String[] args) {
		super(sender, args);
		if (TabooLibraryModule.getInst().valueOf("Kits") == null) {
			TLM.getInst().getLanguage().get("KIT-DISABLE").send(sender);
			return;
		}

		// ��ȡģ��
		ModuleKits moduleKits = (ModuleKits) TabooLibraryModule.getInst().valueOf("Kits");
		
		// �ж�����
		if (args.length == 1) {
			TLM.getInst().getLanguage().get("KIT-EMPTY").send(sender);
			return;
		}
		
		else if (args[1].equalsIgnoreCase("reward")) {
			// �ж�Ȩ��
			if (!sender.hasPermission("taboolib.kit.reward")) {
				TLM.getInst().getLanguage().get("NOPERMISSION-KIT-REWARD").send(sender);
				return;
			}
			
			// ������
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("KIT-NAME").send(sender);
				return;
			}
			
			// ���������
			if (!moduleKits.contains(args[2])) {
				TLM.getInst().getLanguage().get("KIT-NOTFOUND").addPlaceholder("$kit", args[2]).send(sender);
				return;
			}
			
			// ��ȡ���
			Player player;
			if (args.length > 3) {
				player = Bukkit.getPlayerExact(args[3]);
				// ��Ҳ�����
				if (player == null) {
					TLM.getInst().getLanguage().get("KIT-OFFLINE").addPlaceholder("$name", args[3]).send(sender);
					return;
				}
			} else if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				TLM.getInst().getLanguage().get("KIT-CONSOLE").send(sender);
				return;
			}
			
			// �Ƿ���ȡ
			if (moduleKits.isPlayerRewared(player, args[2])) {
				// �Ƿ�ֻ����ȡһ��
				if (moduleKits.isDisposable(args[2])) {
					TLM.getInst().getLanguage().get("KIT-DISPOSABLE").addPlaceholder("$kit", args[2]).send(sender);
					return;
				}
				// �Ƿ���ȴ��
				if (moduleKits.isPlayerCooldown(player, args[2])) {
					TLM.getInst().getLanguage().get("KIT-COOLDOWN").addPlaceholder("$kit", args[2]).send(sender);
					return;
				}
			}
			
			// �Ƿ���Ȩ����ȡ
			String permission = moduleKits.getPermission(args[2]);
			if (permission != null && !player.hasPermission(permission)) {
				// ��ʾ��Ϣ
				player.sendMessage(moduleKits.getPermissionMessage(args[2]));
				return;
			}
			
			// �������
			List<ItemStack> items = moduleKits.getItems(args[2]);
			for (ItemStack item : items) {
				// ������Ʒ
				HashMap<Integer, ItemStack> result = player.getInventory().addItem(item);
				// ��������ռ䲻��
				if (result.size() > 0 && moduleKits.isFullDrop(args[2])) {
					// ������Ʒ
					player.getWorld().dropItem(player.getLocation(), item);
				}
			}
			
			// ����ȡ
			moduleKits.setPlayerReward(player, args[2], true);
			
			// ��ʾ��Ϣ
			TLM.getInst().getLanguage().get("KIT-SUCCESS").addPlaceholder("$kit", args[2]).send(sender);
		}
		else if (args[1].equalsIgnoreCase("reset")) {
			// �ж�Ȩ��
			if (!sender.hasPermission("taboolib.kit.reset")) {
				TLM.getInst().getLanguage().get("NOPERMISSION-KIT-RESET").send(sender);
				return;
			}
			
			// ������
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("KIT-NAME").send(sender);
				return;
			}
			
			// ���������
			if (!moduleKits.contains(args[2])) {
				TLM.getInst().getLanguage().get("KIT-NOTFOUND").addPlaceholder("$kit", args[2]).send(sender);
				return;
			}
			
			// ��ȡ���
			Player player;
			if (args.length > 3) {
				player = Bukkit.getPlayerExact(args[3]);
				// ��Ҳ�����
				if (player == null) {
					TLM.getInst().getLanguage().get("KIT-OFFLINE").addPlaceholder("$name", args[3]).send(sender);
					return;
				}
				else {
					moduleKits.setPlayerReward(player, args[2], false);
					TLM.getInst().getLanguage().get("KIT-RESET-PLAYER").addPlaceholder("$kit", args[2]).addPlaceholder("$player", player.getName()).send(sender);
				}
			} else {
				moduleKits.resetKit(args[2]);
				TLM.getInst().getLanguage().get("KIT-RESET-ALL").addPlaceholder("$kit", args[2]).send(sender);
			}
		}
		else {
			TLM.getInst().getLanguage().get("COMMAND-ERROR").send(sender);
		}
	}

}
