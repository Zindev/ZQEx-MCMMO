package me.Zindev.zqexmcm;

import me.Zindev.zqexmcm.objectives.SaGainExpObjective;
import me.Zindev.zquest.objects.extension.ZQuestAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

public class MyListener implements Listener{
	@EventHandler
	public void xpGain(McMMOPlayerXpGainEvent e){
		Player p = e.getPlayer();
		if(!ZQuestAPI.playerIsMakingQuest(p.getUniqueId()))return;
		SaGainExpObjective ob = ZQuestAPI.playerHasObjective(p.getUniqueId(), SaGainExpObjective.class,
				true);
		if(ob != null)ob.checkIn(e.getSkill().getName(),Math.round(e.getRawXpGained()), p);
	}

}
