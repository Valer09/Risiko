package risiko.infrastructure;

import risiko.helper.PrinterHelper;
import risiko.model.Player;

public class VictoryException extends RuntimeException
{
    private final Player player;

    public VictoryException(Player player)
    {
        this.player = player;
    }

    public void runVictoryMessage()
    {
        PrinterHelper.printPlayerAction(String.format("VITTORIA!!\n %s ha vinto la partita raggiungendo il suo obbiettivo!\n ", player.getName()), player, false, 1000);
        PrinterHelper.printSystemMessage(String.format("L'obbiettivo di %s era %s", player.getName(), player.getGoal().getDescription()),  false, 3500);

        PrinterHelper.printSystemMessage("Il gioco verrá chiuso. Per giocare di nuovo effettuare una nuova esecuzione.",  false, 1500);
    }
}
