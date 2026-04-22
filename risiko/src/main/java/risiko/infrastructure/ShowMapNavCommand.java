package risiko.infrastructure;

import risiko.data.TerritoryDefinition;
import risiko.model.GameState;
import risiko.model.Player;
import risiko.model.Territory;
import risiko.helper.PrinterHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowMapNavCommand implements Command
{

    @Override
    public void doCommand()
    {
        if(GameState.getInstance() == null) return;
        var players = GameState.getInstance().getPlayers();

        var terrDefinitions = TerritoryDefinition.values();
        HashMap<TerritoryDefinition, Player> ownedTerritories = new HashMap<>();

        List<TerritoryDefinition> neutralTerritories = new ArrayList<>();

        for(Player player : GameState.getInstance().getPlayers())
            for(Territory territory : player.getTerritories())
                ownedTerritories.put(TerritoryDefinition.fromId(territory.getId()), player);

        for(TerritoryDefinition territoryDefinition : terrDefinitions)
            if (!ownedTerritories.containsKey(territoryDefinition)) neutralTerritories.add(territoryDefinition);

        PrinterHelper.printSystemMessage("Questa e' la mappa attuale: ", false, 750);

        PrinterHelper.printSystemMessage("STATI NEUTRALI: ", false, 1250);
        StringBuilder s = new StringBuilder();
        for(TerritoryDefinition territoryDefinition : neutralTerritories)
            s.append(territoryDefinition.getName()).append("  \n");
        if(s.length() >=2) s.delete(s.length() - 2, s.length());
        System.out.println(s);

        PrinterHelper.ahead();


        if (!GameState.getInstance().getPlayers().isEmpty() && GameState.getInstance().getPlayers().stream().anyMatch(el -> !el.getTerritories().isEmpty()))
        {
            PrinterHelper.printSystemMessage("STATI POSSEDUTI: ", false, 1250);

            for(Player player : players)
            {
                PrinterHelper.printPlayerMessage(String.format("Il giocatore %s possiede i seguenti territori e armate: ", player.getName()), player, false, 1500);
                StringBuilder list = new StringBuilder();
                for (Territory t : player.getTerritories())
                {
                    StringBuilder borders = new StringBuilder();
                    for(TerritoryDefinition td : TerritoryDefinition.getBorderingTerritoriesById(t.getId()))
                        borders.append(td.getName()).append(", ");

                    if(borders.length() >=2) borders.delete(borders.length() - 2, borders.length());
                    list.append("# ").append(t.getArmiesCount()).append(" armate in ").append(t.getName().toUpperCase()).append("\n").append("---> territori confinanti: ").append(borders).append('\n');
                }
                list.append("\n-> per un totale di ").append(player.getTerritories().size()).append(" Territori e ").append(player.getTerritories().stream().mapToInt(Territory::getArmiesCount).sum()).append(" Armate ").append("\n");

                PrinterHelper.printPlayerMessage(String.format(list.substring(0, list.toString().length()-2)+"\n", player.getName()), player, false, 0);
            }
        }
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.map);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }
}
