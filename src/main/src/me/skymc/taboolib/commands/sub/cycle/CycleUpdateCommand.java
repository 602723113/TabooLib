package me.skymc.taboolib.commands.sub.cycle;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.database.GlobalDataManager;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.timecycle.TimeCycle;
import me.skymc.taboolib.timecycle.TimeCycleEvent;
import me.skymc.taboolib.timecycle.TimeCycleInitializeEvent;
import me.skymc.taboolib.timecycle.TimeCycleManager;

public class CycleUpdateCommand extends SubCommand {

	public CycleUpdateCommand(CommandSender sender, String[] args) {
		super(sender, args);
		if (args.length < 3) {
			MsgUtils.send(sender, "&c��������ȷ�ļ��������");
			return;
		}
		
		TimeCycle cycle = TimeCycleManager.getTimeCycle(args[2]);
		if (cycle == null) {
			MsgUtils.send(sender, "&c����� &4" + args[2] + " &c������");
			return;
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// ����
				GlobalDataManager.setVariable("timecycle:" + cycle.getName(), String.valueOf(System.currentTimeMillis()));
				// ������
				Bukkit.getPluginManager().callEvent(new TimeCycleEvent(cycle));
				// ��ʾ
				MsgUtils.send(sender, "����� &f" + args[2] + " &7�Ѹ���");
			}
		}.runTaskAsynchronously(Main.getInst());
	}

	@Override
	public boolean command() {
		return true;
	}

}
