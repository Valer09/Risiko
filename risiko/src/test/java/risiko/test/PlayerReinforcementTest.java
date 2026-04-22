package risiko.test;

import org.junit.jupiter.api.Test;
import risiko.data.TerritoryDefinition;
import risiko.model.*;
import risiko.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerReinforcementTest
{
    @Test
    void getAllCombinationTest()
    {
        GameState.createNewState(false);
        Player player = new Player("Miriana", ArmyColor.Green);

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));
        player.addCardAndItsTerritory(new TerritoryCard("C", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));

        GameState.getInstance().setPlayers(new ArrayList<>(List.of(player)));
        var playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.getAllCombinations().size() == 1;
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Cannon.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Cannon.getId()).toList().getFirst().getPower() == CombinationType.Cannon.getPower();

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Infantry));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(),0), CardSymbol.Infantry));
        player.addCardAndItsTerritory(new TerritoryCard("C", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Infantry));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.getAllCombinations().size() == 2;
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Infantry.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Infantry.getId()).toList().getFirst().getPower() == CombinationType.Infantry.getPower();

        //RESET
        player.cleanTerritoryCards();

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));
        player.addCardAndItsTerritory(new TerritoryCard("C", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.getAllCombinations().size() == 1;
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Knight.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Knight.getId()).toList().getFirst().getPower() == CombinationType.Knight.getPower();

        //RESET
        player.cleanTerritoryCards();

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));
        player.addCardAndItsTerritory(new TerritoryCard("C", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Infantry));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.getAllCombinations().size() == 1;
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Different.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Different.getId()).toList().getFirst().getPower() == CombinationType.Different.getPower();

        //RESET
        player.cleanTerritoryCards();

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.All));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));
        player.addCardAndItsTerritory(new TerritoryCard("C", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Infantry));
        player.addCardAndItsTerritory(new TerritoryCard("D", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.getAllCombinations().size() == 4;
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Jolly.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Jolly.getId()).toList().getFirst().getPower() == CombinationType.Jolly.getPower();
        assert playerReinf.getAllCombinations().stream().anyMatch(el -> el.getCombinationType().getId() == CombinationType.Different.getId());
        assert playerReinf.getAllCombinations().stream().filter(el -> el.getCombinationType().getId() == CombinationType.Different.getId()).toList().getFirst().getPower() == CombinationType.Different.getPower();
    }

    @Test
    void playerReinforcement()
    {
        Player player = new Player("Miriana", ArmyColor.Green);
        GameState.createNewState(false);
        GameState.getInstance().setPlayers(new ArrayList<>(List.of(player)));

        addNotCombinationCards(player);

        var playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert !playerReinf.hasCardCombinations();

        player.cleanTerritoryCards();
        addNotCombinationCards(player);
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(),0), CardSymbol.Knight));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.hasCardCombinations();

        player.cleanTerritoryCards();
        addNotCombinationCards(player);
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.hasCardCombinations();

        player.cleanTerritoryCards();
        addNotCombinationCards(player);
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Knight));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.hasCardCombinations();

        player.cleanTerritoryCards();
        addNotCombinationCards(player);
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.All));

        playerReinf = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());

        assert playerReinf.hasCardCombinations();
    }



    private static void addNotCombinationCards(Player player)
    {
        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(), 0), CardSymbol.Cannon));

        player.addCardAndItsTerritory(new TerritoryCard("A", new Territory(getRandomTerritoryDefinition(),0), CardSymbol.Knight));
        player.addCardAndItsTerritory(new TerritoryCard("B", new Territory(getRandomTerritoryDefinition(),0), CardSymbol.Knight));
    }

    private static TerritoryDefinition getRandomTerritoryDefinition()
    {

        return TerritoryDefinition.fromId(new Random().nextInt(TerritoryDefinition.getTerritoriesCount() - 1) + 1);
    }
}
