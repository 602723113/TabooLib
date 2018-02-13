package me.skymc.taboolib.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.commands.sub.AttributesCommand;
import me.skymc.taboolib.commands.sub.EnchantCommand;
import me.skymc.taboolib.commands.sub.FlagCommand;
import me.skymc.taboolib.commands.sub.ImportCommand;
import me.skymc.taboolib.commands.sub.InfoCommand;
import me.skymc.taboolib.commands.sub.ItemCommand;
import me.skymc.taboolib.commands.sub.PotionCommand;
import me.skymc.taboolib.commands.sub.SaveCommand;
import me.skymc.taboolib.commands.sub.SlotCommand;
import me.skymc.taboolib.commands.sub.VariableGetCommand;
import me.skymc.taboolib.commands.sub.VariableSetCommand;
import me.skymc.taboolib.commands.sub.itemlist.ItemListCommand;
import me.skymc.taboolib.commands.sub.shell.ShellCommand;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.message.MsgUtils;

public class MainCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("��f");
			sender.sendMessage("��b��l----- ��3��lTaooLib Commands ��b��l-----");
			sender.sendMessage("��f");
			sender.sendMessage("��7 /taboolib save [����] ��f- ��8����������Ʒ");
			sender.sendMessage("��7 /taboolib item/i [��Ʒ] <���> <����> ��f- ��8���������Ʒ");
			sender.sendMessage("��7 /taboolib iteminfo ��f- ��8�鿴��Ʒ��Ϣ");
			sender.sendMessage("��7 /taboolib itemlist ��f- ��8�鿴������Ʒ");
			sender.sendMessage("��7 /taboolib itemreload/ireload ��f- ��8������Ʒ����");
			sender.sendMessage("��f");
			sender.sendMessage("��7 /taboolib attributes ��f- ��8�鿴��������");
			sender.sendMessage("��7 /taboolib enchants ��f- ��8�鿴���и�ħ");
			sender.sendMessage("��7 /taboolib potions ��f- ��8�鿴����ҩˮ");
			sender.sendMessage("��7 /taboolib flags ��f- ��8�鿴���б�ǩ");
			sender.sendMessage("��7 /taboolib slots ��f- ��8�鿴���в�λ");
			sender.sendMessage("��f");
			sender.sendMessage("��7 /taboolib getvariable [-s|a] [��] ��f- ��8�鿴����");
			sender.sendMessage("��7 /taboolib setvariable [-s|a] [��] [ֵ] ��f- ��8���ı���");
			sender.sendMessage("��f");
			sender.sendMessage("��7 /taboolib shell/s load [�ű�] ��f- ��8����ĳ���ű�");
			sender.sendMessage("��7 /taboolib shell/s unload [�ű�] ��f- ��8ж��ĳ���ű�");
			sender.sendMessage("��f");
			sender.sendMessage("��c /taboolib importdata ��f- ��4�����ݿ⵼�뱾������ ��8(�ò�������������ݿ�)");
			sender.sendMessage("��f");
		}
		else if (args[0].equalsIgnoreCase("itemreload") || args[0].equalsIgnoreCase("ireload")) {
			ItemUtils.reloadItemCache();
			ItemUtils.reloadItemName();
			MsgUtils.send(sender, "���سɹ�");
		}
		else if (args[0].equalsIgnoreCase("save")) {
			new SaveCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("enchants")) {
			new EnchantCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("potions")) {
			new PotionCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("flags")) {
			new FlagCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("attributes")) {
			new AttributesCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("slots")) {
			new SlotCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("importdata")) {
			new ImportCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("iteminfo")) {
			new InfoCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("itemlist")) {
			new ItemListCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
			new ItemCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("setvariable")) {
			new VariableSetCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("getvariable")) {
			new VariableGetCommand(sender, args);
		}
		else if (args[0].equalsIgnoreCase("shell") || args[0].equalsIgnoreCase("s")) {
			new ShellCommand(sender, args);
		}
		else {
			MsgUtils.send(sender, "&4ָ�����");
		}
		return true;
	}
}
