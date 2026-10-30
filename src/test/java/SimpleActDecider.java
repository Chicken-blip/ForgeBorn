import ca.ACOII.forgeborn.core.duel.DuelConstruct;
import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.core.runtime.ActDecider;
import ca.ACOII.forgeborn.load.Definitions;

import java.util.function.Predicate;

public class SimpleActDecider implements ActDecider {

    @Override
    public Skill pickAction(DuelConstruct construct, Definitions def) {
        for (Skill skill : construct.getLoadout()) {
            if (skill.energyCost() <= construct.getEnergy()) return skill;
        }
        return def.skillList.stream().filter(new Predicate<Skill>() {
            @Override
            public boolean test(Skill skill) {
                return skill.isBasic();
            }
        }).toList().getFirst();
    }
}
