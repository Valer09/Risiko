package risiko.infrastructure;

import risiko.data.GameStorage;
import risiko.model.GameState;
import risiko.helper.PrinterHelper;

import java.util.Scanner;

public class SaveGameNavCommand implements Command
{
    @Override
    public void doCommand()
    {
        var gameState = GameState.getInstance();
        if(gameState == null) PrinterHelper.printSystemMessage("Lo stato della partita é ancora vuoto", true);
        else
        {
            var gameName = "";
            while(gameName.isEmpty())
            {
                PrinterHelper.printSystemMessage("dai un nome alla partita: ", true);
                Scanner scanner = new Scanner(System.in);
                gameName = scanner.next();
            }

            if(GameStorage.saveGame(gameName, gameState))
            {
                GameState.getInstance().saved();
                PrinterHelper.printMessageAndSimulateIODelay("Salvataggio partita in corso. . .");
                PrinterHelper.printSuccessfulMessage("Partita salvata con successo!", false);
            }
        }
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.saveGame);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }

    public void checkIfNeedsSave()
    {
        GameState instance = GameState.getInstance();
        if(instance == null) return;

        if(instance.hasChanged())
        {
            PrinterHelper.printSystemMessage("Se i dati di gioco non sono stati salvati, andranno persi.\nVuoi salvare prima la partita corrente?", false);

            var input = "";
            while((!input.equalsIgnoreCase("SI") && !input.equalsIgnoreCase("NO")) )
            {
                Scanner scanner = new Scanner(System.in);
                PrinterHelper.printSystemMessage("Inserisci SI per salvare, NO per continuare", true);
                input = scanner.next();
            }
            if(input.equalsIgnoreCase("SI")) doCommand();
        }
    }
}
