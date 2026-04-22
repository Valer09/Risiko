package risiko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TerritoryCard extends Card {

	@JsonProperty("territory")
	private final Territory territory;

	@JsonProperty("cardSymbol")
	private final CardSymbol cardSymbol;

	public TerritoryCard(
			@JsonProperty("description")String description,
			@JsonProperty("territory") Territory territory,
			@JsonProperty("cardSymbol") CardSymbol cardSymbol)
	{
		super(description);
		this.cardSymbol = cardSymbol;
		this.territory = territory;
	}
	
	public Territory getTerritory()
	{
		return territory;
	}

	@JsonIgnore
	public CardSymbol getCardSymbol()
	{
		return cardSymbol;
	}
}
