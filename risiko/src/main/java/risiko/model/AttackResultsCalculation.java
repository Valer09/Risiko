package risiko.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackResultsCalculation
{

    public static int getAttackerLoose(List<Integer> attackerResults, List<Integer> defenderResults)
    {
        List<Integer> attackerResultSorted = new ArrayList<>(attackerResults);
        List<Integer> defenderResultsSorted = new ArrayList<>(defenderResults);
        Collections.sort(attackerResultSorted);
        Collections.sort(defenderResultsSorted);

        var attackerLoses = 0;
        while(!attackerResultSorted.isEmpty() && !defenderResultsSorted.isEmpty())
        {
            var att = attackerResultSorted.getLast();
            var def = defenderResultsSorted.getLast();
            attackerResultSorted.removeLast();
            defenderResultsSorted.removeLast();

            if(def >= att) attackerLoses++;
        }
        return attackerLoses;
    }

    public static int getDefenderLoose(List<Integer> attackerResults, List<Integer> defenderResults)
    {
        List<Integer> attackerResultSorted = new ArrayList<>(attackerResults);
        List<Integer> defenderResultsSorted = new ArrayList<>(defenderResults);
        Collections.sort(attackerResultSorted);
        Collections.sort(defenderResultsSorted);

        var defenderLoses = 0;
        while(!attackerResultSorted.isEmpty() && !defenderResultsSorted.isEmpty())
        {
            var att = attackerResultSorted.getLast();
            var def = defenderResultsSorted.getLast();
            attackerResultSorted.removeLast();
            defenderResultsSorted.removeLast();

            if (def < att) defenderLoses++;
        }
        return defenderLoses;
    }
}
