package risiko.model;

public enum CombinationType
{
    Cannon(1,"Tre cannoni", 4),
    Infantry(2,"Tre fanti", 6),
    Knight(3, "Tre fanti", 8),
    Different(4, "Tre diversi", 10),
    Jolly(5, "Due uguali ed un Jolly", 12),
    Null(0, "", 0);

    private final String description;
    private final int power;
    private final int id;

    CombinationType(int id, String description, int power)
    {
        this.id = id;
        this.description = description;
        this.power = power;
    }

    public String getDescription()
    {
        return description;
    }

    public int getPower() {
        return power;
    }

    public int getId() {
        return id;
    }
}
