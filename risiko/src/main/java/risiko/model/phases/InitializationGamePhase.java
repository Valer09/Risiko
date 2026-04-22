package risiko.model.phases;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.model.phases.steps.ArmiesPositioningStep;
import risiko.model.phases.steps.CardDistributionStep;
import risiko.model.phases.steps.GameStep;

import java.util.Arrays;
import java.util.List;

public class InitializationGamePhase implements GamePhase
{
    @JsonProperty("gameSteps")
    private List<GameStep> gameSteps;
    @JsonProperty("currentStepIndex")
    private int currentStepIndex;

    /**
     * JSON constructor
     * */
    @JsonCreator
    public InitializationGamePhase(@JsonProperty("gameSteps") List<GameStep> gameSteps, @JsonProperty("currentStepIndex") int currentStepIndex)
    {
        this.gameSteps = gameSteps;
        this.currentStepIndex = currentStepIndex;
    }

    public InitializationGamePhase()
    {
        this.gameSteps = getMySteps();
        this.currentStepIndex = 0;
    }

    @Override
    public void execute()
    {
        while(currentStepIndex < gameSteps.size())
        {
            while(!gameSteps.get(currentStepIndex).isCompleted()) gameSteps.get(currentStepIndex).run();
            currentStepIndex++;
        }
    }

    @Override
    public boolean isCompleted()
    {
        for(GameStep gameStep : gameSteps) if(!gameStep.isCompleted()) return false;
        return true;
    }

    @JsonIgnore
    private static List<GameStep> getMySteps()
    {
        return Arrays.asList(new CardDistributionStep(), new ArmiesPositioningStep());
    }
}
