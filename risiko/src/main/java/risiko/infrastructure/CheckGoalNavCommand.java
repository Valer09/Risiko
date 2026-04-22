package risiko.infrastructure;

import risiko.model.GameState;
import risiko.helper.PrinterHelper;

public class CheckGoalNavCommand implements Command
{
    @Override
    public void doCommand()
    {
        GameState gameState = GameState.getInstance();
        if(gameState == null || gameState.getCurrentPlayer() == null || gameState.getCurrentPlayer().getGoal() == null)
            PrinterHelper.printSystemMessage("Il gioco non é ancora in una fase che permette di visualizzare gli obbiettivi", false, 500);
        else {
            var player = gameState.getCurrentPlayer();
            var goal = player.getGoal();

            PrinterHelper.printPlayerAction(player.getName() + " é stato richiesto di visualizzare l'obbiettivo", player, false, 1000);
            PrinterHelper.printPlayerMessage("Fra 5 secondi il tuo obbiettivo sará visualizzato.", player, false, 700);
            PrinterHelper.printPlayerMessage("Mi raccomando, fai in modo che non sará visibile agli altri giocatori!", player, false, 1000);
            for (int i = 1; i <= 5; i++) PrinterHelper.printSystemMessage(i + ". . .", false, true, 1000);
            PrinterHelper.printPlayerMessage("Questo é il tuo obbiettivo: "+ goal.getDescription(), player, false, 2100);
            PrinterHelper.printPlayerMessage("Inserisci il comando "+NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.clearConsole)+" se vuoi pulire la console e nascondere il tuo obbiettivo", player, true, 4500);
        }
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.showGoal);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }
}
