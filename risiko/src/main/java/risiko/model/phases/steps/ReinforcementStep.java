package risiko.model.phases.steps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.infrastructure.VictoryException;
import risiko.model.*;
import risiko.model.*;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;
import risiko.infrastructure.InputToCommandForward;

import java.util.*;
import java.util.Map;

public class ReinforcementStep implements GameStep, ResettableStep
{
    private final InputToCommandForward inputToCommandForward;
    private PlayerReinforcement playerReinforcement;
    @JsonProperty("reinforcementCompleted")
    private boolean reinforcementCompleted;

    @JsonCreator
    public ReinforcementStep(@JsonProperty("reinforcementCompleted") boolean reinforcementCompleted)
    {
        this.reinforcementCompleted = reinforcementCompleted;
        this.inputToCommandForward = InputToCommandForward.getInstance();
    }

    public ReinforcementStep()
    {
        this.inputToCommandForward = InputToCommandForward.getInstance();
    }

    @Override
    public void run()
    {
        if(!reinforcementCompleted)
        {
            playerReinforcement = new PlayerReinforcement(GameState.getInstance().getCurrentPlayer());
            if(GameState.getInstance().isReinforcementCompletedForCurrentPlayer())
            {
                reinforcementCompleted = true;
            }
            else
            {
                if(GameState.getInstance().getReinforcementForPlayer() > 0)
                    runReinforcementPositioning(GameState.getInstance().getReinforcementForPlayer());
                else runReinforcementPositioning(calculateReinforcement());
            }
        }
    }

    @Override
    public boolean isCompleted()
    {
        return reinforcementCompleted;
    }

    @Override
    public void reset()
    {
        reinforcementCompleted = false;
    }

    private int calculateReinforcement()
    {
        Scanner scanner = new Scanner(System.in);
        Player player = playerReinforcement.getPlayer();
        PrinterHelper.printPlayerMessage(player.getName()+" é il momento di rinforzare le tue armate!\n", player, false, 1000);

        PlayerReinforcementCalculation calculator = new PlayerReinforcementCalculation();
        var additionalContinentArmiesCount = playerReinforcement.getAdditionalContinentArmiesCount();

        var basicReinforce = playerReinforcement.getBasicReinforce();
        calculator.addBasicArmies(basicReinforce);
        var continentReinforce = calculator.addContinentsAndGetContinentsValue(additionalContinentArmiesCount);

        PrinterHelper.printPlayerAction(String.format("%s guadagna %d truppe per il possesso di %d territori",player.getName(), basicReinforce, player.getTerritories().size()), player, false, 500);
        if(!additionalContinentArmiesCount.isEmpty())
        {
            PrinterHelper.printPlayerAction(String.format("%s guadagna %d truppe aggiuntive per il possesso di %d continenti: ",player.getName(), continentReinforce, additionalContinentArmiesCount.size()), player, false, 500);
            for(Continent cont : additionalContinentArmiesCount)
                PrinterHelper.printPlayerAction(String.format("%d truppe aggiuntive per %s", cont.getArmiesValue(), cont.getName()), player, false, 500);
        }

        var in = "";
        while(!in.equals("N") && playerReinforcement.hasCardCombinations())
        {
            PrinterHelper.printPlayerMessage("Ci sono carte che puoi giocare. Vuoi utilizzarle? [S] [N]", player, false, 500);
            while(true)
            {
                in = scanner.next();
                if(Objects.equals(in, "S") || Objects.equals(in, "N")) break;
                inputToCommandForward.tryForwardAsCommand(in);
            }

            if(Objects.equals(in, "S"))
            {
                var combinations = playerReinforcement.getAllCombinations();
                PrinterHelper.printPlayerMessage("Quale combinazione di carte vuoi giocare?", player, false, 500);

                Set<Integer> combinationIds = new HashSet<>();
                String in2;
                while(true)
                {
                    for(Combination combination : combinations)
                    {
                        PrinterHelper.printPlayerMessage(combination.getCombinationType().getId() + " per " + combination.getCombinationType().getDescription(), player, false, 500);
                        combinationIds.add(combination.getCombinationType().getId());
                    }
                    in2 = scanner.next();
                    if(!InputTypeHelper.isInt(in2))
                    {
                        inputToCommandForward.tryForwardAsCommand(in2);
                        continue;
                    }
                    if(InputTypeHelper.isInt(in2) && combinationIds.contains(Integer.parseInt(in2))) break;
                }
                String finalIn = in2;
                var selectedCombination = combinations.stream().filter(el -> el.getCombinationType().getId() == Integer.parseInt(finalIn)).findFirst().get();
                PrinterHelper.printPlayerMessage("Ottima scelta!", player, false, 500);

                player.playCardsCombination(selectedCombination);
                playerReinforcement.load();

                PrinterHelper.printPlayerAction(player.getName()+" aggiunge "+calculator.sumCombinationAndGetValue(selectedCombination)+" alle armate di rinforzo", player, false, 500);
            }
        }
        return calculator.getTotal();
    }

