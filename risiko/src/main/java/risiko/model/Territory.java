package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.TerritoryDefinition;
import risiko.helper.PrinterHelper;

public class Territory {

	@JsonProperty("territoryDefinition")
	private TerritoryDefinition territoryDefinition;
	@JsonProperty("armiesCount")
	private int armiesCount;

	@JsonCreator
	public Territory(@JsonProperty("territoryDefinition") TerritoryDefinition territoryDefinition, @JsonProperty("armiesCount")int armiesCount)
	{
		this.territoryDefinition = territoryDefinition;
		this.armiesCount = armiesCount;
	}

	@JsonIgnore
	public String getName()
	{
		return territoryDefinition.getName();
	}

	@JsonIgnore
	public int getArmiesCount()
	{
		return armiesCount;
	}

	public void addArmies(int count)
	{
		if(count > 0) this.armiesCount += count;
		else PrinterHelper.printSystemMessage("Non é stata aggiunta alcuna armata. É stata inserita una quantitá negativa.", false, false);
	}
	
	public void removeArmiesAmount(int count)
	{
		if(count == 0) return;
		if (count > 0 && count <= this.armiesCount) this.armiesCount -= count;
		else PrinterHelper.printSystemMessage("Non é stata eliminata alcuna armata. La quantitá inserita é sbagliata.", false, false);
	}

	@JsonIgnore
	public int getId()
	{
		return this.territoryDefinition.getId();
	}
}
