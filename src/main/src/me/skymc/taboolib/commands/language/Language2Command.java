package me.skymc.taboolib.commands.language;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.string.language2.Language2Value;

/**
 * @author sky
 * @since 2018��2��13�� ����5:11:01
 */
public class Language2Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("��f");
			sender.sendMessage("��b��l----- ��3��lLanguage2 Commands ��b��l-----");
			sender.sendMessage("��f");
			sender.sendMessage("��f /language2 send ��8[��7��ҡ�8] ��8[��7���ԡ�8] ��8<��7������8> ��6- ��e����������ʾ");
			sender.sendMessage("��f /language2 reload ��6- ��e�������Կ�");
			sender.sendMessage("��f");
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			MsgUtils.send(sender, "��7������..");
			long time = System.currentTimeMillis();
			Main.getExampleLangauge2().reload();
			MsgUtils.send(sender, "��7�������! ��ʱ: &f" + (System.currentTimeMillis() - time) + "ms");
		}
		else if (args[0].equalsIgnoreCase("send")) {
			if (args.length < 3) {
				MsgUtils.send(sender, "��4��������");
			}
			else {
				// ��ȡ���
				Player player = Bukkit.getPlayerExact(args[1]);
				if (player == null) {
					MsgUtils.send(sender, "��4��Ҳ�����");
				}
				else {
					// ʱ��
					long time = System.currentTimeMillis();
					
					// ��ȡ�����ļ�
					Language2Value value = Main.getExampleLangauge2().get(args[2]);
					
					// ����б�������
					if (args.length > 3) {
						int i = 0;
						for (String variable : args[3].split("\\|")) {
							value.addPlaceholder("$" + i, variable);
							i++;
						}
					}
					
					// ������Ϣ
					value.send(player);
					
					// ��������������
					if (sender instanceof Player && ((Player) sender).getItemInHand().getType().equals(Material.COMMAND)) {
						MsgUtils.send(sender, "��7��Ϣ�ѷ���, ���μ����ʱ: &f" + (System.currentTimeMillis() - time) + "ms");
					}
				}
			}
		}
		return true;
	}
}
