package ca.ACOII.forgeborn.core.runtime;

import ca.ACOII.forgeborn.core.model.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Calculator {
    private static boolean canLearnSkill(ConstructState construct, Skill skill) {
        for (Prerequisite prereq : skill.prereqs()) {
            if (prereq instanceof PartPrereq(String slotType, String statId, int value)) {
                int total = 0;
                for (Part p : construct.getFittedParts()) {
                    if (Objects.equals(slotType, p.slotType())) {
                        total += p.statVals().getOrDefault(statId, 0);
                    }
                }
                if (total < value) return false;
            }
            if (prereq instanceof SkillPrereq(String skillId)) {
                if (construct.getLearnedSkillIds().isEmpty() || !construct.getLearnedSkillIds().contains(skillId)) return false;
            }
            if (prereq instanceof StatPrereq(String statId, int val)) {
                if (construct.getEffectiveStat(statId) < val) return false;
            }
        }
        return true;
    }

    public static List<Skill> learnableSkills(ConstructState construct, List<Skill> allSkills) {
        return allSkills.stream().filter(new Predicate<Skill>() {
            @Override
            public boolean test(Skill skill) {
                return canLearnSkill(construct, skill) && !construct.getLearnedSkillIds().contains(skill.id());
            }
        }).toList();
    }

    public static void addWear(int damage) {}
    public static void removeWear(int recovery) {}
}
