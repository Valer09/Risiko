package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerGoal
{
    @JsonProperty("id")
    private int id;
    @JsonProperty("description")
    private String description;

    @JsonCreator
    public PlayerGoal(@JsonProperty("id") int id, @JsonProperty("description") String description)
    {
        this.id = id;
        this.description = description;
    }

    @JsonIgnore
    public String getDescription()
    {
        return description;
    }

    @JsonIgnore
    public int getId()
    {
        return id;
    }
}
