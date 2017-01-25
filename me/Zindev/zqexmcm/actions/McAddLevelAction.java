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
import com.gmail.nossr50.util.player.UserManager;

@QuestActionMark(actionID ="McMmoAddLevel")
public class McAddLevelAction extends QuestAction{
	private static final long serialVersionUID = 1L;
	
	
	private Integer amount;
	private String skillType;
	public McAddLevelAction() {
		skillType = SkillType.ARCHERY.getName();
		amount = 2;
	}
	@Override
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&dIncrease the level of",
				"&dplayer's choosen skill."
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
				")";
	}

	@Override
	public void execute(Player p) {
		if(UserManager.hasPlayerDataKey(p)){
			ExperienceAPI.addLevel(p, skillType, amount);
		}
		
	}

	@Override
	public ArrayList<Object> getFields() {
		return getFields(new ArrayList<Object>(Arrays.asList(
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.DIAMOND), "&5&lAmount", 
								new ArrayList<String>(Arrays.asList(
										"&dHow many levels do you",
										"&dwant to increase ?",
										"&5&lCurrently:&d<value>"
										))
								, (short)0)
						
						, null, null, "amount", "&dLevel", 0, 9999999),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lSkill Type", 
								new ArrayList<String>(Arrays.asList(
										"&cWhich skill do you want",
										"&cto increase ?",
										"&4&lCurrently:&c<value>"
										))
								, (short)0)
						
						, null, null, "skillType", "&cSkill Type", 0, 0,new ArrayList<String>(SkillAPI.getSkills()))
						
					)));
		
	}
	public Integer getAmount() {
		return amount;
	}
	public String getSkillType() {
		return skillType;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}
}
