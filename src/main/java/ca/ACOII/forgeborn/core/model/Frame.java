package ca.ACOII.forgeborn.core.model;

import java.util.List;
import java.util.Map;

public record Frame(String id, String name, Map<String, Integer> statVals, List<String> slotTypes) {

}
