package risiko.model;

import java.util.List;

public class PlayerReinforcementCalculation
{
    private int total;

    public PlayerReinforcementCalculation()
    {
        total = 0;
    }

    public void addBasicArmies(int count)
    {
        total += count;
    }

    public int addContinentsAndGetContinentsValue(List<Continent> continents)
    {
        int sum = continents.stream().mapToInt(Continent::getArmiesValue).sum();
        total += sum;
        return sum;
    }

    public int sumCombinationAndGetValue(Combination selectedCombination)
    {
        total += selectedCombination.getPower();
        return selectedCombination.getPower();
    }

    public int getTotal()
    {
        return total;
    }
}
