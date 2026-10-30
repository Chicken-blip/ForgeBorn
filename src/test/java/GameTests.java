import ca.ACOII.forgeborn.core.duel.Duel;
import ca.ACOII.forgeborn.core.duel.DuelConstruct;
import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;
import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.core.runtime.Calculator;
import ca.ACOII.forgeborn.core.runtime.ConstructState;
import ca.ACOII.forgeborn.load.DefinitionLoader;
import ca.ACOII.forgeborn.load.Definitions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {
    public static Definitions def;

    @BeforeAll
    public static void setup() {
        def = new Definitions();
    }
    @Test
    public void test_projectCompiles() {
        assertTrue(true);
    }

    @Test
    public void test_skillLearning() {
        Frame base = def.frameList.getFirst();
        ConstructState construct = new ConstructState(base);

        for (String key : base.statVals().keySet()) {
            construct.getStats().put(key, base.statVals().get(key));
        }

        List<Skill> skills = def.skillList;
        skills = skills.stream().filter(new Predicate<Skill>() {
            @Override
            public boolean test(Skill skill) {
                return Arrays.stream(new String[]{"powered_strike", "powered_jab", "improved_arm_dexterity"}).toList().contains(skill.id());
            }
        }).toList();
        assertEquals(3, skills.size());
        assertTrue(Calculator.learnableSkills(construct, skills).isEmpty());

        Part targetPart = def.partList.getFirst();
        construct.getFittedParts().add(targetPart);

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

        construct.getLearnedSkillIds().add("powered_strike");

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

    }

    @Test
    public void test_statTraining1() {
        List<Frame> frames = def.frameList;
        Frame base = frames.getFirst();
        ConstructState construct = new ConstructState(base);

        int oldStat = construct.getBaseStat("power");
        int oldWear = construct.getWear();
        construct.trainStat("power", 5);
        assertEquals(oldStat + 5, construct.getBaseStat("power"));
        System.out.println(oldWear + " -> " + construct.getWear());
        assertTrue(oldWear < construct.getWear());
    }

    @Test
    public void test_simpleDuel1() {
        List<Frame> frames = def.frameList;
        List<Skill> allSkills = def.skillList;
        Frame playerFrame = frames.getFirst();
        Frame oppFrame = frames.getLast();

        ConstructState player = new ConstructState(playerFrame);
        player.fitPart(def.findPart("test_arm"));
        player.learnSkill("powered_strike");
        ConstructState opponent = new ConstructState(oppFrame);
        opponent.learnSkill("jab");

        DuelConstruct playerDuelist = new DuelConstruct(player, allSkills);
        DuelConstruct oppDuelist = new DuelConstruct(opponent, allSkills);
        Duel duel = new Duel(playerDuelist, oppDuelist, allSkills);
        assertEquals(playerDuelist, duel.getCurrentDuelist());
        assertEquals(oppDuelist, duel.getOtherDuelist());

        SimpleActDecider oppDecider = new SimpleActDecider();
        System.out.println("Player - Hull: " + playerDuelist.getHull() + " | Opponent - Hull: " + oppDuelist.getHull());

        while (!duel.duelOver()) {
            if (duel.getCurrentDuelist() == playerDuelist) {
                duel.act(playerDuelist.getLoadout().getFirst());
            } else {
                duel.act(oppDecider.pickAction(oppDuelist, def));
            }
            System.out.println("Player - Hull: " + playerDuelist.getHull() + " | Opponent - Hull: " + oppDuelist.getHull());
        }

        String winner = (playerDuelist.getHull() > 0 ? playerFrame.name() : oppFrame.name());
        System.out.println("WINNER: " + winner);
        assertEquals(playerFrame.name(), winner);
    }
}
