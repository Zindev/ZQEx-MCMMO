package me.Zindev.zqexmcm.actions;
import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestAction;
import me.Zindev.zquest.objects.extension.QuestActionMark;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.SkillAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.util.player.UserManager;

@QuestActionMark(actionID ="McMmoAddExp")
public class McAddExpAction extends QuestAction{
	private static final long serialVersionUID = 1L;
	
	
	private Float amount;
	private String skillType;
	private String giveType;
	public McAddExpAction() {
		skillType = SkillType.ARCHERY.getName();
		amount = 2.0f;
		giveType = "MODIFIED";
	}
	@Override
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&dGive some XP to one of",
				"&dthe player's skills."
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
				",giveType:"+giveType+
				")";
	}

	@Override
	public void execute(Player p) {;
		if(UserManager.hasPlayerDataKey(p)){
			switch (giveType) {
			
			case "RAW":
				ExperienceAPI.addRawXP(p, skillType, amount, XPGainReason.UNKNOWN.name());
				break;
			case "MULTIPLIED":
				ExperienceAPI.addMultipliedXP(
						p, skillType, Math.round(amount), XPGainReason.UNKNOWN.name());
				break;
			case "MODIFIED":
				ExperienceAPI.addModifiedXP(
						p, skillType, Math.round(amount), XPGainReason.UNKNOWN.name());
				break;
			}
		}
		
	}

	@Override
	public ArrayList<Object> getFields() {
		return getFields(new ArrayList<Object>(Arrays.asList(
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&5&lAmount", 
								new ArrayList<String>(Arrays.asList(
										"&dHow much exp do you",
										"&dwant to give ?",
										"&5&lCurrently:&d<value>"
										))
								, (short)0)
						
						, null, null, "amount", "&dExperience", 0, 9999999),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lSkill Type", 
								new ArrayList<String>(Arrays.asList(
										"&cWhich skill do you want",
										"&cto give this XP ?",
										"&4&lCurrently:&c<value>"
										))
								, (short)0)
						
						, null, null, "skillType", "&cSkill Type", 0, 0,new ArrayList<String>(SkillAPI.getSkills())),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.IRON_INGOT), "&3&lGiveType", 
								new ArrayList<String>(Arrays.asList(
										"&3&lRAW:&bGives the amount",
										"&bdirectly.",
										"&3&lMULTIPLIED:&bGives the",
										"&bamount depending on XP Rate",
										"&3&lMODIFIED:&bGives the amount",
										"&bwith XP Rate and skill multiplier",
										"&3&lCurrently:&b<value>"
										))
								, (short)0)
						
						, null, null, "giveType", "&dXP Multiplier", 0, 0,new ArrayList<String>(Arrays.asList(
								"RAW","MULTIPLIED","MODIFIED"
								)))
						
					)));
		
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}

}
