package risiko.model;

import java.util.Arrays;

public class CombinationTypeCalculation
{
    public static boolean isCombination(Card[] combination)
    {
        if(combination.length != 3) return false;
        return calculateCombinationType(combination) != CombinationType.Null;
    }

    public static CombinationType calculateCombinationType(Card[] combination)
    {
        if(Arrays.stream(combination).allMatch(el -> el.getCardSymbol() == CardSymbol.Cannon)) return CombinationType.Cannon;
        if(Arrays.stream(combination).allMatch(el -> el.getCardSymbol() == CardSymbol.Infantry)) return CombinationType.Infantry;
        if(Arrays.stream(combination).allMatch(el -> el.getCardSymbol() == CardSymbol.Knight)) return CombinationType.Knight;
        if(!combination[0].getCardSymbol().equals(CardSymbol.All) &&
                !combination[1].getCardSymbol().equals(CardSymbol.All) &&
                !combination[2].getCardSymbol().equals(CardSymbol.All) &&
                combination[0].getCardSymbol().getId() != combination[1].getCardSymbol().getId() &&
                combination[0].getCardSymbol().getId() != combination[2].getCardSymbol().getId() &&
                combination[1].getCardSymbol().getId() != combination[2].getCardSymbol().getId())
            return CombinationType.Different;

        int jollycount=0;

        for (Card card : combination)
            if (card.getCardSymbol() == CardSymbol.All) jollycount++;

        if(jollycount != 1) return CombinationType.Null;
        else return CombinationType.Jolly;
    }
}
