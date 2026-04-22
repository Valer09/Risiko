package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.TerritoryDefinition;
import risiko.helper.PrinterHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Deck
{
    @JsonProperty("deck")
    private List<Card> deck;

    @JsonCreator
    public Deck(@JsonProperty("deck") List<Card> deck)
    {
        this.deck = deck;
    }

    public static Deck createTerritoriesCardsForNewGame()
    {
        return new Deck(getFullCardList());
    }

    public void shuffle()
    {
        PrinterHelper.printSystemMessage("Mischio le carte. . . ", false, 1500);
        Collections.shuffle(deck);
    }

    @JsonIgnore
    public List<Card> getCards()
    {
        return deck;
    }

    public List<TerritoryCard> getTerritoryCardsOnly()
    {
        return deck.stream()
                .filter(el -> el instanceof TerritoryCard) // Filtra solo le istanze di TerritoryCard
                .map(el -> (TerritoryCard) el) // Effettua il casting a TerritoryCard
                .collect(Collectors.toList());
    }

    public TerritoryCard take(TerritoryCard card)
    {
        int index = deck.lastIndexOf(card);
        var cardToReturn = (TerritoryCard) deck.get(index);
        deck.remove(index);
        PrinterHelper.printSystemMessage("Presa una carta dal deck. Carte rimanenti: "+(long) deck.size(), false, 10);
        return cardToReturn;
    }

    private Card take(Card card)
    {
        int index = deck.lastIndexOf(card);
        var cardToReturn = deck.get(index);
        deck.remove(index);
        PrinterHelper.printSystemMessage("Presa una carta dal deck. Carte rimanenti: "+(long) deck.size(), false, 10);
        return cardToReturn;
    }

    public void reMake()
    {
        PrinterHelper.printSystemMessage("Rimetto tutte le carte nel deck. . .", false, 1500);
        this.deck = getFullCardList();
        this.shuffle();
    }

    private static ArrayList<Card> getFullCardList()
    {
        var cards = new ArrayList<Card>();

        for(int i = 1; i<= TerritoryDefinition.getTerritoriesCount(); i++)
            cards.add(new TerritoryCard(
                    "Carta territorio",
                    new Territory(TerritoryDefinition.fromId(i), 0),
                    CardSymbol.fromId(new Random().nextInt(CardSymbol.Knight.getId(), CardSymbol.Cannon.getId()+1))));

        cards.add(new JollyCard("Carta jolly"));
        cards.add(new JollyCard("Carta jolly"));
        return cards;
    }

    public Card takeOne()
    {
        if(this.deck.isEmpty()) throw new RuntimeException("Non c'é nessuna carta dal mazzo da poter prendere!");
        else return take(deck.getFirst());
    }
}
