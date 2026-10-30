package ca.ACOII.forgeborn.core.runtime;

import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;

import java.util.*;

public class ConstructState {
    private final List<String> learnedSkillIds;
    private List<Part> fittedParts;
    private Map<String, Integer> stats;
    private int wear;
    public static int maxWear = 100;
    public static String[] trainiableSkills = {"power", "speed", "endurance"};
    public ConstructState(Frame base) {
        learnedSkillIds = new ArrayList<>();
        fittedParts = new ArrayList<>();
        stats = new HashMap<>();
        for (String stat : base.statVals().keySet()) {
            stats.put(stat, base.statVals().get(stat));
        }
        wear = 0;
    }
    public void addWear(int damage) {
        wear = Math.min(wear + damage, maxWear);
    }
    public void removeWear(int recovery) {
        wear = Math.max(0, wear - recovery);
    }
    public boolean trainStat(String statId, int amount) {
        if (!Arrays.stream(trainiableSkills).toList().contains(statId)) return false;
        stats.put(statId, stats.get(statId) + amount);
        addWear((int) (Math.random() * 6) + 5);
        return true;
    }
    public boolean fitPart(Part p) {
        if (getPartAtSlot(p.slotType()) != null) return false;
        fittedParts.add(p);
        return true;
    }
    public boolean removePart(String slot) {
        for (Part p : fittedParts) {
            if (Objects.equals(p.slotType(), slot)) {
                fittedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public void learnSkill(String skillId) {
        learnedSkillIds.add(skillId);
    }


    //Getters
    public List<String> getLearnedSkillIds() {
        return learnedSkillIds;
    }
    public List<Part> getFittedParts() {
        return fittedParts;
    }
    public Map<String, Integer> getStats() {
        return stats;
    }
    public int getEffectiveStat(String id) {
        int base = getBaseStat(id);
        for (Part p : fittedParts) {
            base += p.statVals().getOrDefault(id, 0);
        }
        return base;
    }
    public int getBaseStat(String id) {
        return stats.getOrDefault(id, 0);
    }
    public int getWear() {
        return wear;
    }
    public Part getPartAtSlot(String slotId) {
        for (Part p : fittedParts) {
            if (Objects.equals(p.slotType(), slotId)) return p;
        }
        return null;
    }
}
