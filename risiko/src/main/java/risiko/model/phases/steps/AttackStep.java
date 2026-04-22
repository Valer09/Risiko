package risiko.model.phases.steps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.TerritoryDefinition;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;
import risiko.infrastructure.InputToCommandForward;
import risiko.infrastructure.VictoryException;
import risiko.model.*;
import risiko.model.*;

import java.util.*;

public class AttackStep implements GameStep, ResettableStep
{
    private final InputToCommandForward inputToCommandForward;
    @JsonProperty("isCompleted")
    private boolean isCompleted;

    public AttackStep()
    {
        inputToCommandForward = InputToCommandForward.getInstance();
    }

    @JsonCreator
    public AttackStep(@JsonProperty("isCompleted") boolean isCompleted)
    {
        this.isCompleted = isCompleted;
        inputToCommandForward = InputToCommandForward.getInstance();
    }

    @Override
    public void run()
    {
        if(!isCompleted)
        {
            var player = GameState.getInstance().getCurrentPlayer();
            runAttack(player);
        }
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public void reset()
    {
        isCompleted = false;
    }

    private void runAttack(Player player)
    {
        Scanner scanner = new Scanner(System.in);
        var addictionalWord = "";

        while(!isCompleted)
        {
            if(!canAttack(player))
            {
                PrinterHelper.printPlayerMessage(player.getName() +" al momento non con le armate e territori che possiedi non puoi effettuare alcun attacco.", player, true, 200);
                isCompleted = true;
                return;
            }

            String userInput = "";
            while (!userInput.equalsIgnoreCase("NO") && !userInput.equalsIgnoreCase("SI"))
            {
                PrinterHelper.printPlayerMessage(String.format("%s Vuoi attaccare%s? [SI] [NO]", player.getName(), addictionalWord), player, true, 200);
                var in = scanner.next();
                if (inputToCommandForward.isCommand(in).isPresent()) inputToCommandForward.tryForwardAsCommand(in);
                else if (!InputTypeHelper.isInt(in)) userInput = in;
            }
            if (userInput.equalsIgnoreCase("NO"))
            {
                isCompleted = true;
                if(!addictionalWord.isEmpty()) PrinterHelper.printPlayerAction(player.getName()+" ha completato la sua fase di attacco", player, false, 400);
                return;
            }

            var userInput2 = "";
            HashMap<Integer, Territory> userTerritory = new HashMap<>();


            for (Territory territory : BorderTerritoriesCalculation.calculateValidAttackerTerritories(player))
                userTerritory.put(territory.getId(), territory);

            while (!InputTypeHelper.isInt(userInput2) || !userTerritory.containsKey(Integer.parseInt(userInput2)))
            {
                PrinterHelper.printPlayerMessage("Da quale territorio vuoi attaccare?", player, false, 200);
                for (Territory territory : userTerritory.values())
                    PrinterHelper.printPlayerMessage(territory.getId() + " -> " + territory.getName()+"; armate -> "+territory.getArmiesCount(), player, false, 10);
                PrinterHelper.printTip();

                var in = scanner.next();
                if (inputToCommandForward.isCommand(in).isPresent()) inputToCommandForward.tryForwardAsCommand(in);
                else if (InputTypeHelper.isInt(in)) userInput2 = in;
            }

            var attackerTerritory = userTerritory.get(Integer.parseInt(userInput2));
            userInput2 = "";

            HashMap<Integer, TerritoryDefinition> borderTerritories = new HashMap<>();

            for (TerritoryDefinition territory : BorderTerritoriesCalculation.calculateValidTerritoriesToAttack(player, attackerTerritory))
                borderTerritories.put(territory.getId(), territory);

            while (!InputTypeHelper.isInt(userInput2) || !borderTerritories.containsKey(Integer.parseInt(userInput2)))
            {
                PrinterHelper.printPlayerMessage("Quale territorio vuoi attaccare?", player, false, 200);
                for (TerritoryDefinition territory : borderTerritories.values())
                    PrinterHelper.printPlayerMessage(territory.getId() + " -> " + territory.getName(), player, false, 10);
                PrinterHelper.printTip();

                var in = scanner.next();
                if (inputToCommandForward.isCommand(in).isPresent()) inputToCommandForward.tryForwardAsCommand(in);
                else if (InputTypeHelper.isInt(in)) userInput2 = in;
            }

            runSingleAttack(player, attackerTerritory, borderTerritories.get(Integer.parseInt(userInput2)));

            addictionalWord = " ancora";
        }
    }

    private void runSingleAttack(Player player, Territory attackerTerritory, TerritoryDefinition attackedTerritory)
    {
        Scanner scanner = new Scanner(System.in);
        var concluded = false;
        Optional<Player> defendingPlayerOpt = GameState.getInstance().findTerritoryOwner(attackedTerritory);
        if(defendingPlayerOpt.isEmpty()) throw new RuntimeException("Il territorio attaccato non é in possesso da alcun giocatore!");
        while(!concluded)
        {
            if(attackerTerritory.getArmiesCount() < 2)
            {
                PrinterHelper.printPlayerAction(player.getName()+" ha esaurito le armate per attaccare dal territorio "+attackerTerritory.getName(), player, false, 400);
                return;
            }
            var in = "";

            var max = Math.min(attackerTerritory.getArmiesCount() - 1, 3);
            while(in.isEmpty() || !InputTypeHelper.isInt(in) || Integer.parseInt(in) > max || Integer.parseInt(in) < 1)
            {
                PrinterHelper.printPlayerMessage("Seleziona il numero di armate: "+"(massimo "+max+")" , player, false, 200);
                in = scanner.next();
                if(inputToCommandForward.isCommand(in).isPresent()) inputToCommandForward.tryForwardAsCommand(in);
            }

            var defendingTerritory = defendingPlayerOpt.get().getTerritories().stream().filter(el -> el.getId() == attackedTerritory.getId()).findFirst().get();
            int attackArmiesCount = Integer.parseInt(in);
            int defendArmiesCount = Math.min(defendingTerritory.getArmiesCount(), attackArmiesCount);
            var defendingPlayer = defendingPlayerOpt.get();
            PrinterHelper.printPlayerAction(player.getName()+" attacca violentemente "+defendingPlayer.getName()+" da "+attackerTerritory.getName()+" a "+attackedTerritory.getName()+ " con "+attackArmiesCount+" armate" , player, false, 200);

            List<Integer> attackerResults = new ArrayList<>();
            List<Integer> defenderResults = new ArrayList<>();

            lunchDicesAndGetResults(scanner, attackArmiesCount, player, attackerResults);

            PrinterHelper.printPlayerMessage(defendingPlayer.getName()+" hai "+defendArmiesCount+" armate per difendere", defendingPlayer, false, 200);

            lunchDicesAndGetResults(scanner, defendArmiesCount, defendingPlayer, defenderResults);

            var attackerLoses = 0;
            var defenderLoses = 0;

            attackerLoses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
            defenderLoses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

            if(attackerLoses > 0)
            {
                PrinterHelper.printPlayerAction(player.getName()+" perde "+attackerLoses+" truppe", player, false, 350);
                player.removeArmiesFromTerritory(attackerLoses, attackerTerritory);
            }
            if(defenderLoses > 0)
            {
                PrinterHelper.printPlayerAction(defendingPlayer.getName()+" perde "+defenderLoses+" truppe", defendingPlayer, false, 350);
                defendingPlayer.removeArmiesFromTerritory(defenderLoses, defendingTerritory);
            }

            if(defendingTerritory.getArmiesCount() == 0)
            {
                PrinterHelper.printPlayerAction(defendingPlayer.getName()+" perde il controllo di "+defendingTerritory.getName(), defendingPlayer, false, 650);
                defendingPlayer.removeTerritory(defendingTerritory);
                PrinterHelper.printPlayerAction(player.getName()+" conquista "+defendingTerritory.getName(), player, false, 650);
                player.addTerritory(defendingTerritory);
                player.move(attackArmiesCount - attackerLoses, attackerTerritory, defendingTerritory);
                PrinterHelper.printPlayerMessage(player.getName()+" ha diritto ad una carta dal mazzo. . .", defendingPlayer, false, 1000);
                player.addCard(GameState.getInstance().getDeck().takeOne());
                concluded = true;
                if(GameState.getInstance().isTestGame()) throw new VictoryException(player);
            }

            if(PlayerGoalCheck.check(player)) throw new VictoryException(player);
            if(PlayerGoalCheck.check(defendingPlayer)) throw new VictoryException(defendingPlayer);

            var inp = "";
            while(!concluded && attackerTerritory.getArmiesCount() > 1 && !inp.equalsIgnoreCase("SI") && !inp.equalsIgnoreCase("NO"))
            {
                PrinterHelper.printPlayerMessage(player.getName()+" vuoi continuare l'attacco da "+attackerTerritory.getName()+" a "+attackedTerritory.getName()+"? [SI] [NO]", player, false, 1000);
                inp = scanner.next();
                if(inputToCommandForward.isCommand(inp).isPresent()) inputToCommandForward.tryForwardAsCommand(inp);
            }
            if(inp.equalsIgnoreCase("NO") ) concluded = true;
        }
    }

    private void lunchDicesAndGetResults(Scanner scanner, int defendArmiesCount, Player defendingPlayer, List<Integer> defenderResults) {
        for(int i = 1; i <= defendArmiesCount; i++)
        {
            while (true)
            {
                PrinterHelper.printPlayerMessage(defendingPlayer.getName()+" inserisci un tasto qualsiasi per lanciare il "+i+"o dado", defendingPlayer, false, 200);
                var x = scanner.next();
                if(inputToCommandForward.isCommand(x).isPresent()) inputToCommandForward.tryForwardAsCommand(x);
                else
                {
                    defenderResults.add(defendingPlayer.rollDices());
                    break;
                }
            }
        }
    }

    private boolean canAttack(Player player)
    {
        return !BorderTerritoriesCalculation.calculateValidAttackerTerritories(player).isEmpty();
    }
}
