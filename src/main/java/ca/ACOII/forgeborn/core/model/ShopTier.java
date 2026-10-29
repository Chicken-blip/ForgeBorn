package ca.ACOII.forgeborn.core.model;

import java.util.List;

public record ShopTier(String id, int requiredFame, List<String> tierPartsIds) {
}
