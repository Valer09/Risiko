package risiko.infrastructure;

import risiko.helper.PrinterHelper;

import java.util.Scanner;

public class NavCommand implements Command
{
    private final Command implementation;

    public NavCommand(Command implementation)
    {
        this.implementation = implementation;
    }

    @Override
    public void doCommand()
    {
        implementation.doCommand();

        Scanner scanner = new Scanner(System.in);
        PrinterHelper.printSystemMessage("Premere qualsiasi tasto e poi invio per proseguire ", false, 1500);
        scanner.next();
    }

    @Override
    public String getCommandName()
    {
        return implementation.getCommandName();
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return implementation.getsFlowOwnership();
    }

}