    private void runReinforcementPositioning(int reinforcementArmies)
    {
        Player player = playerReinforcement.getPlayer();

        if(reinforcementArmies > 0)
        {
            PrinterHelper.printPlayerAction(String.format("%s riceve un totale di %d armate di rinforzo", player.getName(), reinforcementArmies), player, false, 1000);
            PrinterHelper.printPlayerMessage(String.format("\n%s decidi dove posizionare le tue armate\n", player.getName()), player, false, 500);
            PrinterHelper.printPlayerMessage("Per ogni territorio, inserisci il numero di armate da spostare nel formato [X] N", player, false, 500);
            PrinterHelper.printPlayerMessage("Per esempio A 5 posizionerá 5 armate nel territorio in tuo possesso contrassegnato con la A\n", player, false, 1500);
            Scanner scanner = new Scanner(System.in);
            var map = new HashMap<Integer, Territory>();
            var territories = player.getTerritories();
            var count = reinforcementArmies;
            char c = 65;
            var inT = "";
            int inV;

            for(Territory territory : territories) map.put((int) c++, territory);

            while(count > 0 )
            {
                inT = "";
                inV = 0;

                while(inT.isEmpty() || !Character.isLetter(inT.charAt(0)) || !map.containsKey((int)inT.charAt(0)) || inV <=0  )
                {
                    for(Map.Entry<Integer, Territory> entry : map.entrySet())
                    {
                        Integer key = entry.getKey();
                        Territory value = entry.getValue();
                        char ch = (char) key.intValue();
                        PrinterHelper.printPlayerMessage(ch+" -> " + value.getName() + "; armate attuali -> " + value.getArmiesCount(), player, false, 50);
                    }
                    PrinterHelper.ahead();
                    PrinterHelper.printPlayerMessage("Rinforzi rimanenti -> "+count, player, false, 500);
                    PrinterHelper.printTip();
                    var input = scanner.nextLine();
                    if(inputToCommandForward.isCommand(input).isPresent())
                    {
                        inputToCommandForward.tryForwardAsCommand(input);
                        continue;
                    }
                    String[] parts = input.split(" ", 2);
                    if(parts.length != 2) continue;
                    if((parts[0].length() == 1 && Character.isLetter(parts[0].charAt(0)))  && InputTypeHelper.isInt(parts[1]))
                    {
                        if(count - Integer.parseInt(parts[1]) < 0)
                        {
                            PrinterHelper.printPlayerAction("Hai dispobibili soltanto "+count+" armate ancora", player, false, 500);
                            continue;
                        }

                        inT = parts[0];
                        inV = Integer.parseInt(parts[1]);

                    }else PrinterHelper.printPlayerAction("Inserimento errato", player, false, 500);
                }
                player.addArmiesToTerritory(inV, map.get((int)(inT.charAt(0))));
                count -= inV;
                GameState.getInstance().updateReinforcementForPlayer(count);
            }
        }
        GameState.getInstance().completeReinforcementForPlayer();
        reinforcementCompleted = true;
        if(PlayerGoalCheck.check(player)) throw new VictoryException(player);
    }
}
