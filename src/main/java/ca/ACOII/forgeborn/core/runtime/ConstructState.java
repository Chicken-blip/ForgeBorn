package ca.ACOII.forgeborn.core.runtime;

import ca.ACOII.forgeborn.core.model.Part;

import java.util.List;
import java.util.Map;

public record ConstructState(List<String> learnedSkillIds, List<Part> fittedParts, Map<String, Integer> stats) {
}
