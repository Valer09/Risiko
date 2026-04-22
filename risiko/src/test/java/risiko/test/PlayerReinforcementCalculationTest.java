package risiko.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import risiko.data.TerritoryDefinition;
import risiko.model.*;
import risiko.model.*;

import java.util.Arrays;

public class PlayerReinforcementCalculationTest
{

    @BeforeEach
    void initialize()
    {
        GameState.cleanGameState();
        GameState.createNewState(false);
    }

    @Test
    void getTotalCalculationTest()
    {
        Player player = new Player("Miriana", ArmyColor.Red);

        var allTerritories = TerritoryDefinition.getTerritoriesCount();
        for(int i = 1; i <= allTerritories; i++)
            player.addCardAndItsTerritory(new TerritoryCard("Terr", new Territory(TerritoryDefinition.fromId(i), 10), CardSymbol.All));

        PlayerReinforcementCalculation calculator = new PlayerReinforcementCalculation();

        var additionalContinentArmiesCount = Continent.values();

        var basicReinforce = allTerritories / 3; // 43/3

        calculator.addBasicArmies(basicReinforce);

        var continentReinforce = calculator.addContinentsAndGetContinentsValue(Arrays.stream(additionalContinentArmiesCount).toList());

        assert continentReinforce == 24;

        var cardCombinationValue = calculator.sumCombinationAndGetValue(new Combination(new Card[]
                {
                        new TerritoryCard("",null, CardSymbol.Cannon),
                        new TerritoryCard("",null, CardSymbol.Cannon),
                        new TerritoryCard("",null, CardSymbol.Cannon)
                }));
        assert cardCombinationValue == 4;

        assert calculator.getTotal() == 42;
    }
}
