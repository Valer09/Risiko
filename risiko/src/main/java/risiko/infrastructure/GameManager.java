package risiko.infrastructure;

import risiko.data.ActionsTrackingIO;
import risiko.data.GameStorage;
import risiko.model.GameState;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager
{
    private final InputToCommandForward inputToCommandForward;
    private final RunnerCallback callback;

    public GameManager(InputToCommandForward inputToCommandForward, RunnerCallback callback)
    {
        this.inputToCommandForward = inputToCommandForward;
        this.callback = callback;
    }

    public void loadGame()
    {

        var savedGameList = GameStorage.getSavedGameNamesList();
        if(savedGameList.isEmpty())
        {
            PrinterHelper.printSystemMessage("Non ci sono partite salvate!\n", false, true);
            var in = "";
            while(!in.equals(new NewGameNavCommand(callback).getCommandName()) && !in.equals(new EndGameNavCommand().getCommandName()))
            {
                PrinterHelper.printSystemMessage("Creare una nuova partita oppure terminare il gioco", true);
                in = new Scanner(System.in).next();
                if(inputToCommandForward.isCommand(in).isPresent()) inputToCommandForward.tryForwardAsCommand(in);
            }
            if(in.equals(new EndGameNavCommand().getCommandName())) System.exit(0);
            runNewGame();
        }

        int userInput = -1;
        while(userInput == -1 || userInput >= savedGameList.size() )
        {
            PrinterHelper.printSystemMessage("Scegli una partita: ", false, true, 0);
            for (int i = 0; i < savedGameList.size(); i++)
                PrinterHelper.printSystemMessage(i + " - > " + savedGameList.get(i), false, true);
            Scanner scanner = new Scanner(System.in);
            try
            {
                var next = scanner.next();
                if(InputTypeHelper.isInt(next)) userInput = Integer.parseInt(next);
                else inputToCommandForward.tryForwardAsCommand(next);
            }
            catch (InputMismatchException e)
            {
                PrinterHelper.printSystemMessage("Input non corretto, scegli il numero corrispondente", false);
            }
        }

        ActionsTrackingIO.loadTrackingFileFromSavedGame(savedGameList.get(userInput));

        GameState.cleanGameState();
        GameState.loadGameState(GameStorage.loadGame(savedGameList.get(userInput)));

        PrinterHelper.printMessageAndSimulateIODelay("Caricamento partita in corso. . .");
        PrinterHelper.printSuccessfulMessage("Partita caricata con successo!", false);

        GameState.getInstance().runPhases();
    }

    public void runNewGame()
    {
        Scanner scanner = new Scanner(System.in);
        boolean isTest = false;
        ActionsTrackingIO.createNewTrackingFile();
        GameState.cleanGameState();

        String wantTest = "";
        while(!wantTest.equalsIgnoreCase("NO") && !wantTest.equalsIgnoreCase("SI"))
        {
            PrinterHelper.printSystemMessage("Scegli se vuoi eseguire il gioco nella modalitá di prova.\nQuesta contiene degli obbiettivi semplificati per testare una partita completa in breve tempo\n[SI] [NO]", false, 1500);
            wantTest = scanner.next();
        }

        if(wantTest.equalsIgnoreCase("SI")) isTest = true;

        GameState.createNewState(isTest);
        GameState.getInstance().runPhases();
    }

}
