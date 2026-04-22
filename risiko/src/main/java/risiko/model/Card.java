package risiko.model;

/* Implemento la classe Carte come classe astratta per creare poi estensioni della classe stessa per i diversi tipi di carte
 * presenti nel gioco risiko, ossia carte territorio, carte jolly e carte obiettivo*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = JollyCard.class, name = "JollyCard"),
		@JsonSubTypes.Type(value = TerritoryCard.class, name = "TerritoryCard"),
})

public abstract class Card
{
	String description;

	public Card(String description)
	{
		this.description = description;
	}
	
	public void setDescription(String descrizione)
	{
		this.description = descrizione;
	}
	
	public String getDescription()
	{
		return this.description;
	}

	@JsonIgnore
	public abstract CardSymbol getCardSymbol();
}
