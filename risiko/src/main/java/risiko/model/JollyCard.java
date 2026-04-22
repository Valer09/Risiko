package risiko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JollyCard extends Card {

	public JollyCard(@JsonProperty("description") String description)
	{
		super(description);
	}

	@JsonIgnore
	public CardSymbol getCardSymbol()
	{
		return CardSymbol.All;
	}
}
