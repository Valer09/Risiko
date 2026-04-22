package risiko.model;

import java.util.List;
import java.util.Optional;

public class PlayerGoalCheck
{
    public static boolean check(Player player)
    {
        List<Territory> territories = player.getTerritories();
        GameState gameState = GameState.getInstance();
        List<Player> players = gameState.getPlayers();
        List<Continent> continentList = player.getOwnedContinents();

        switch (player.getGoal().getId())
        {
            case 1: return countTerritoriesWithTwoArmiesAtLeast(territories) >=  18;
            case 2: return territories.size() >= 24;
            case 3: return continentList.contains(Continent.North_America) && continentList.contains(Continent.Africa);
            case 4: return continentList.contains(Continent.North_America) && continentList.contains(Continent.Oceania);
            case 5: return continentList.contains(Continent.Asia) && continentList.contains(Continent.South_America);
            case 6: return continentList.contains(Continent.Asia) && continentList.contains(Continent.Africa);
            case 7: return continentList.contains(Continent.Europe) && continentList.contains(Continent.South_America);
            case 8: return continentList.contains(Continent.Europe) && continentList.contains(Continent.Oceania);
            case 9:
                if(player.getArmyColor().equals(ArmyColor.Blue)) return check24Territories(territories);

                Optional<Player> bluePlayer = players.stream()
                        .filter(el -> el.getArmyColor().equals(ArmyColor.Blue))
                        .findFirst();

                return bluePlayer.map(value -> value.getArmiesCount() == 0).orElseGet(() -> check24Territories(territories));
            case 99: return false;//check will be done in the attack step
            default:throw new RuntimeException("Goal not defined");
        }
    }

    private static int countTerritoriesWithTwoArmiesAtLeast(List<Territory> territories)
    {
        int count = 0;
        for(Territory territory : territories)
            if (territory.getArmiesCount() >= 2) count++;
        return count;
    }

    private static boolean check24Territories(List<Territory> territories)
    {
        return territories.size() >= 24;
    }
}
