package risiko.model.phases.steps;

import risiko.data.TerritoryDefinition;
import risiko.helper.InputTypeHelper;
import risiko.helper.PrinterHelper;
import risiko.infrastructure.InputToCommandForward;
import risiko.model.GameState;
import risiko.model.BorderTerritoriesCalculation;
import risiko.model.Territory;

import java.util.HashMap;
import java.util.Scanner;

public class ArmiesMoveStep implements GameStep, ResettableStep
{
    private boolean isMovingStepFinished;

    @Override
    public void run()
    {
        var inputToCommandForward = InputToCommandForward.getInstance();
        Scanner scanner = new Scanner(System.in);
        var player = GameState.getInstance().getCurrentPlayer();
        var in = "";
        while(!in.equalsIgnoreCase("SI") && !in.equalsIgnoreCase("NO"))
        {
            PrinterHelper.printPlayerMessage(player.getName()+" vuoi spostare delle truppe? [SI] [NO]", player, true,  1000);
            var i = scanner.next();
            inputToCommandForward.tryForwardAsCommand(i);
            in = i;
        }
        if(in.equalsIgnoreCase("SI"))
        {
            in = "";
            HashMap<Integer, Territory> from = new HashMap<>();
            for (Territory territory : BorderTerritoriesCalculation.calculateValidBorderForMovingArmiesFrom(player))
                from.put(territory.getId(), territory);

            while(!InputTypeHelper.isInt(in) || !from.containsKey(Integer.parseInt(in)))
            {
                PrinterHelper.printPlayerMessage("Da quale territorio intendi spostare le armate?", player, true,  1000);
                for(Territory territory : from.values()) PrinterHelper.printPlayerMessage(territory.getId()+" -> "+territory.getName()+"; armate correnti: "+territory.getArmiesCount(), player, false,  75);
                PrinterHelper.printTip();
                in = scanner.next();
                inputToCommandForward.tryForwardAsCommand(in);
            }


            HashMap<Integer, TerritoryDefinition> to = new HashMap<>();
            for (TerritoryDefinition territory : BorderTerritoriesCalculation.calculateValidBorderForMovingArmiesTo(player, from.get(Integer.parseInt(in))))
                to.put(territory.getId(), territory);

            var in2 = "";
            while(!InputTypeHelper.isInt(in2) || !to.containsKey(Integer.parseInt(in2)))
            {
                PrinterHelper.printPlayerMessage("Verso quale quale territorio?", player, true,  1000);
                for(TerritoryDefinition territory : to.values())
                    PrinterHelper.printPlayerMessage(territory.getId()+" -> "+territory.getName()+"; armate correnti: "+player.getTerritories().stream().filter(el -> el.getId() == territory.getId()).toList().getFirst().getArmiesCount(), player, false,  75);
                PrinterHelper.printTip();
                in2 = scanner.next();
                inputToCommandForward.tryForwardAsCommand(in2);
            }

            int n = 0;
            var armiesLimit = from.get(Integer.parseInt(in)).getArmiesCount() - 1;
            while(n <= 0 || n > armiesLimit)
            {
                PrinterHelper.printPlayerMessage("Quante armate?", player, true,  1000);
                var i = scanner.next();
                inputToCommandForward.tryForwardAsCommand(i);
                if(InputTypeHelper.isInt(i)) n = Integer.parseInt(i);
            }

            PrinterHelper.printPlayerMessage("Perfetto!", player, false,  1000);

            String finalIn2 = in2;
            player.move(n, from.get(Integer.parseInt(in)), player.getTerritories().stream().filter(el -> el.getId() == to.get(Integer.parseInt(finalIn2)).getId()).toList().getFirst());
        }
        isMovingStepFinished = true;
    }

    @Override
    public boolean isCompleted()
    {
        return isMovingStepFinished;
    }

    @Override
    public void reset()
    {
        isMovingStepFinished = false;
    }
}
