package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dice
{
    @JsonProperty("color")
    ArmyColor color;

    @JsonCreator
    public Dice(@JsonProperty("color") ArmyColor color)
    {
        this.color = color;
    }

    public int roll()
    {
        return (int)(Math.random() * 6) + 1;
    }
}
