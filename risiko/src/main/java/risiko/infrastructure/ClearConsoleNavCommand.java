package risiko.infrastructure;

public class ClearConsoleNavCommand implements Command
{
    @Override
    public void doCommand()
    {
        System.out.println("\n".repeat(50));
    }

    @Override
    public String getCommandName() {
        return NavCommandNameBinding.COMMAND_NAMES.get(NavCommandType.clearConsole);
    }

    @Override
    public boolean getsFlowOwnership()
    {
        return false;
    }
}
