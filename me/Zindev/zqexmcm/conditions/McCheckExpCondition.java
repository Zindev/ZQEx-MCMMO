package me.Zindev.zqexmcm.conditions;

import java.util.ArrayList;
import java.util.Arrays;

import me.Zindev.zquest.Gerekli;
import me.Zindev.zquest.chestlib.ChestField;
import me.Zindev.zquest.objects.QuestCondition;
import me.Zindev.zquest.objects.extension.QuestConditionMark;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.SkillAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.util.player.UserManager;

@QuestConditionMark(conditionID = "checkMcMmoExp")
public class McCheckExpCondition extends QuestCondition{
	private static final long serialVersionUID = 1L;
	private Integer amount;
	private String skillType;
	private String operation;
	public McCheckExpCondition() {
		setVariables(new String[3]);
		setVariable("<name>", "name of the skill", 0);
		setVariable("<amount>", "condition amount", 1);
		setVariable("<operation>", "operation's name", 2);
		operation = "EQUAL";
		skillType = SkillType.ARCHERY.name();
		amount = 5;
		setDisplayName("&eYour <name>'s XP must <operation> to <amount>");
		setErrMessage("&eYour <name>'s XP is not <operation> to <amount>");
	}
	@Override
	public String getDisplayName() {
		return super.getDisplayName().replaceAll("<amount>", ""+amount).replaceAll("<name>", skillType)
				.replaceAll("<operation>", operation);
	}
	@Override
	public String getErrMessage() {
		// TODO Auto-generated method stub
		return super.getErrMessage().replaceAll("<amount>", ""+amount).replaceAll("<name>", skillType)
				.replaceAll("<operation>", operation);
	}

	@Override
	public ArrayList<String> getWikiDesc() {
		return new ArrayList<String>(Arrays.asList(
				"&dCheck XP of a skill",
				"&dwith given operations."
				));
	}
	@Override
	public boolean check(Player p) {
		if(UserManager.hasPlayerDataKey(p)){
			int current = ExperienceAPI.getXP(p, skillType);
			switch (operation) {
			case "EQUAL":
				return amount == current;
			case "LARGER":
				return current > amount;
			case "SMALLER":
				return current < amount;
			case "LARGER_EQUAL":
				return current >= amount;
			case "SMALLER_EQUAL":
				return current <= amount;
			default:
				break;
			}
		}
		return false;
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
						
						, null, null, "amount", "&dExperience", 0, 9999999),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.GOLD_INGOT), "&4&lSkill Type", 
								new ArrayList<String>(Arrays.asList(
										"&cWhich skill do you",
										"&cwant to check ?",
										"&4&lCurrently:&c<value>"
										))
								, (short)0)
						
						, null, null, "skillType", "&cSkill Type", 0, 0,new ArrayList<String>(SkillAPI.getSkills())),
				new ChestField( 
						Gerekli.yapEsya(new ItemStack(Material.IRON_INGOT), "&3&lOperation", 
								new ArrayList<String>(Arrays.asList(
										"&bWhat do you want to check ?",
										"&3&lCurrently:&b<value>"
										))
								, (short)0)
						
						, null, null, "operation", "&dOperation", 0, 0,new ArrayList<String>(Arrays.asList(
								"EQUAL","LARGER","SMALLER","LARGER_EQUAL","SMALLER_EQUAL"
								)))
						
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
