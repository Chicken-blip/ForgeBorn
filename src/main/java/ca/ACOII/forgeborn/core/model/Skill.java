package ca.ACOII.forgeborn.core.model;

import java.util.List;

public record Skill(String id, String name, List<Prerequisite> prereqs, int energyCost, int damage, boolean isBasic) {
}
