package ca.ACOII.forgeborn.core.model;

import java.util.Map;

public record Part(String id, String name, Map<String, Integer> statVals, String slotType, int price) {
}
