package risiko.model.phases.steps;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.model.GameState;
import risiko.model.Player;
import risiko.model.Territory;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;
import risiko.infrastructure.Command;
import risiko.infrastructure.InputToCommandForward;

import java.util.*;

public class ArmiesPositioningStep implements GameStep
{
    private final InputToCommandForward inputToCommandForward;
    @JsonProperty("areArmiesSet")
    private boolean areArmiesSet;

    //@JsonProperty("isMainPlayerSet")
    //private boolean isMainPlayerSet;

    @JsonCreator
    public ArmiesPositioningStep(@JsonProperty("areArmiesSet") boolean areArmiesSet/*,@JsonProperty("isMainPlayerSet") boolean isMainPlayerSet*/)
    {
        //this.isMainPlayerSet = isMainPlayerSet;
        this.inputToCommandForward = InputToCommandForward.getInstance();
        this.areArmiesSet = areArmiesSet;
    }

    public ArmiesPositioningStep()
    {
        this.inputToCommandForward = InputToCommandForward.getInstance();
    }

    @Override
    public void run()
    {
        if(!areArmiesSet)
        {
            runSetArmies();
            runPutBackCards();
        }
    }


    @Override
    public boolean isCompleted()
    {
        return areArmiesSet;
    }

    private void runPutBackCards()
    {
        GameState gameState = GameState.getInstance();

        var playerList = gameState.getPlayers();

        for(Player player : playerList)
            player.cleanTerritoryCards();

        gameState.getDeck().reMake();

        areArmiesSet = true;
    }

    private void runSetArmies()
    {
        GameState gameState = GameState.getInstance();
        var mainPlayer = gameState.getOptionalMainPlayer().get();
        gameState.getPlayers().remove(mainPlayer);
        gameState.getPlayers().addFirst(mainPlayer);

        var playerList = gameState.getPlayers();

        for(Player player : playerList) player.cleanArmies();

        PrinterHelper.printSystemMessage("Posizionamento delle armate", false, 500);


        Scanner scanner = new Scanner(System.in);
        int in = 0;
        while(in != 1 && in != 2)
        {
            PrinterHelper.printSystemMessage("Inserisci", false, 50);
            PrinterHelper.printSystemMessage("1 per procedere nella modalitá classica di posizionamento delle armate", false, 500);
            PrinterHelper.printSystemMessage("2 per procedere ad un posizionamento automatico distribuito delle armate", true, 500);
            var temp = scanner.next();
            if(inputToCommandForward.isCommand(temp).isPresent())
            {
                inputToCommandForward.tryForwardAsCommand(temp);
                continue;
            }
            if(InputTypeHelper.isInt(temp)) in = Integer.parseInt(temp);
        }
        int armiesPerPlayer = calculateArmiesNumber(playerList.size(), in == 1);
        var count = armiesPerPlayer;
        if(in == 1)
        {
            PrinterHelper.printSystemMessage(String.format("Saranno fornite %d armate iniziali a testa. Inserirete 3 armate a turno finché non saranno finite", armiesPerPlayer), false, 500);
            PrinterHelper.printSystemMessage(mainPlayer.getName()+" sara' il primo giocatore", false, 500);

            while(count > 0)
            {
                PrinterHelper.printSystemMessage("Armate rimanenti: "+count, false, 500);
                for(Player player : playerList)
                {
                    GameState.getInstance().setCurrentPlayerIndex(GameState.getInstance().getPlayers().indexOf(player));
                    if(letPlayerSetArmies(player, count).isPresent()) return;
                    if(player.getArmiesCount() == armiesPerPlayer)
                        checkIfOccupiesAllTerritories(player);
                }
                count-=3;
            }
        }
        else distributeArmiesAutomatically(armiesPerPlayer, playerList);

        PrinterHelper.printSystemMessage("Posizionamento armate completo", true, 10);

    }

    private void distributeArmiesAutomatically(int count, List<Player> playerList)
    {
        for(Player player : playerList)
        {
            var playerTerritories = player.getTerritories();
            int armiesPerTerritory = count / playerTerritories.size();
            var totalArmies = count;
            while(totalArmies > 0 )
            {
                for(Territory territory : playerTerritories)
                {
                    if(totalArmies <=0) break;
                    int armiesToDistribute = Math.min(armiesPerTerritory, totalArmies);
                    player.addArmiesToTerritory(armiesToDistribute, territory);
                    totalArmies -= armiesToDistribute;
                }
            }
        }
    }

