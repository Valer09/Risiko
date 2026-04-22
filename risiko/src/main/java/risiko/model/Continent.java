package risiko.model;

public enum Continent
{
    North_America(5, "Nord America"), South_America(2, "Sud America"), Africa(3,"Africa"), Oceania(2, "Oceania"), Asia(7, "Asia"), Europe(5, "Europa");

    private final int armiesValue;
    private final String name;

    Continent(int armiesValue, String name)
    {
        this.armiesValue = armiesValue;
        this.name = name;
    }

    public int getArmiesValue()
    {
        return armiesValue;
    }

    public String getName()
    {
        return name;
    }
}
