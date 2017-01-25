package me.Zindev.zqexmcm.objectives;

import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestObjective;
import me.Zindev.zquest.objects.extension.QuestObjectiveMark;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.SkillAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;

@QuestObjectiveMark(objectiveID="McMmoXPGainObjective",hideSuccess= true)
public class SaGainExpObjective extends QuestObjective{
	private static final long serialVersionUID = 1L;
	private Integer amt;
	private String skillType;
	
	
	public SaGainExpObjective(){
		setVariables(new String[2]);
		setVariable("<amount>", "remaining XP", 0);
		setVariable("<name>", "name of the skill", 1);
		setCompleteMessage("&aYou just completed a gain XP Objective !");
		setDisplayName("&dGain <amount> XP in <name> skill");
		this.amt = 500;
		skillType = SkillType.ARCHERY.getName();
	}
	@Override
	public void success() {
		check();
		
	}

	@Override
	public boolean check() {
		if(amt > 0){return false;}

		return true;
	}
	public boolean checkIn(String stype,Integer cms,Player p){
		if(!SkillType.getSkill(stype).equals(SkillType.getSkill(skillType)))return false;
		if(!checkConditions(p))return false;
		amt = amt>cms?amt-cms:0;
		success();
		if(amt <= 0)Gerekli.yollaMesaj(p, getCompleteMessage());
		return true;
	}

	@Override
	public String buildString() {
		return "(amount:"+amt+",skillType:"+skillType+")";
	}
	public int getAmt() {
		return amt;
	}
	public void setAmt(Integer amt) {
		this.amt = amt;
	}
	@Override
	public String getDisplayName() {
		return super.getDisplayName().replaceAll("<amount>", ""+amt).replaceAll("<name>", skillType);
	}
	@Override
	public String getSuccessMessage() {
		if(amt == 0){
			return super.getCompleteMessage();
		}
		return super.getSuccessMessage().replaceAll("<amount>", ""+amt).replaceAll("<name>", skillType);
	}
	@Override
	public String getCompleteMessage() {
		return super.getCompleteMessage().replaceAll("<amount>", ""+amt).replaceAll("<name>", skillType);
	}
	@Override
	public ArrayList<Object> getFields() {
		return getFields(new ArrayList<Object>(Arrays.asList(
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&5&lAmount", 
								new ArrayList<String>(Arrays.asList(
										"&dHow much XP player",
										"&dshould gain ?",
										"&5&lCurrently:&d<value>"
										))
								, (short)0)
						
						, null, null, "amt", "&dRequired Experience", 0, 9999999),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lSkill Type", 
								new ArrayList<String>(Arrays.asList(
										"&cWhich skill do you",
										"&cwant to check ?",
										"&4&lCurrently:&c<value>"
										))
								, (short)0)
						
						, null, null, "skillType", "&cSkill Type", 0, 0,new ArrayList<String>(SkillAPI.getSkills()))
						
					)));
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
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&dPlayer needs to collect",
				"&damount of choosen skill's xp."
				));

	}
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

}
