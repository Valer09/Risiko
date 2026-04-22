package risiko.infrastructure;


public interface Command
{
    void doCommand();
    String getCommandName();
    boolean getsFlowOwnership();
}
