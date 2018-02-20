package me.skymc.taboolib.commands.sub;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import me.skymc.taboolib.TabooLib;
import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.jsonformatter.JSONFormatter;
import me.skymc.taboolib.jsonformatter.click.SuggestCommandEvent;
import me.skymc.taboolib.jsonformatter.hover.ShowTextEvent;

public class HelpCommand extends SubCommand {
	
	public HelpCommand(CommandSender sender, String[] args) {
		super(sender, args);
		
		HashMap<String, String> helps = new LinkedHashMap<>();
		helps.put("/taboolib save ��8[��7���ơ�8]", "��e����������Ʒ");
		helps.put("/taboolib item ��8[��7���ơ�8] ��8<��7��ҡ�8> ��8<��7������8>", "��e���������Ʒ");
		helps.put("/taboolib iteminfo", "��e�鿴��Ʒ��Ϣ");
		helps.put("/taboolib itemlist", "��e�鿴������Ʒ");
		helps.put("/taboolib itemreload", "��e������Ʒ����");
		helps.put("��a", null);
		helps.put("/taboolib attributes", "��e�鿴��������");
		helps.put("/taboolib enchants", "��e�鿴���и�ħ");
		helps.put("/taboolib potions", "��e�鿴����ҩˮ");
		helps.put("/taboolib flags", "��e�鿴���б�ǩ");
		helps.put("/taboolib slots", "��e�鿴���в�λ");
		helps.put("��b", null);
		helps.put("/taboolib getvariable ��8[��7-s|a��8] ��8[��7����8]", "��e�鿴����");
		helps.put("/taboolib setvariable ��8[��7-s|a��8] ��8[��7����8] ��8[��7ֵ��8]", "��e���ı���");
		helps.put("��c", null);
		helps.put("/taboolib cycle list", "��e�г�����ʱ������");
		helps.put("/taboolib cycle info ��8[��7���ơ�8]", "��e��ѯ�������Ϣ");
		helps.put("/taboolib cycle reset ��8[��7���ơ�8]", "��e��ʼ��ʱ������");
		helps.put("/taboolib cycle update ��8[��7���ơ�8]", "��e����ʱ������");
		helps.put("��d", null);
		helps.put("/taboolib shell load ��8[��7���ơ�8]", "��e����ĳ���ű�");
		helps.put("/taboolib shell unload ��8[��7���ơ�8]", "��eж��ĳ���ű�");
		helps.put("��e", null);
		helps.put("/taboolib importdata", "��4�����ݿ⵼�뱾������ ��8(�ò�������������ݿ�)");
		
		if (sender instanceof ConsoleCommandSender || TabooLib.getVerint() < 10900) {
			sender.sendMessage("��f");
			sender.sendMessage("��b��l----- ��3��lTaooLib Commands ��b��l-----");
			sender.sendMessage("��f");
			// ��������
			for (Entry<String, String> entry : helps.entrySet()) {
				if (entry.getValue() == null) {
					sender.sendMessage("��f");
				} else {
					sender.sendMessage("��f " + entry.getKey() + " ��6- " + entry.getValue());
				}
			}
			sender.sendMessage("��f");
		}
		else if (sender instanceof Player) {
			JSONFormatter json = new JSONFormatter();
			json.append("��f"); json.newLine();
			json.append("��b��l----- ��3��lTaooLib Commands ��b��l-----"); json.newLine();
			json.append("��f"); json.newLine();
			// ��������
			for (Entry<String, String> entry : helps.entrySet()) {
				if (entry.getValue() == null) {
					json.append("��f"); json.newLine();
				} else {
					json.appendHoverClick("��f " + entry.getKey() + " ��6- " + entry.getValue(), new ShowTextEvent("��f�������ָ��"), new SuggestCommandEvent(entry.getKey().split("��")[0])); json.newLine();
				}
			}
			json.append("��f");
			json.send((Player) sender);
		}
	}
}
