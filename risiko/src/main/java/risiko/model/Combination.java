package risiko.model;

public class Combination
{
    private final Card[] combination;
    private final int power;
    private final CombinationType combinationType;

    public Combination(Card[] combination)
    {
        if(!CombinationTypeCalculation.isCombination(combination)) throw new IllegalArgumentException("Il set di carte non rappresenta una combinazione valida");
        this.combination = combination;
        this.combinationType = CombinationTypeCalculation.calculateCombinationType(combination);
        this.power = CombinationTypeCalculation.calculateCombinationType(combination).getPower();
    }

    public Card[] getCombination()
    {
        return combination;
    }

    public int getPower()
    {
        return power;
    }

    public CombinationType getCombinationType()
    {
        return combinationType;
    }
}
