package me.skymc.taboolib.commands.taboolib;

import com.ilummc.tlib.resources.TLocale;
import me.skymc.taboolib.Main;
import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.database.GlobalDataManager;
import me.skymc.taboolib.timecycle.TimeCycle;
import me.skymc.taboolib.timecycle.TimeCycleEvent;
import me.skymc.taboolib.timecycle.TimeCycleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class CycleUpdateCommand extends SubCommand {

	public CycleUpdateCommand(CommandSender sender, String[] args) {
		super(sender, args);
		if (args.length < 2) {
			TLocale.sendTo(sender, "COMMANDS.PARAMETER.UNKNOWN");
			return;
		}
		
		TimeCycle cycle = TimeCycleManager.getTimeCycle(args[2]);
		if (cycle == null) {
			TLocale.sendTo(sender, "COMMANDS.TABOOLIB.TIMECYCLE.INVALID-CYCLE", args[2]);
			return;
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// 重置
				GlobalDataManager.setVariable("timecycle:" + cycle.getName(), String.valueOf(System.currentTimeMillis()));
				// 触发器
				Bukkit.getPluginManager().callEvent(new TimeCycleEvent(cycle));
				// 提示
				TLocale.sendTo(sender, "COMMANDS.TABOOLIB.TIMECYCLE.CYCLE-UPDATE", args[2]);
			}
		}.runTaskAsynchronously(Main.getInst());
	}

	@Override
	public boolean command() {
		return true;
	}

}
