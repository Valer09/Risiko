package risiko.model.phases.steps;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArmiesPositioningStep.class, name = "ArmiesPositioningStep"),
        @JsonSubTypes.Type(value = CardDistributionStep.class, name = "CardDistributionStep"),
        @JsonSubTypes.Type(value = ReinforcementStep.class, name = "ReinforcementStep"),
        @JsonSubTypes.Type(value = AttackStep.class, name = "AttackStep"),
        @JsonSubTypes.Type(value = ArmiesMoveStep.class, name = "ArmiesMoveStep"),
})

public interface GameStep
{
    void run();
    boolean isCompleted();
}
