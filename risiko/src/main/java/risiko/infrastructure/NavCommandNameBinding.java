package risiko.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class NavCommandNameBinding
{
    public final static Map<NavCommandType, String> COMMAND_NAMES = new HashMap<>()
    {
        {
            put(NavCommandType.newGame, "nuova");
            put(NavCommandType.saveGame, "salva");
            put(NavCommandType.loadGame, "carica");
            put(NavCommandType.endGame, "termina");
            put(NavCommandType.stato, "stato");
            put(NavCommandType.showCommands, "aiuto");
            put(NavCommandType.showGoal, "obbiettivo");
            put(NavCommandType.clearConsole, "clear");
            put(NavCommandType.map, "mappa");
        }
    };
}
