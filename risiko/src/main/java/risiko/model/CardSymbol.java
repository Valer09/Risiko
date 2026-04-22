package risiko.model;

public enum CardSymbol
{
    Knight (1, "Cavaliere"),
    Infantry(2, "Fante"),
    Cannon(3, "Cannone"),
    All(4, "All");


    private final int id;
    private final String name;

    CardSymbol(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public static CardSymbol fromId(int id)
    {
        for (CardSymbol symbol : values()) if (symbol.getId() == id) return symbol;
        throw new IllegalArgumentException("ID non valido: " + id);
    }

    public static CardSymbol fromName(String name)
    {
        for (CardSymbol symbol : values()) if (symbol.getName().equalsIgnoreCase(name)) return symbol;
        throw new IllegalArgumentException("Nome non valido: " + name);
    }
}
