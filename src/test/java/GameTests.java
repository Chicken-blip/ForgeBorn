import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;
import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.core.runtime.Calculator;
import ca.ACOII.forgeborn.core.runtime.ConstructState;
import ca.ACOII.forgeborn.load.DefinitionLoader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        ConstructState construct = new ConstructState(new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        for (String key : base.statVals().keySet()) {
            construct.stats().put(key, base.statVals().get(key));
        }

        List<Skill> skills = DefinitionLoader.loadSkills();
        assertTrue(Calculator.learnableSkills(construct, skills).isEmpty());

        List<Part> parts = DefinitionLoader.loadParts();
        Part targetPart = parts.getFirst();
        construct.fittedParts().add(targetPart);
        for (String str : targetPart.statVals().keySet()) {
            construct.stats().put(str, construct.stats().getOrDefault(str, 0) + targetPart.statVals().get(str));
        }

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

        construct.learnedSkillIds().add("powered_strike");

        assertEquals(2, Calculator.learnableSkills(construct, skills).size());

    }
}
