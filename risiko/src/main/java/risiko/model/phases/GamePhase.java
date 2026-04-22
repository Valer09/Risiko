package risiko.model.phases;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InitializationGamePhase.class, name = "InitializationGamePhase"),
        @JsonSubTypes.Type(value = CoreGamePhase.class, name = "CoreGamePhase")
})

public interface GamePhase
{
    void execute();
    boolean isCompleted();
}
