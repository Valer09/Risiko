package risiko.infrastructure;

import risiko.helper.PrinterHelper;

public class HelpNavCommand implements Command
{
    @Override
    public void doCommand()
    {
        PrinterHelper.printCommands();
    }

    @Override
    public String getCommandName()
    {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.showCommands);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }
}
