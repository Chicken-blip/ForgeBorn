package ca.ACOII.forgeborn.core.duel;

import ca.ACOII.forgeborn.core.model.Skill;

import java.util.List;
import java.util.function.Predicate;

public class Duel {
    DuelConstruct player, opponent;
    //0 = player, 1 = opponent;
    int currTurn;
    List<Skill> skillLibrary;
    public Duel(DuelConstruct player, DuelConstruct opponent, List<Skill> allSkills) {
        this.player = player;
        this.opponent = opponent;
        currTurn = 0;
        skillLibrary = allSkills;
    }

    public DuelConstruct getCurrentDuelist() {
        return currTurn == 0 ? player : opponent;
    }
    public DuelConstruct getOtherDuelist() {
        return currTurn == 0 ? opponent : player;
    }
    private int startingDuelist() {
        if (player.getConstruct().getEffectiveStat("speed") == opponent.getConstruct().getEffectiveStat("speed")) return 0;
        return player.getConstruct().getEffectiveStat("speed") > opponent.getConstruct().getEffectiveStat("speed") ? 0 : 1;
    }

    private int dealtDamage(int damage) {
        DuelConstruct defender = getOtherDuelist();
        int dealtDamage = damage - defender.getConstruct().getEffectiveStat("endurance");
        return Math.max(1, dealtDamage);
    }
    public boolean act(Skill skill) {
        DuelConstruct current = getCurrentDuelist();
        if (current.getEnergy() < skill.energyCost()) return false;
        current.spendEnergy(skill.energyCost());
        getOtherDuelist().applyDamage(dealtDamage(skill.damage()));
        switchTurn();
        return true;
    }
    public void energyRegen(int amount) {getCurrentDuelist().regenEnergy(amount);}
    public void switchTurn() {currTurn = (currTurn + 1) % 2;}
    public boolean duelOver() {return player.isDefeated() || opponent.isDefeated();}
    public List<Skill> getAvailableSkills() {
        return skillLibrary.stream().filter(new Predicate<Skill>() {
            @Override
            public boolean test(Skill skill) {
                return getCurrentDuelist().getLoadout().contains(skill.id()) || skill.isBasic();
            }
        }).toList();
    }
}
