package ca.ACOII.forgeborn.load;

import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;
import ca.ACOII.forgeborn.core.model.ShopTier;
import ca.ACOII.forgeborn.core.model.Skill;

import java.util.List;
import java.util.Objects;

public class Definitions {
    public List<Skill> skillList;
    public List<Frame> frameList;
    public List<Part> partList;
    public List<ShopTier> shopTierList;
    public Definitions() {
        skillList = DefinitionLoader.loadSkills();
        frameList = DefinitionLoader.loadFrames();
        shopTierList = DefinitionLoader.loadShopTiers();
        partList = DefinitionLoader.loadParts();
    }

    public Frame findFrame(String frameId) {
        for (Frame f : frameList) {
            if (Objects.equals(f.id(), frameId)) return f;
        }
        return null;
    }
    public Skill findSkill(String skillId) {
        for (Skill s : skillList) {
            if (Objects.equals(s.id(), skillId)) return s;
        }
        return null;
    }
    public Part findPart(String partId) {
        for (Part p : partList) {
            if (Objects.equals(p.id(), partId)) return p;
        }
        return null;
    }
    public ShopTier findShopTier(String shopTierId) {
        for (ShopTier tier : shopTierList) {
            if (Objects.equals(tier.id(), shopTierId)) return tier;
        }
        return null;
    }
}
