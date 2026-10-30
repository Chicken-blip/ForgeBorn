package ca.ACOII.forgeborn.core.runtime;

import ca.ACOII.forgeborn.core.duel.DuelConstruct;
import ca.ACOII.forgeborn.core.model.Skill;
import ca.ACOII.forgeborn.load.Definitions;

public interface ActDecider {
    Skill pickAction(DuelConstruct construct, Definitions def);
}
