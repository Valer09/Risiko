package risiko.model.phases;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.model.GameState;
import risiko.model.phases.steps.*;
import risiko.model.phases.steps.*;

import java.util.Arrays;
import java.util.List;

public class CoreGamePhase implements GamePhase
{
    @JsonProperty("gameSteps")
    private List<GameStep> gameSteps;
    @JsonProperty("currentStepIndex")
    private int currentStepIndex;

    /**
     * JSON constructor
     * */
    @JsonCreator
    public CoreGamePhase(@JsonProperty("gameSteps") List<GameStep> gameSteps, @JsonProperty("currentStepIndex") int currentStepIndex)
    {
        this.gameSteps = gameSteps;
        this.currentStepIndex = currentStepIndex;
    }

    public CoreGamePhase()
    {
        this.gameSteps = getMySteps();
        this.currentStepIndex = 0;
    }

    @Override
    public void execute()
    {
        while(currentStepIndex < gameSteps.size() && !GameState.getInstance().isWon())
        {
            while(!gameSteps.get(currentStepIndex).isCompleted())
                gameSteps.get(currentStepIndex).run();
            currentStepIndex++;

            if(gameSteps.stream().allMatch(GameStep::isCompleted))
            {
                GameState.getInstance().resetReinforcementPerPlayer();
                GameState.getInstance().nextPlayer();
                ((ResettableStep)(gameSteps.get(1))).reset();
                GameState.getInstance().resetReinforcementPerPlayer();
                ((ResettableStep)(gameSteps.get(0))).reset();
                ((ResettableStep)(gameSteps.get(2))).reset();
            }
            if(currentStepIndex % gameSteps.size() == 0 )
            {
                currentStepIndex = 0;
                ((ResettableStep)(gameSteps.get(currentStepIndex))).reset();
            }

        }

        System.exit(0);
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
        return Arrays.asList(new ReinforcementStep(), new AttackStep(), new ArmiesMoveStep());
    }
}
