package ca.ACOII.forgeborn.core.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "kind"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StatPrereq.class, name = "stat"),
        @JsonSubTypes.Type(value = SkillPrereq.class, name = "skill"),
        @JsonSubTypes.Type(value = PartPrereq.class, name = "part")
})

public sealed interface Prerequisite permits StatPrereq, SkillPrereq, PartPrereq {
}
