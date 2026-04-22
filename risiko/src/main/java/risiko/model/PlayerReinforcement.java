package risiko.model;

import org.apache.commons.math3.util.CombinatoricsUtils;
import risiko.data.ContinentData;

import java.util.ArrayList;
import java.util.List;

public class PlayerReinforcement
{
    private final Player player;
    private int basicReinforce;
    private List<Combination> cardCombinations;

    public PlayerReinforcement(Player player)
    {
        this.player = player;
        load();
    }

    public int getBasicReinforce()
    {
        return basicReinforce;
    }

    public List<Continent> getAdditionalContinentArmiesCount()
    {
        return ContinentData.getContinentsFromTerritories(player.getTerritories());
    }

    public Player getPlayer()
    {
        return player;
    }

    public boolean hasCardCombinations()
    {
        return !cardCombinations.isEmpty();
    }

    public List<Combination> getAllCombinations()
    {
        return cardCombinations;
    }

    public void load()
    {
        basicReinforce = calculateBasicReinforce();
        cardCombinations = calculateCardCombinations(player);
    }

    private int calculateBasicReinforce()
    {
        if(player.getTerritories().isEmpty()) return 0;
        return player.getTerritories().size() / 3;
    }

    private List<Combination> calculateCardCombinations(Player player)
    {
        List<Combination> result = new ArrayList<>();

        List<Card[]> combinationOfThree = new ArrayList<>();

        var cards = player.getCards();

        if(cards.isEmpty() || cards.size() < 3) return result;

        CombinatoricsUtils.combinationsIterator(cards.size(), 3).forEachRemaining(combination ->
        {
            Card[] combin =
            {
                cards.get(combination[0]),
                cards.get(combination[1]),
                cards.get(combination[2])
            };

            combinationOfThree.add(combin);
        });

        for(Card[] combination : combinationOfThree)
            if (CombinationTypeCalculation.isCombination(combination))
                result.add(new Combination(combination));

        return result;
    }
}
