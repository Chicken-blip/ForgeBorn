package ca.ACOII.forgeborn.core.duel;

import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.core.runtime.ConstructState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DuelConstruct {
    private ConstructState constructRef;
    private int hull, energy;
    private List<Skill> loadout;
    public DuelConstruct(ConstructState reference, List<Skill> allSkills) {
        this.constructRef = reference;
        hull = reference.getEffectiveStat("hull");
        energy = reference.getEffectiveStat("energy");
        loadout = new ArrayList<>();
        for (String skillId : reference.getLearnedSkillIds()) {
            Skill skill = findSkill(skillId, allSkills);
            loadout.add(skill);
        }
    }

    private Skill findSkill(String skillId, List<Skill> allSkills) {
        for (Skill skill : allSkills) {
            if (Objects.equals(skill.id(), skillId)) {
                return skill;
            }
        }
        return null;
    }

    public void applyDamage(int damage) {
        hull = Math.max(hull - damage, 0);
    }
    public void spendEnergy(int amount) {
        energy = Math.max(energy - amount, 0);
    }
    public void regenEnergy(int amount) {
        energy = Math.min(energy + amount, constructRef.getEffectiveStat("energy"));
    }
    public boolean isDefeated() {
        return hull == 0;
    }

    //Getters
    public ConstructState getConstruct() {
        return constructRef;
    }
    public int getHull() {
        return hull;
    }
    public int getEnergy() {
        return energy;
    }
    public List<Skill> getLoadout() {
        return loadout;
    }
}
