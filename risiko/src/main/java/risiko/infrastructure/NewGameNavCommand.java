package risiko.infrastructure;

import risiko.helper.PrinterHelper;

public class NewGameNavCommand implements Command
{
    private final RunnerCallback callback;

    public NewGameNavCommand(RunnerCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void doCommand()
    {
        new SaveGameNavCommand().checkIfNeedsSave();
        PrinterHelper.printMessageAndSimulateIODelay("Creazione nuova partita. . .");
        callback.createNewGameCallback();
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.newGame);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return true;
    }
}
