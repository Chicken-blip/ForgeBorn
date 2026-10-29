import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;
import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.core.runtime.Calculator;
import ca.ACOII.forgeborn.core.runtime.ConstructState;
import ca.ACOII.forgeborn.load.DefinitionLoader;
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
    @Test
    public void test_projectCompiles() {
        assertTrue(true);
    }

    @Test
    public void test_skillLearning() {
        List<Frame> frames = DefinitionLoader.loadFrames();
        Frame base = frames.getFirst();
        ConstructState construct = new ConstructState(base);

        for (String key : base.statVals().keySet()) {
            construct.getStats().put(key, base.statVals().get(key));
        }

        List<Skill> skills = DefinitionLoader.loadSkills();
        skills = skills.stream().filter(new Predicate<Skill>() {
            @Override
            public boolean test(Skill skill) {
                return Arrays.stream(new String[]{"powered_strike", "powered_jab", "improved_arm_dexterity"}).toList().contains(skill.id());
            }
        }).toList();
        assertEquals(3, skills.size());
        assertTrue(Calculator.learnableSkills(construct, skills).isEmpty());

        List<Part> parts = DefinitionLoader.loadParts();
        Part targetPart = parts.getFirst();
        construct.getFittedParts().add(targetPart);

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

        construct.getLearnedSkillIds().add("powered_strike");

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

    }

    @Test
    public void test_statTraining1() {
        List<Frame> frames = DefinitionLoader.loadFrames();
        Frame base = frames.getFirst();
        ConstructState construct = new ConstructState(base);

        int oldStat = construct.getBaseStat("power");
        int oldWear = construct.getWear();
        construct.trainStat("power", 5);
        assertEquals(oldStat + 5, construct.getBaseStat("power"));
        System.out.println(oldWear + " -> " + construct.getWear());
        assertTrue(oldWear < construct.getWear());
    }
}
