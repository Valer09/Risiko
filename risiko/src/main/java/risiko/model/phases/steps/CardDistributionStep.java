package risiko.model.phases.steps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.GoalData;
import risiko.model.ArmyColor;
import risiko.model.GameState;
import risiko.model.Player;
import risiko.model.*;
import risiko.helper.ArmyColorTranslation;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;
import risiko.infrastructure.InputToCommandForward;

import java.util.*;
import java.util.Map;

public class CardDistributionStep implements GameStep
{
    private final InputToCommandForward inputToCommandForward;
    @JsonProperty("arePlayersSet")
    private boolean arePlayersSet;
    @JsonProperty("areCardsSet")
    private boolean areCardsSet;
    @JsonProperty("isFirstPlayerSet")
    private boolean isFirstPlayerSet;
    @JsonProperty("arePlayersGoalsSet")
    private boolean arePlayersGoalsSet;

    @JsonCreator
    public CardDistributionStep(
            @JsonProperty("arePlayersSet") boolean arePlayersSet,
            @JsonProperty("areCardsSet") boolean areCardsSet,
            @JsonProperty("isFirstPlayerSet") boolean isFirstPlayerSet,
            @JsonProperty("arePlayersGoalsSet") boolean arePlayersGoalsSet)
    {
        this.inputToCommandForward = InputToCommandForward.getInstance();
        this.arePlayersSet = arePlayersSet;
        this.areCardsSet = areCardsSet;
        this.isFirstPlayerSet = isFirstPlayerSet;
        this.arePlayersGoalsSet = arePlayersGoalsSet;
    }

    public CardDistributionStep()
    {
        this.inputToCommandForward = InputToCommandForward.getInstance();
        arePlayersSet = false;
        areCardsSet = false;
        isFirstPlayerSet = false;
        arePlayersGoalsSet = false;
    }

    @Override
    public void run()
    {
        if(!arePlayersSet) runSetPlayer();
        if(!areCardsSet) runSetCards();
        if(!isFirstPlayerSet) runSetFirstPlayer();
        if(!arePlayersGoalsSet) runSetPlayersGoals();
    }

    @Override
    public boolean isCompleted()
    {
        return arePlayersSet && areCardsSet && isFirstPlayerSet && arePlayersGoalsSet;
    }

    private void runSetPlayer() {
        int playersNum = 0;
        while(playersNum <= 1 || playersNum > 6)
        {
            PrinterHelper.printSystemMessage("Inserisci il numero di giocatori (da 2 a 6): ", true);
            Scanner scanner = new Scanner(System.in);
            var in = scanner.next();
            if(inputToCommandForward.isCommand(in).isPresent())
            {
                inputToCommandForward.tryForwardAsCommand(in);
                continue;
            }

            if(InputTypeHelper.isInt(in))
                playersNum = Integer.parseInt(in);
        }

        String[] names = new String[playersNum];

        for (int i = 0; i < playersNum;)
        {
            PrinterHelper.printSystemMessage("Inserisci il nome del giocatore "+(i+1)+": ", false);
            Scanner scanner = new Scanner(System.in);
            var in = scanner.next();
            if(inputToCommandForward.isCommand(in).isPresent())
            {
                inputToCommandForward.tryForwardAsCommand(in);
                continue;
            }
            if(InputTypeHelper.isInt(in)) continue;
            names[i] = in;
            i++;
        }
        var colors = ArmyColor.values();
        Map<ArmyColor, Boolean> armyColorsAvailability = new HashMap<>();
        for (ArmyColor color : colors) armyColorsAvailability.put(color, true);

        ArmyColor[] choosen = new ArmyColor[names.length];

        for (int i = 0; i < playersNum;)
        {
            PrinterHelper.printSystemMessage("Inserisci il colore delle armate di "+names[i]+", colori disponibili: ", false);
            for (ArmyColor armyColor : armyColorsAvailability.keySet())
                if(armyColorsAvailability.get(armyColor))
                    PrinterHelper.printSystemMessageInLine(ArmyColorTranslation.getItalianColor(armyColor) + ArmyColorTranslation.getItalianColorLabel(armyColor) + ',' , true);
            PrinterHelper.ahead();

            Scanner scanner = new Scanner(System.in);
            var in = scanner.next();
            if(inputToCommandForward.isCommand(in).isPresent())
            {
                inputToCommandForward.tryForwardAsCommand(in);
                continue;
            }
            if(InputTypeHelper.isInt(in)) continue;
            if(!containArmyColor(in)) continue;
            if(in.isEmpty()) continue;
            if(in.length() != 1) continue;
            if(!armyColorsAvailability.get(ArmyColorTranslation.getColorFromFirstChar(in.charAt(0))))
            {
                PrinterHelper.printSystemMessage("Questo colore é giá stato scelto da un altro giocatore", false, 500);
                continue;
            }
            armyColorsAvailability.put(ArmyColorTranslation.getColorFromFirstChar(in.charAt(0)), false);
            choosen[i] = ArmyColorTranslation.getColorFromFirstChar(in.charAt(0));
            i++;
        }

        List<Player> gamePlayers = new ArrayList<>();
        for (int i = 0; i < names.length; i++)
            gamePlayers.add(new Player(names[i], choosen[i]));

        GameState.getInstance().setPlayers(gamePlayers);
        arePlayersSet = true;
        PrinterHelper.printSystemMessage("Giocatori confermati!", false, 200);
    }

