package risiko.infrastructure;

import risiko.model.GameState;
import risiko.model.Player;
import risiko.model.*;
import risiko.helper.PrinterHelper;

import java.util.List;

public class ShowStateCommand implements Command
{

    @Override
    public void doCommand()
    {
        var state = GameState.getInstance();
        if(state == null) PrinterHelper.printSystemMessage("Lo stato della partita é ancora vuoto", false);
        else
        {
            List<Player> players = state.getPlayers();

            PrinterHelper.printSystemMessage(String.format("Ci sono %d giocatori", (long) players.size())+":", false);
            for (Player player : players) PrinterHelper.printPlayerMessage(player.getName()+" ", player, false, 100);

            PrinterHelper.ahead();

            var mainPlayer = state.getOptionalMainPlayer();

            PrinterHelper.printSystemMessage(state.getPhasesInfo(), false);

            if(mainPlayer.isPresent())
                PrinterHelper.printSystemMessage("Il giocatore iniziale é "+ mainPlayer.get().getName(), false);
            else
                PrinterHelper.printSystemMessage("Il giocatore iniziale non e'ancora stato definito", false);

            PrinterHelper.printSystemMessage(String.format("Il deck contiene ancora %d carte", state.getDeck().getCards().size()) , false);


            new ShowMapNavCommand().doCommand();

            PrinterHelper.ahead();
            PrinterHelper.printSystemMessage("Lo stato della partita é "+ (state.hasChanged() ? "non salvata" : "salvata"), false);
            PrinterHelper.ahead();
            PrinterHelper.ahead();
        }
    }

    @Override
    public String getCommandName() {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.stato);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }

}
