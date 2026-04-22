package risiko.model;

import risiko.data.TerritoryDefinition;

import java.util.*;
import java.util.stream.Collectors;

public class BorderTerritoriesCalculation
{
    public static List<Territory> calculateValidBorderForMovingArmiesFrom(Player player)
    {
        Set<Integer> playerTerritoryIds = collectPlayerTerritoriesIds(player);

        return player.getTerritories().stream()
                .filter(el -> el.getArmiesCount() > 1)
                .filter(territory ->
                        TerritoryDefinition.getBorderingTerritoriesById(territory.getId())
                                .stream()
                                .anyMatch(border -> playerTerritoryIds.contains(border.getId())))
                .toList();
    }

    public static List<TerritoryDefinition> calculateValidBorderForMovingArmiesTo(Player player, Territory from)
    {
        Set<Integer> playerTerritoryIds = collectPlayerTerritoriesIds(player);
        return TerritoryDefinition.getBorderingTerritoriesById(from.getId()).stream().filter(territory -> playerTerritoryIds.contains(territory.getId())).toList();
    }

    public static List<Territory> calculateValidAttackerTerritories(Player player)
    {

        Set<Integer> playerTerritoryIds = collectPlayerTerritoriesIds(player);

        // Ritorno l'elenco dei territori del giocatore, i cui confini sono territori NON appartenenti al giocatore che esegue l'attacco
        return player.getTerritories().stream()
                .filter(el -> el.getArmiesCount() > 1)
                .filter(territory ->
                        TerritoryDefinition.getBorderingTerritoriesById(territory.getId())
                                .stream()
                                .anyMatch(border -> !playerTerritoryIds.contains(border.getId())))
                .toList();
    }

    public static List<TerritoryDefinition> calculateValidTerritoriesToAttack(Player player, Territory from)
    {
        Set<Integer> playerTerritoryIds = collectPlayerTerritoriesIds(player);

        // Ritorno l'elenco dei territori confinanti con il territorio fornito ma che non sono in possesso dal giocatore che esegue l'attacco
        return TerritoryDefinition.getBorderingTerritoriesById(from.getId()).stream().filter(territory -> !playerTerritoryIds.contains(territory.getId())).toList();
    }

    private static Set<Integer> collectPlayerTerritoriesIds(Player player) {
        return player.getTerritories().stream()
                .map(Territory::getId)
                .collect(Collectors.toSet());
    }
}
