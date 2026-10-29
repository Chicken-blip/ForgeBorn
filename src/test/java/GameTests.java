import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.load.DefinitionLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {
    @Test
    public void test_projectCompiles() {
        assertTrue(true);
    }

    @Test
    public void test_loading() {
        //TODO: Edit when content has been added
        List list = DefinitionLoader.loadFrames();
        assertTrue(list.isEmpty());
        list = DefinitionLoader.loadShopTiers();
        assertTrue(list.isEmpty());
        list = DefinitionLoader.loadSkills();
        assertTrue(list.isEmpty());
        list = DefinitionLoader.loadParts();
        assertTrue(list.isEmpty());
    }
}
