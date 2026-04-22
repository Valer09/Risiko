package risiko.infrastructure;

import risiko.helper.InputTypeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InputToCommandForward
{
    private static InputToCommandForward instance;

    private final List<Command> availableCommands;

    public InputToCommandForward(List<Command> availableCommands)
    {
        this.availableCommands = availableCommands;
    }

    public void tryForwardAsCommand(String next)
    {
        if(InputTypeHelper.isInt(next)) return;
        Optional<Command> navigationCommand = isCommand(next);
        if(navigationCommand.isPresent())
        {
            if(navigationCommand.get().getsFlowOwnership())
            {
                System.out.printf("Interruzione del gioco propagata dal comando '%s'\n", navigationCommand.get().getCommandName());
                throw new InterruptedGameException(navigationCommand.get());
            } else navigationCommand.get().doCommand();
        }
    }

    public Optional<Command> isCommand(String next)
    {
        for(Command cmd : availableCommands)
            if (cmd.getCommandName().equalsIgnoreCase(next)) return Optional.of(cmd);
        return Optional.empty();
    }

    public static InputToCommandForward getInstance()
    {
        if(instance == null) instance = new InputToCommandForward(new ArrayList<>());
        return instance;
    }

    public static InputToCommandForward createInstance(RunnerCallback callback) {
        instance =
        new InputToCommandForward(new ArrayList<>(Arrays.asList(
                new NavCommand(new LoadGameNavCommand(callback)),
                new NavCommand(new NewGameNavCommand(callback)),
                new NavCommand(new SaveGameNavCommand()),
                new NavCommand(new HelpNavCommand()),
                new NavCommand(new ShowStateCommand()),
                new NavCommand(new EndGameNavCommand()),
                new NavCommand(new CheckGoalNavCommand()),
                new NavCommand(new ClearConsoleNavCommand()),
                new NavCommand(new ShowMapNavCommand()))));
        return instance;
    }
}
