package risiko.data;

import java.util.*;

public enum TerritoryDefinition
{
    ALASKA(1, "Alaska", new HashSet<>(Arrays.asList(2,4,30))),
    TERRITORI_DEL_NORD_OVEST(2, "Territori del Nord-Ovest", new HashSet<>(Arrays.asList(3,4,1,5))),
    GROENLANDIA(3, "Groenlandia", new HashSet<>(Arrays.asList(2,5,6,14))),
    ALBERTA(4, "Alberta", new HashSet<>(Arrays.asList(1,2,5,7))),
    ONTARIO(5, "Ontario", new HashSet<>(Arrays.asList(2,3,4,6,7,8))),
    QUEBEC(6, "Quebec", new HashSet<>(Arrays.asList(3,5,8))),
    STATI_UNITI_OCCIDENTALI(7, "Stati Uniti Occidentali", new HashSet<>(Arrays.asList(4,5,8,9))),
    STATI_UNITI_ORIENTALI(8, "Stati Uniti Orientali", new HashSet<>(Arrays.asList(5,6,7,9))),
    AMERICA_CENTRALE(9, "America Centrale", new HashSet<>(Arrays.asList(5,6,7,9))),
    VENEZUELA(10, "Venezuela", new HashSet<>(Arrays.asList(9, 11, 12))),
    BRASILE(11, "Brasile", new HashSet<>(Arrays.asList(10, 12, 13, 21))),
    PERU(12, "Perù", new HashSet<>(Arrays.asList(10, 11, 13))),
    ARGENTINA(13, "Argentina", new HashSet<>(Arrays.asList(11, 12))),
    ISLANDA(14, "Islanda", new HashSet<>(Arrays.asList(3, 15, 16))),
    SCANDINAVIA(15, "Scandinavia", new HashSet<>(Arrays.asList(14, 16, 18, 19))),
    GRAN_BRETAGNA(16, "Gran Bretagna", new HashSet<>(Arrays.asList(14, 15, 17, 18))),
    EUROPA_OCCIDENTALE(17, "Europa Occidentale", new HashSet<>(Arrays.asList(16, 18, 20, 21))),
    EUROPA_SETTENTRIONALE(18, "Europa Settentrionale", new HashSet<>(Arrays.asList(15, 16, 17, 19, 20))),
    UCRAINA(19, "Ucraina", new HashSet<>(Arrays.asList(15, 18, 20, 27, 34, 37))),
    EUROPA_MERIDIONALE(20, "Europa Meridionale", new HashSet<>(Arrays.asList(17, 18, 19, 21, 22, 34))),
    NORDAFRICA(21, "Nordafrica", new HashSet<>(Arrays.asList(11, 17, 20, 22, 23, 24))),
    EGITTO(22, "Egitto", new HashSet<>(Arrays.asList(20, 21, 24, 34))),
    CONGO(23, "Congo", new HashSet<>(Arrays.asList(21, 24, 25))),
    AFRICA_ORIENTALE(24, "Africa Orientale", new HashSet<>(Arrays.asList(21, 22, 23, 25, 26))),
    AFRICA_DEL_SUD(25, "Africa del Sud", new HashSet<>(Arrays.asList(23, 24, 26))),
    MADAGASCAR(26, "Madagascar", new HashSet<>(Arrays.asList(24, 25))),
    URALI(27, "Urali", new HashSet<>(Arrays.asList(19, 28, 33, 37))),
    SIBERIA(28, "Siberia", new HashSet<>(Arrays.asList(27, 29, 31, 32, 33))),
    JACUZIA(29, "Jacuzia", new HashSet<>(Arrays.asList(28, 30, 31))),
    KAMCHATKA(30, "Kamchatka", new HashSet<>(Arrays.asList(1, 29, 31, 32, 38))),
    IRKUTSK(31, "Irkutsk", new HashSet<>(Arrays.asList(28, 29, 30, 32))),
    MONGOLIA(32, "Mongolia", new HashSet<>(Arrays.asList(28, 30, 31, 33, 38))),
    CINA(33, "Cina", new HashSet<>(Arrays.asList(27, 28, 32, 34, 35, 36, 37))),
    MEDIO_ORIENTE(34, "Medio Oriente", new HashSet<>(Arrays.asList(19, 20, 22, 33, 35, 37))),
    INDIA(35, "India", new HashSet<>(Arrays.asList(33, 34, 36))),
    SIAM(36, "Siam", new HashSet<>(Arrays.asList(33, 35, 39))),
    AFGHANISTAN(37, "Afghanistan", new HashSet<>(Arrays.asList(19, 27, 33, 34))),
    GIAPPONE(38, "Giappone", new HashSet<>(Arrays.asList(30, 32))),
    INDONESIA(39, "Indonesia", new HashSet<>(Arrays.asList(36, 40, 41))),
    NUOVA_GUINEA(40, "Nuova Guinea", new HashSet<>(Arrays.asList(39, 41, 42))),
    AUSTRALIA_OCCIDENTALE(41, "Australia Occidentale", new HashSet<>(Arrays.asList(39, 40, 42))),
    AUSTRALIA_ORIENTALE(42, "Australia Orientale", new HashSet<>(Arrays.asList(40, 41)));


    private final int id;
    private final String name;
    private final Set<Integer> borderingTerritories;

    TerritoryDefinition(int id, String name, Set<Integer> borderingTerritories)
    {
        this.id = id;
        this.name = name;
        this.borderingTerritories = borderingTerritories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TerritoryDefinition fromId(int id)
    {
        for (TerritoryDefinition territorio : values())
            if (territorio.getId() == id) return territorio;
        throw new IllegalArgumentException("ID non valido: " + id);
    }

    public static TerritoryDefinition fromName(String name)
    {
        for (TerritoryDefinition territoryDefinition : values())
            if (territoryDefinition.getName().equalsIgnoreCase(name)) return territoryDefinition;
        throw new IllegalArgumentException("Nome non valido: " + name);
    }

    public static int getTerritoriesCount()
    {
        return values().length;
    }

    public static List<TerritoryDefinition> getBorderingTerritoriesById(int territoryId)
    {
        List<TerritoryDefinition> borderingTerritories = new ArrayList<>();
        for(int id : fromId(territoryId).borderingTerritories) borderingTerritories.add(fromId(id));
        return borderingTerritories;
    }
}
