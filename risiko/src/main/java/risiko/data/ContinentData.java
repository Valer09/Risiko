package risiko.data;

import risiko.model.Continent;
import risiko.model.Territory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContinentData
{
    public static List<Continent> getContinentsFromTerritories(List<Territory> territories)
    {
        List<Continent> continents = new ArrayList<>();
        if(territories.size() < 4) return continents;
        for(Continent continent : Continent.values())
            if (makes(continent, territories)) continents.add(continent);
        return continents;
    }

    private static boolean makes(Continent continent, List<Territory> territories)
    {
        switch(continent){
            case North_America ->
            {
                return
                        territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.ALASKA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.ALBERTA.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AMERICA_CENTRALE.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.STATI_UNITI_OCCIDENTALI.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.STATI_UNITI_ORIENTALI.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.GROENLANDIA.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.TERRITORI_DEL_NORD_OVEST.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.ONTARIO.getName()))
                                && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.QUEBEC.getName()));
            }
            case South_America ->
            {
                return territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.ARGENTINA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.BRASILE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.PERU.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.VENEZUELA.getName()));
            }
            case Africa ->
            {
                return territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.CONGO.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AFRICA_ORIENTALE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.EGITTO.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.MADAGASCAR.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.NORDAFRICA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AFRICA_DEL_SUD.getName()));
            }
            case Oceania ->
            {
                return territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AUSTRALIA_ORIENTALE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AUSTRALIA_OCCIDENTALE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.INDONESIA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.NUOVA_GUINEA.getName()));
            }
            case Asia -> {
                return territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.AFGHANISTAN.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.CINA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.INDIA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.GIAPPONE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.KAMCHATKA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.MEDIO_ORIENTE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.MONGOLIA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.SIAM.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.SIBERIA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.URALI.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.JACUZIA.getName()));
            }
            case Europe -> {
                return territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.GRAN_BRETAGNA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.ISLANDA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.EUROPA_SETTENTRIONALE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.SCANDINAVIA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.EUROPA_MERIDIONALE.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.UCRAINA.getName()))
                        && territories.stream().anyMatch(el -> Objects.equals(el.getName(), TerritoryDefinition.EUROPA_OCCIDENTALE.getName()));
            }
        }
        return false;
    }

}
