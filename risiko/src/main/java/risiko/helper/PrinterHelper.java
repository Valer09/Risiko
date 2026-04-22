package risiko.helper;

import risiko.data.ActionsTrackingIO;
import risiko.model.ArmyColor;
import risiko.infrastructure.NavCommandNameBinding;
import risiko.infrastructure.NavCommandType;
import risiko.model.Player;

public class PrinterHelper
{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_DARK_GRAY= "\033[1;30m";
    private static final String ANSI_LIGHT_GRAY= "\033[0;37m";
    public static final int IO_NORMAL = 500;
    public static final int IO_VERY_FAST = 50;
    public static final int IO_FAST = 200;
    public static final int IO_SLOW = 1500;

    public static void printSystemMessage(String message, boolean attendiInput)
    {
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
        if(attendiInput) printTip();
        else System.out.println();
        simulateIODelay(IO_VERY_FAST);
    }

    public static void printSystemMessage(String message, boolean attendiInput, long delay )
    {
        printSystemMessage(message, attendiInput);
        if(delay > 0 ) simulateIODelay(delay);
    }


    public static void printSystemMessage(String message, boolean attendiInput, boolean noSpaces)
    {
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
        if(attendiInput) printTip();
        else if(!noSpaces) System.out.println();
    }

    public static void printSystemMessage(String message, boolean attendiInput, boolean noSpaces, long delay )
    {
        printSystemMessage(message, attendiInput, noSpaces);
        if(delay > 0) simulateIODelay(delay);
    }

    public static void printSuccessfulMessage(String message, boolean waitUserInput)
    {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
        if(waitUserInput) printTip();
        else System.out.println();
        simulateIODelay(500);
    }

    public static void printPlayerMessage(String message, Player player, boolean waitUserInput, long delay)
    {
        System.out.println(getFontColorFromArmyColor(player.getArmyColor()) + message + ANSI_RESET);
        if(waitUserInput) printTip();
        if(delay > 0 ) simulateIODelay(delay);
    }

    public static void printPlayerAction(String message, Player player, boolean waitUserInput, long delay)
    {
        System.out.println(getFontColorFromArmyColor(player.getArmyColor()) + message + ANSI_RESET);
        ActionsTrackingIO.writeActionToTrackingFile(message);
        if(waitUserInput) printTip();
        if(delay > 0 ) simulateIODelay(delay);
    }

    public static void printCommands()
    {
        var names = NavCommandNameBinding.COMMAND_NAMES;
        System.out.println(ANSI_LIGHT_GRAY+"NAVIGAZIONE - inserisci: ");
        System.out.printf("'%s' -> nuova partita \n'%s' -> salva partita \n'%s' -> carica partita \n'%s' -> mostra stato della partita \n'%s' -> termina partita \n'%s' -> vedi obbiettivo \n'%s' -> mostra mappa \n",
                names.get(NavCommandType.newGame),
                names.get(NavCommandType.saveGame),
                names.get(NavCommandType.loadGame),
                names.get(NavCommandType.stato),
                names.get(NavCommandType.endGame),
                names.get(NavCommandType.showGoal),
                names.get(NavCommandType.map));
        System.out.println("---------------------------------" + ANSI_RESET);
        System.out.println();
        simulateIODelay(200);
    }

    public static void printTip()
    {
        System.out.println(ANSI_DARK_GRAY+"Per visualizzare i comandi inserisci -> aiuto");
        System.out.println("---------------------------------------------" + ANSI_RESET);

        simulateIODelay(IO_SLOW);
    }

    public static void printMessageAndSimulateIODelay(String IOMessage)
    {
        printSystemMessage(IOMessage, false);
        simulateIODelay(IO_SLOW);
    }

    public static void printSystemMessageInLine(String message, boolean noDelay)
    {
        System.out.print(ANSI_PURPLE + message + ANSI_RESET);
        if(!noDelay) simulateIODelay(IO_FAST);
    }

    public static void ahead()
    {
        System.out.println();
    }

    private static void simulateIODelay(long ms)
    {
        try
        {
            Thread.sleep(ms);
        }catch (InterruptedException ignored){}
    }

    private static String getFontColorFromArmyColor(ArmyColor coloreArmata)
    {
        return switch (coloreArmata)
        {
            case Red -> ANSI_RED;
            case Blue -> ANSI_BLUE;
            case Green -> ANSI_GREEN;
            case White -> ANSI_WHITE;
            case Yellow -> ANSI_YELLOW;
            case Purple -> ANSI_CYAN;
        };
    }
}