    private Optional<Command> letPlayerSetArmies(Player player, int armiesPerPlayer)
    {
        PrinterHelper.printPlayerMessage(player.getName()+" é il tuo turno, comincia a posizionare le armate!", player, false, 800);

        Scanner scanner = new Scanner(System.in);
        List<Territory> territories = player.getTerritories();

        int in = 0;
        var remaining = Math.min(armiesPerPlayer, 3);

        while (remaining > 0)
        {

            int in2 = 0;

            Map<Integer, Territory> choises = new HashMap<>();

            for (int j = 0; j < territories.size(); j++) choises.put(j + 1, territories.get(j));
            while(!choises.containsKey(in2))
            {
                PrinterHelper.printPlayerMessage("Seleziona il territorio: ", player, false, 500);
                in2 = printCurrentTerritories(player, scanner, in2, choises, true);
            }

            while(in < 1 || in > remaining)
            {
                PrinterHelper.printPlayerMessage("Seleziona quante armate vuoi posizionare. Al momento puoi posizionare fino a "+remaining+" armate",  player, true, 200);
                var next = scanner.next();
                if(inputToCommandForward.isCommand(next).isPresent())
                {
                    inputToCommandForward.tryForwardAsCommand(next);
                    continue;
                }
                if(!InputTypeHelper.isInt(next)) continue;
                in = Integer.parseInt(next);
            }

            PrinterHelper.printPlayerMessage(in+" armate posizionate in "+choises.get(in2).getName(), player, false, 500);

            if(!player.addArmiesToTerritory(in, choises.get(in2))) continue;
            remaining -= in;
            in = 0;
        }
        PrinterHelper.printPlayerMessage(player.getName()+" ottimo lavoro!\n", player, false, 500);

        return Optional.empty();
    }

    private void checkIfOccupiesAllTerritories(Player player)
    {
        if(player.getTerritories().stream().noneMatch(el -> el.getArmiesCount() == 0)) return;

        PrinterHelper.printPlayerMessage(player.getName()+" sembra che non abbia inserito le truppe in ogni territorio. Dovresti possedere almeno una armata per ogni territorio.", player,false,    1000);

        while(player.getTerritories().stream().anyMatch(el -> el.getArmiesCount() == 0))
        {
            player.printArmiesPositioning();
            PrinterHelper.printPlayerMessage("\nSposta le truppe da un territorio ad un altro per occupare i territori che non hai giá occupato", player,false,    500);

            Map<Integer, Territory> choises = new HashMap<>();
            Map<Integer, Territory> choisesToPut = new HashMap<>();
            Scanner scanner = new Scanner(System.in);
            var territories = player.getTerritories();
            int in2=0;

            for (int j = 0; j < territories.size(); j++)
                if(territories.get(j).getArmiesCount() > 0) choises.put(j + 1, territories.get(j));
                else choisesToPut.put(j + 1, territories.get(j));
            while(!choises.containsKey(in2))
            {
                PrinterHelper.printPlayerMessage("Seleziona il territorio da cui spostare le truppe: ", player, false, 500);
                in2 = printCurrentTerritories(player, scanner, in2, choises, false);
            }

            int in3 = 0;
            while(!choisesToPut.containsKey(in3))
            {
                PrinterHelper.printPlayerMessage("Seleziona il territorio senza armate in cui metterle: ", player, false, 500);
                for(Integer key : choisesToPut.keySet())
                    PrinterHelper.printPlayerMessage(key + " per " + choisesToPut.get(key).getName() , player, false, 50);

                var next = scanner.next();
                if(inputToCommandForward.isCommand(next).isPresent())
                {
                    inputToCommandForward.tryForwardAsCommand(next);
                    continue;
                }

                if(!InputTypeHelper.isInt(next)) continue;
                in3 = Integer.parseInt(next);
            }

            int in4 = 0;
            while(in4 <= 0 || in4 > choises.get(in2).getArmiesCount() -1 )
            {
                PrinterHelper.printPlayerMessage("Quante armate cuoi spostare?", player, false, 500);
                var next = scanner.next();
                if(inputToCommandForward.isCommand(next).isPresent())
                {
                    inputToCommandForward.tryForwardAsCommand(next);
                    continue;
                }

                if(!InputTypeHelper.isInt(next)) continue;
                in4 = Integer.parseInt(next);
            }
            player.move(in4, choises.get(in2), choisesToPut.get(in3));
        }
    }

    private int printCurrentTerritories(Player player, Scanner scanner, int in2, Map<Integer, Territory> choises, boolean isFirstPositioning) {
        for(Integer key : choises.keySet())
        {
            int armiesCount = player.getTerritories().stream().filter(el -> el.getId() == choises.get(key).getId()).toList().getFirst().getArmiesCount();
            if(armiesCount > 1 || isFirstPositioning)
                PrinterHelper.printPlayerMessage(key + " per " + choises.get(key).getName() + ": armate correnti -> " + armiesCount, player, false, 50);
        }
        var next = scanner.next();
        if(inputToCommandForward.isCommand(next).isPresent())
        {
            inputToCommandForward.tryForwardAsCommand(next);
            return in2;
        }
        if(!InputTypeHelper.isInt(next)) return in2;
        in2 = Integer.parseInt(next);
        return in2;
    }


    private int calculateArmiesNumber(int count, boolean fake)
    {
        if(fake) return GameState.getInstance().getPlayers().getFirst().getTerritories().size();
        return switch (count)
        {
            case 2 -> 40;
            case 3 -> 35;
            case 4 -> 30;
            case 5 -> 25;
            case 6 -> 20;

            default -> throw new IllegalArgumentException("Il numero di giocatori deve essere compreso tra 2 e 6");
        };
    }
}
