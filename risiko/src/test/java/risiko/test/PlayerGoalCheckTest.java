package risiko.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import risiko.data.GoalData;
import risiko.data.TerritoryDefinition;
import risiko.model.*;
import risiko.model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerGoalCheckTest
{
    @BeforeEach
    void initialize()
    {
        GameState.cleanGameState();
        GameState.createNewState(false);
    }

    @Test
    void check18TerritoriesTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().getFirst());

        for(int i = 1; i <= 17; i++) addTerritoryCard(i, player);

        for(Territory territory : player.getTerritories()) player.addArmiesToTerritory(2, territory);

        assert !PlayerGoalCheck.check(player);

        var td = TerritoryDefinition.fromId(18);

        TerritoryCard territoryCard = new TerritoryCard("a", new Territory(td, 0), CardSymbol.Cannon);
        player.addCardAndItsTerritory(territoryCard);
        player.addArmiesToTerritory(2, territoryCard.getTerritory());

        assert PlayerGoalCheck.check(player);

    }

    @Test
    void check24TerritoriesTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(1));

        for(int i = 1; i <= 23; i++) addTerritoryCard(i, player);

        assert !PlayerGoalCheck.check(player);

        var td = TerritoryDefinition.fromId(24);

        TerritoryCard territoryCard = new TerritoryCard("a", new Territory(td, 0), CardSymbol.Cannon);
        player.addCardAndItsTerritory(territoryCard);

        assert PlayerGoalCheck.check(player);

    }

    @Test
    void checkNAandAfricaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(2));

        var NAterritories = getContinentTerritories(Continent.North_America);
        var africaTerritories = getContinentTerritories(Continent.Africa);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }

    @Test
    void checkNAandOceaniaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(3));

        var NAterritories = getContinentTerritories(Continent.North_America);
        var africaTerritories = getContinentTerritories(Continent.Oceania);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }

    @Test
    void checkAsiaAndSouthAmericaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(4));

        var NAterritories = getContinentTerritories(Continent.Asia);
        var africaTerritories = getContinentTerritories(Continent.South_America);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }

    @Test
    void checkAsiaAndAfricaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(5));

        var NAterritories = getContinentTerritories(Continent.Asia);
        var africaTerritories = getContinentTerritories(Continent.Africa);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }

    @Test
    void checkEuropeAndSouthAmericaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(6));

        var NAterritories = getContinentTerritories(Continent.Europe);
        var africaTerritories = getContinentTerritories(Continent.South_America);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }

    @Test
    void checkEuropeAndOceaniaContinentsTest()
    {
        Player player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(7));

        var NAterritories = getContinentTerritories(Continent.Europe);
        var africaTerritories = getContinentTerritories(Continent.Oceania);

        for(Territory territory : NAterritories) addTerritoryCard(territory.getId(), player);
        for(int i = 0; i < africaTerritories.size() - 1; i++) addTerritoryCard(africaTerritories.get(i).getId(), player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(africaTerritories.getLast().getId(), player);

        assert PlayerGoalCheck.check(player);
    }


    @Test
    void checkBlueArmiesTest()
    {
        //CASO: Non sono il blu e il blu gioca quindi devo distruggere il blu

        Player player = new Player("GreenPlayer", ArmyColor.Green);

        var playerBlue = new Player("BluePlayer", ArmyColor.Blue);

        GameState.cleanGameState();
        GameState.createNewState(false);

        GameState.getInstance().setPlayers(Arrays.asList(player, playerBlue));


        player.setGoal(GoalData.getGoals().get(8));
        playerBlue.setGoal(GoalData.getGoals().get(1));

        //aggiungi un territorio al player blu: non ho ancora vinto
        var addedTerrCard = addTerritoryCard(1,playerBlue);
        playerBlue.addArmiesToTerritory(1, addedTerrCard.getTerritory());

        assert !PlayerGoalCheck.check(player);

        //appena dopo aver eseguito una mossa, il player blu non ha piu' armate: ho vinto
        playerBlue.cleanArmies();

        assert PlayerGoalCheck.check(player);



        //CASO: Sono il blu quindi devo fare 24 territori

        GameState.cleanGameState();
        GameState.createNewState(false);

        player = new Player("BluePlayer", ArmyColor.Blue);
        player.setGoal(GoalData.getGoals().get(8));

        GameState.getInstance().setPlayers(List.of(player));

        for(int i = 1; i <= 23; i++) addTerritoryCard(i, player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(24, player);

        assert PlayerGoalCheck.check(player);

        //CASO: Non sono il blu ma il blu non gioca, quindi devo fare 24 territori
        player = new Player("BluePlayer", ArmyColor.Blue);
        var playerNotBlue = new Player("YellowPlayer", ArmyColor.Yellow);

        GameState.cleanGameState();
        GameState.createNewState(false);

        GameState.getInstance().setPlayers(Arrays.asList(player, playerNotBlue));

        player.setGoal(GoalData.getGoals().get(8));
        playerNotBlue.setGoal(GoalData.getGoals().get(1));

        for(int i = 1; i <= 23; i++) addTerritoryCard(i, player);

        assert !PlayerGoalCheck.check(player);

        addTerritoryCard(24, player);

        assert PlayerGoalCheck.check(player);

    }

    private static TerritoryCard addTerritoryCard(int i, Player player)
    {
        var td = TerritoryDefinition.fromId(i);
        TerritoryCard territoryCard = new TerritoryCard("a", new Territory(td, 0), CardSymbol.Cannon);
        player.addCardAndItsTerritory(territoryCard);
        return territoryCard;
    }

    private List<Territory> getContinentTerritories(Continent continent)
    {
        List<Territory> continentTerritories = new ArrayList<>();
        switch (continent) {
            case North_America:
                continentTerritories.add(new Territory(TerritoryDefinition.ALASKA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.ALBERTA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.AMERICA_CENTRALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.STATI_UNITI_OCCIDENTALI, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.STATI_UNITI_ORIENTALI, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.GROENLANDIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.TERRITORI_DEL_NORD_OVEST, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.ONTARIO, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.QUEBEC, 0));
                return continentTerritories;
            case South_America:
                continentTerritories.add(new Territory(TerritoryDefinition.ARGENTINA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.BRASILE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.PERU, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.VENEZUELA, 0));
                return continentTerritories;
            case Africa:
                continentTerritories.add(new Territory(TerritoryDefinition.CONGO, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.AFRICA_ORIENTALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.EGITTO, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.MADAGASCAR, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.NORDAFRICA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.AFRICA_DEL_SUD, 0));
                return continentTerritories;
            case Oceania:
                continentTerritories.add(new Territory(TerritoryDefinition.AUSTRALIA_ORIENTALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.AUSTRALIA_OCCIDENTALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.INDONESIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.NUOVA_GUINEA, 0));
                return continentTerritories;
            case Asia:
                continentTerritories.add(new Territory(TerritoryDefinition.AFGHANISTAN, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.CINA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.INDIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.GIAPPONE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.KAMCHATKA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.MEDIO_ORIENTE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.MONGOLIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.SIAM, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.SIBERIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.URALI, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.JACUZIA, 0));
                return continentTerritories;
            case Europe:
                continentTerritories.add(new Territory(TerritoryDefinition.GRAN_BRETAGNA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.ISLANDA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.EUROPA_SETTENTRIONALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.SCANDINAVIA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.EUROPA_MERIDIONALE, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.UCRAINA, 0));
                continentTerritories.add(new Territory(TerritoryDefinition.EUROPA_OCCIDENTALE, 0));
                return continentTerritories;
            default: return new ArrayList<>();
        }
    }
}
