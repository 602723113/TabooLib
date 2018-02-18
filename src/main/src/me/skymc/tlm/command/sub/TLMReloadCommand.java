package me.skymc.tlm.command.sub;

import org.bukkit.command.CommandSender;

import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.tlm.TLM;
import me.skymc.tlm.module.ITabooLibraryModule;
import me.skymc.tlm.module.TabooLibraryModule;

/**
 * @author sky
 * @since 2018��2��18�� ����2:09:34
 */
public class TLMReloadCommand extends SubCommand {

	/**
	 * @param sender
	 * @param args
	 */
	public TLMReloadCommand(CommandSender sender, String[] args) {
		super(sender, args);
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

}
