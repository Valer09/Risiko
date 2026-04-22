package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReinforcementState
{
    @JsonProperty("player")
    private Player player;
    @JsonProperty("reinforcementToPlace")
    private int reinforcementToPlace;
    @JsonProperty("completed")
    private boolean completed;

    @JsonCreator
    public ReinforcementState(@JsonProperty("player")Player player, @JsonProperty("reinforcementToPlace")int reinforcementToPlace,   @JsonProperty("completed") boolean completed)
    {
        this.player = player;
        this.reinforcementToPlace = reinforcementToPlace;
        this.completed = completed;
    }

    public ReinforcementState(Player player)
    {
        this.player = player;
        this.reinforcementToPlace = -1;
        this.completed = false;
    }

    @JsonIgnore
    public Player getPlayer()
    {
        return player;
    }

    @JsonIgnore
    public void reset()
    {
        completed = false;
        this.reinforcementToPlace = -1;
    }

    @JsonIgnore
    public boolean isCompleted()
    {
        return completed;
    }
    @JsonIgnore
    public void complete()
    {
        completed = true;
    }

    @JsonIgnore
    public int getReinforcementToPlace()
    {
        return reinforcementToPlace;
    }

    @JsonIgnore
    public void setReinforcementToPlace(int reinforcementToPlace)
    {
        this.reinforcementToPlace = reinforcementToPlace;
    }
}
