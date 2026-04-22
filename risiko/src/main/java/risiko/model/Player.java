package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.ContinentData;
import risiko.helper.PrinterHelper;

import java.util.*;

public class Player {

	private final Dice firstDice;
	@JsonProperty("name")
	private String name;
	@JsonProperty("armyColor")
	private ArmyColor armyColor;
	@JsonProperty("territories")
	private List<Territory> territories;
	@JsonProperty("cards")
	private List <Card> cards;
	@JsonProperty("goal")
	private PlayerGoal goal;

	/**
	 * JSON constructor
	 * */
	@JsonCreator
	public Player(
			@JsonProperty("name") String name,
			@JsonProperty("armyColor") ArmyColor armyColor,
			@JsonProperty("territories") List<Territory> territories,
			@JsonProperty("cards") List <Card>  cards,
			@JsonProperty("goal") PlayerGoal goal
			)
	{
		this.name = name;
		this.armyColor = armyColor;
		this.firstDice = new Dice(armyColor);
		this.goal = goal;
		this.cards = cards;
		this.territories = territories;
	}

	public Player(
			String name,
			ArmyColor armyColor) {
		
		this.name = name;
		this.armyColor = armyColor;
		this.firstDice = new Dice(armyColor);
		this.cards = new ArrayList<>();
		this.territories = new ArrayList<>();
	}
	
//GETTER E SETTER
	
	public String getName()
	{
		return name;
	}

	public ArmyColor getArmyColor()
	{
		return armyColor;
	}

	public List<Territory> getTerritories() {
		return territories;
	}


//ALTRI METODI
	public int rollDices()
	{
		var result = firstDice.roll();
		PrinterHelper.printPlayerAction(getName()+" lancia un dado ed ottiene "+ result, this, false, 350);
		return result;
	}

	public void addCardAndItsTerritory(TerritoryCard territoryCard)
	{
		cards.add(territoryCard);
		territories.add(territoryCard.getTerritory());
	}

	public void addCard(Card territoryCard)
	{
		cards.add(territoryCard);
	}

	public void setGoal(PlayerGoal goal)
	{
		this.goal = goal;
	}

	@JsonIgnore
	public int getArmiesCount()
	{
		return territories.stream().mapToInt(Territory::getArmiesCount).sum();
	}

	@JsonIgnore
	public List<Card> getCards()
	{
		return cards;
	}

	public boolean addArmiesToTerritory(int number, Territory territory)
	{
		if(!territories.contains(territory))
		{
			PrinterHelper.printSystemMessage("Non é possibile aggiungere truppe per il giocatore. Il territorio fornito non é in suo possesso", true, 200);
			return false;
		}

		territories.stream().filter(el -> el.equals(territory)).toList().getFirst().addArmies(number);
		PrinterHelper.printPlayerAction(String.format("%s aggiunge %d armate nel territorio %s", getName(), number, territory.getName()), this, false, 75);
		return true;
	}

	public void printArmiesPositioning()
	{
		PrinterHelper.printPlayerMessage(this.getName()+", questo é il numero delle tue armate ed il loro posizionamento", this,false,    650);
		for(Territory territory : territories)
            PrinterHelper.printPlayerMessage(territory.getName() + " : " + territory.getArmiesCount(), this, false, 50);
	}

	@JsonIgnore
	public PlayerGoal getGoal()
	{
		return this.goal;
	}

	@JsonIgnore
	public List<Continent> getOwnedContinents()
	{
		return ContinentData.getContinentsFromTerritories(territories);
	}

	public void cleanTerritoryCards()
	{
		this.cards.clear();
		PrinterHelper.printPlayerMessage(String.format("%s riconsegna tutte le sue carte", this.getName()), this, false, 800);
	}

	public void move(int amount, Territory from, Territory to)
	{
		if(
			territories.contains(from) &&
				territories.contains(to) &&
				territories.stream().filter(el -> el.getId() == from.getId()).toList().getFirst().getArmiesCount() > amount )
		{
			territories.stream().filter(el -> el.getId() == from.getId()).toList().getFirst().removeArmiesAmount(amount);
			territories.stream().filter(el -> el.getId() == to.getId()).toList().getFirst().addArmies(amount);
			PrinterHelper.printPlayerAction(String.format("%s ha spostato %d truppe da %s a %s", getName(), amount, from.getName(), to.getName()), this, false, 200 );
		}
		else
		{
			if(!territories.contains(from)) PrinterHelper.printPlayerMessage("Il territorio "+from.getName()+" non é in tuo possesso!", this, false, 200 );
			if(!territories.contains(to)) PrinterHelper.printPlayerMessage("Il territorio "+to.getName()+" non é in tuo possesso!", this, false, 200 );
			if(territories.stream().filter(el -> el.getId() == from.getId()).toList().getFirst().getArmiesCount() <= amount) PrinterHelper.printPlayerMessage("Il territorio "+from.getName()+" non contiene sufficienti armate!", this, false, 200 );
		}

	}

	public void cleanArmies()
	{
		for(Territory territory : territories)
			territory.removeArmiesAmount(territory.getArmiesCount());
		PrinterHelper.printPlayerAction(String.format("Le armate del giocatore %s sono state azzerate", getName()), this, false,500);
	}

	public void playCardsCombination(Combination combination)
	{
		for(Card card : combination.getCombination())
			if(!cards.contains(card))
			{
				PrinterHelper.printSystemMessage(this.getName()+" non possiede almeno una delle carte della combinazione fornita", true, 500);
				return;
			}else cards.remove(card);

		PrinterHelper.printPlayerAction(getName()+" gioca la combinazione di carte "+combination.getCombinationType().getDescription(), this, false, 500);
	}

	public void removeArmiesFromTerritory(int armiesCount, Territory territory)
	{
		if(territories.stream().noneMatch(el -> el.getId() == territory.getId())) throw new RuntimeException(getName()+" non possiede il territorio "+territory.getName());
		else territories.stream().filter(el -> el.getId() == territory.getId()).findFirst().get().removeArmiesAmount(armiesCount);
	}

	public void removeTerritory(Territory territory)
	{
		if(territories.stream().noneMatch(el -> el.getId() == territory.getId())) throw new RuntimeException(getName()+" non possiede il territorio "+territory.getName());
		else territories.remove(territories.stream().filter(el -> el.getId() == territory.getId()).findFirst().get());
	}

	public void addTerritory(Territory defendingTerritory)
	{
		territories.add(defendingTerritory);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Player)) return false;
		return this.name.equals(((Player)obj).getName()) && this.armyColor.equals(((Player)obj).getArmyColor());
	}
}
