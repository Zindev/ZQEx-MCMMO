package me.Zindev.zqexmcm;

import java.lang.reflect.Method;

import me.Zindev.zqexmcm.actions.McAddExpAction;
import me.Zindev.zqexmcm.actions.McAddLevelAction;
import me.Zindev.zqexmcm.actions.McOperateExpAction;
import me.Zindev.zqexmcm.actions.McOperateLevelAction;
import me.Zindev.zqexmcm.conditions.McCheckExpCondition;
import me.Zindev.zqexmcm.conditions.McCheckLevelCondition;
import me.Zindev.zqexmcm.objectives.SaGainExpObjective;
import me.Zindev.zquest.objects.extension.ZQuestAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		ZQuestAPI.registerExtension(McAddExpAction.class, this);
		ZQuestAPI.registerExtension(McAddLevelAction.class, this);
		ZQuestAPI.registerExtension(McOperateExpAction.class, this);
		ZQuestAPI.registerExtension(McOperateLevelAction.class, this);
		ZQuestAPI.registerExtension(McCheckExpCondition.class, this);
		ZQuestAPI.registerExtension(McCheckLevelCondition.class, this);
		ZQuestAPI.registerExtension(SaGainExpObjective.class, this);
		Bukkit.getPluginManager().registerEvents(new MyListener(), this);
	}
	@Override
	public void onDisable() {
		if(Bukkit.getPluginManager().isPluginEnabled("ZQuest")){
			ZQuestAPI.unregisterAll(this);
		}
	}
	public static void reflectCheckExp(McMMOPlayer p,String skillType,String xpGainReason){
		XPGainReason reason = XPGainReason.getXPGainReason(xpGainReason);
		SkillType stype = SkillType.getSkill(skillType);
		try {
			Method m = p.getClass().getDeclaredMethod("checkXp", SkillType.class,XPGainReason.class);
			m.setAccessible(true);
			m.invoke(p, stype,reason);
			m.setAccessible(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
