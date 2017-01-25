package me.Zindev.zqexmcm.actions;
import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zqexmcm.Main;
import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestAction;
import me.Zindev.zquest.objects.extension.QuestActionMark;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.SkillAPI;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.util.player.UserManager;

@QuestActionMark(actionID ="McMmoOperateExp")
public class McOperateExpAction extends QuestAction{
	private static final long serialVersionUID = 1L;
	
	
	private Integer amount;
	private String skillType;
	private String operation;
	public McOperateExpAction() {
		skillType = SkillType.ARCHERY.getName();
		amount = 2;
		operation = "SET";
	}
	@Override
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&dDo other XP operations",
				"&don player's choosen skill."
				));
	}

	@Override
	public String getWikiName() {
		return "&5"+getID();
	}
	@Override
	public Material getWikiMaterial() {
		return Material.IRON_AXE;
	}
	@Override
	public String buildString() {
		return "(amount:"+amount+
				",skillType:"+skillType+
				",operation:"+operation+
				")";
	}

	@Override
	public void execute(Player p) {
		if(UserManager.hasPlayerDataKey(p)){
			
			switch (operation) {
			case "SET":
				ExperienceAPI.setXP(p, skillType, amount);
				
				break;

			case "DECREASE":
				int current = ExperienceAPI.getXP(p, skillType);
				ExperienceAPI.removeXP(p, skillType, (current-amount > -1?current-amount:0));
				break;
			}
			McMMOPlayer mp = UserManager.getPlayer(p);
			Main.reflectCheckExp(mp, skillType, XPGainReason.UNKNOWN.name());
		}
		
	}

	@Override
	public ArrayList<Object> getFields() {
		return getFields(new ArrayList<Object>(Arrays.asList(
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&5&lAmount", 
								new ArrayList<String>(Arrays.asList(
										"&dOperation will use",
										"&dthis value on XP",
										"&5&lCurrently:&d<value>"
										))
								, (short)0)
						
						, null, null, "amount", "&dAmount", 0, 9999999),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lSkill Type", 
								new ArrayList<String>(Arrays.asList(
										"&cWhich skill do you want",
										"&cto operate ?",
										"&4&lCurrently:&c<value>"
										))
								, (short)0)
						
						, null, null, "skillType", "&cSkill Type", 0, 0,new ArrayList<String>(SkillAPI.getSkills())),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&3&lOperation", 
								new ArrayList<String>(Arrays.asList(
										"&bWhat operation you",
										"&bwant to do ?",
										"&3&lCurrently:&b<value>"
										))
								, (short)0)
						
						, null, null, "operation", "&cOperation", 0, 0,new ArrayList<String>(
								Arrays.asList(
										"SET","DECREASE"
										)
								
								))
						
					)));
		
	}
	public Integer getAmount() {
		return amount;
	}
	public String getSkillType() {
		return skillType;
	}
	public String getOperation() {
		return operation;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
