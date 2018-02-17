package me.skymc.tlm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.tlm.TLM;
import me.skymc.tlm.module.ITabooLibraryModule;
import me.skymc.tlm.module.TabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��18�� ����12:02:08
 */
public class TLMCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("��f");
			sender.sendMessage("��b��l----- ��3��lTaooLibraryModule Commands ��b��l-----");
			sender.sendMessage("��f");
			sender.sendMessage("��7 /tlm list ��f- ��8�г�����ģ��");
			sender.sendMessage("��7 /tlm reload [ģ����/TLM/ALL] ��f- ��8���������ļ�");
			sender.sendMessage("��f");
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			if (args.length != 2) {
				MsgUtils.send(sender, "&4��������");
			}
			
			else if (args[1].equalsIgnoreCase("tlm")) {
				TLM.getInst().reloadConfig();
				MsgUtils.send(sender, "&fTLM &7�����ļ������ء�");
			}
			
			else if (args[1].equalsIgnoreCase("all")) {
				TabooLibraryModule.getInst().reloadConfig();
				MsgUtils.send(sender, "����ģ�������ļ������ء�");
			}
			
			else {
				ITabooLibraryModule module = TabooLibraryModule.getInst().valueOf(args[1]);
				if (module == null) {
					MsgUtils.send(sender, "&4ģ�� &c" + args[1] + " &4�����ڡ�");
				}
				else {
					TabooLibraryModule.getInst().reloadConfig(module, true);
					MsgUtils.send(sender, "ģ�� &f" + args[1] + " &7�������ļ������ء�");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage("��f");
			sender.sendMessage("��b��l----- ��3��lTaooLibraryModule Modules ��b��l-----");
			sender.sendMessage("��f");
			for (ITabooLibraryModule module : TabooLibraryModule.getInst().keySet()) {
				sender.sendMessage("��f - ��8" + module.getName());
			}
			sender.sendMessage("��f");
		}
		return true;
	}
}
