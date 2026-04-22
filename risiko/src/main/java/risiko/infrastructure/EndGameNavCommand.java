package risiko.infrastructure;

import risiko.helper.PrinterHelper;

import java.util.Scanner;

public class EndGameNavCommand implements Command
{
    @Override
    public void doCommand()
    {
        Scanner scanner = new Scanner(System.in);
        PrinterHelper.printSystemMessage("Se i dati di gioco non sono stati salvati, andranno persi.", false);

        var input = "";
        while((!input.equalsIgnoreCase("SI") && !input.equalsIgnoreCase("NO")) )
        {
            PrinterHelper.printSystemMessage("Vuoi prima salvare la partita [SI] [NO]?", true);
            input = scanner.next();
        }
        if(input.equalsIgnoreCase("SI"))
        {
            new SaveGameNavCommand().doCommand();
        }

        PrinterHelper.printSystemMessage("Chiusura del gioco", false, 500);
        System.exit(0);
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.endGame);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return true;
    }
}