    private void runSetCards()
    {
        PrinterHelper.printSystemMessage("Preparazione delle carte in corso. . .", true);

        var deck = GameState.getInstance().getDeck();

        deck.shuffle();

        var territoryCards = deck.getTerritoryCardsOnly();
        List<Player> players = GameState.getInstance().getPlayers();
        int playerNumber = players.size();
        int territoriesPerPlayer = territoryCards.size() / playerNumber;

        int count = 0;
        for(Player player : players)
            for (int i = 0; i < territoriesPerPlayer; i++)
                player.addCardAndItsTerritory(deck.take(territoryCards.get(count++)));

        PrinterHelper.printMessageAndSimulateIODelay("Distribuisco le carte. . .");
        PrinterHelper.printMessageAndSimulateIODelay("Carte distribuite!");
        areCardsSet = true;
    }

    private void runSetFirstPlayer()
    {
        PrinterHelper.printSystemMessage("Selezione del primo giocatore", false);
        PrinterHelper.printSystemMessage("Il giocatore che fará il punteggio piú alto sará il giocatore a fare la prima mossa", false, 200);

        List<Player> players = GameState.getInstance().getPlayers();

        Scanner scanner = new Scanner(System.in);
        List<Player> sameResultPlayers = new ArrayList<>();
        var max = 0;

        Player winner = players.getFirst();

        for (Player player : players) 
        {
            PrinterHelper.printPlayerMessage(player.getName() + " premi un tasto qualsiasi per lanciare un dado e premi invio", player, true, 200);
            var in = scanner.next();
            while(inputToCommandForward.isCommand(in).isPresent())
            {
              inputToCommandForward.tryForwardAsCommand(in);
              PrinterHelper.printPlayerMessage(player.getName() + " premi un tasto qualsiasi per lanciare un dado e premi invio", player, true, 200);
              in = scanner.next();
            }
            int result = rollDice(player);
            PrinterHelper.printPlayerMessage("Risultato: " + result+"\n", player, false, 100);

            if (result > max) 
            {
                max = result;
                winner = player;
                sameResultPlayers.clear();
            } 
            else if (result == max) 
            {
                if (!sameResultPlayers.contains(winner)) sameResultPlayers.add(winner);
                sameResultPlayers.add(player);
            }
        }

        manageSetFirstPlayerParityCase(sameResultPlayers, scanner);

        if (!sameResultPlayers.isEmpty()) winner = sameResultPlayers.getFirst();

        GameState.getInstance().setMainPlayer(winner);
        PrinterHelper.printSystemMessage("Perfetto, "+ winner.getName() +" sará il primo a giocare!", false, false, 200);

        isFirstPlayerSet = true;

    }

    private void runSetPlayersGoals()
    {
        PrinterHelper.printSystemMessage("Consegno gli obbiettivi. . .", false, 800);
        var goalList = GoalData.getGoals();
        for (Player player : GameState.getInstance().getPlayers())
        {
            if(!GameState.getInstance().isTestGame())
            {
                Collections.shuffle(goalList);
                player.setGoal(goalList.getFirst());
            }else player.setGoal(GoalData.testGoal());
            PrinterHelper.printSystemMessage("Obbiettivo assegnato a "+player.getName(), false, 700);
        }

        PrinterHelper.printSystemMessage("Tutti gli obbiettivi sono stati consegnati.\nRicorda: per vedere l'obbiettivo, digita 'obbiettivo' durante il tuo turno!", false, 200);
        arePlayersGoalsSet = true;
    }

    private int rollDice(Player player)
    {
        PrinterHelper.printPlayerMessage("Lancio del dado...", player, false, 300);
        PrinterHelper.printPlayerMessage("...", player, false, 1000);
        return player.rollDices();
    }

    private boolean containArmyColor(String in)
    {
        if (in == null || in.isEmpty()) return false;
        for (ArmyColor color : ArmyColor.values())
            if (Character.toUpperCase(ArmyColorTranslation.GetFirstChar(color)) == Character.toUpperCase(in.charAt(0))) return true;

        return false;
    }

    private void manageSetFirstPlayerParityCase(List<Player> sameResultPlayers, Scanner scanner)
    {
        while (sameResultPlayers.size() > 1)
        {
            PrinterHelper.printSystemMessage("Ci sono dei giocatori con pari punteggio", false, 200);
            List<Player> temp = new ArrayList<>(sameResultPlayers);
            sameResultPlayers.clear();
            int maxTie = 0;

            for (Player player : temp)
            {
                PrinterHelper.printPlayerMessage(player.getName() + " tira nuovamente un dado premendo un tasto qualsiasi e poi premendo invio", player, false, 200);
                var in = scanner.next();
                while(inputToCommandForward.isCommand(in).isPresent())
                {
                    inputToCommandForward.tryForwardAsCommand(in);
                    PrinterHelper.printPlayerMessage(player.getName() + " tira nuovamente un dado premendo un tasto qualsiasi e poi premendo invio", player, false, 200);
                    in = scanner.next();
                }
                int value = rollDice(player);
                PrinterHelper.printPlayerMessage("Risultato: " + value+"\n", player, false, 100);

                if (value > maxTie)
                {
                    maxTie = value;
                    sameResultPlayers.clear();
                    sameResultPlayers.add(player);
                }
                else if (value == maxTie) sameResultPlayers.add(player);
            }
        }
    }
}
