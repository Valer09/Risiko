package risiko.infrastructure;

public class InterruptedGameException extends RuntimeException
{
    private final Command command;

    public InterruptedGameException(Command command)
    {
        this.command = command;
    }

    public void executeCommand()
    {
        command.doCommand();
    }
}
