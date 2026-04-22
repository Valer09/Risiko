package risiko.infrastructure;

import risiko.helper.PrinterHelper;

public class LoadGameNavCommand implements Command {

    private final RunnerCallback callback;

    public LoadGameNavCommand(RunnerCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void doCommand()
    {
        new SaveGameNavCommand().checkIfNeedsSave();
        PrinterHelper.printMessageAndSimulateIODelay("Caricamento delle partite salvate in corso. . .");
        callback.loadGameCallback();
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.loadGame);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return true;
    }
}
