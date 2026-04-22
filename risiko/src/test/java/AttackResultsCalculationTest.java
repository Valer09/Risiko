import org.junit.jupiter.api.Test;
import risiko.model.AttackResultsCalculation;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackResultsCalculationTest
{

    @Test
    void test3v3()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(6, 6, 6));
        List<Integer> defenderResults = new LinkedList<>(List.of(1, 1, 1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 0 == attackerLosses;
        assert 3 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(1, 1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(1 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(1, 6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(3 ,attackerLosses);
        assertEquals(0 ,defenderLosses);

        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(6, 6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(3 ,attackerLosses);
        assertEquals(0 ,defenderLosses);
    }

    @Test
    void test3v2()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(6, 6, 6));
        List<Integer> defenderResults = new LinkedList<>(List.of(1, 1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(0 ,attackerLosses);
        assertEquals(2 ,defenderLosses);


        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(0 ,attackerLosses);
        assertEquals(2 ,defenderLosses);


        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(1, 5));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(0 ,attackerLosses);
        assertEquals(2 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(1 ,attackerLosses);
        assertEquals(1 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(1 ,attackerLosses);
        assertEquals(1 ,defenderLosses);




        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(6, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(2 ,attackerLosses);
        assertEquals(0 ,defenderLosses);
    }


    @Test
    void test3v1()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(6, 6, 6));
        List<Integer> defenderResults = new LinkedList<>(List.of(1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(0 ,attackerLosses);
        assertEquals(1 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 6, 6));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(1 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(0 ,attackerLosses);
        assertEquals(1 ,defenderLosses);


        attackerResults = new LinkedList<>(List.of(6, 6, 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(1 ,attackerLosses);
        assertEquals(0 ,defenderLosses);


        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(0 ,attackerLosses);
        assertEquals(1 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(6, 1, 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);


        assertEquals(1 ,attackerLosses);
        assertEquals(0 ,defenderLosses);




        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(1 ,attackerLosses);
        assertEquals(0 ,defenderLosses);



        attackerResults = new LinkedList<>(List.of(1, 1, 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assertEquals(1 ,attackerLosses);
        assertEquals(0 ,defenderLosses);
    }



    @Test
    void test2v2()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(6, 6));
        List<Integer> defenderResults = new LinkedList<>(List.of(1, 1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 0 == attackerLosses;
        assert 2 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 6));
        defenderResults = new LinkedList<>(List.of(1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 1 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 6));
        defenderResults = new LinkedList<>(List.of(6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 1 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(6, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;




        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(6, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;


        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(1, 6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(6, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(1, 1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 2 == attackerLosses;
        assert 0 == defenderLosses;
    }




    @Test
    void test2v1()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(6, 6));
        List<Integer> defenderResults = new LinkedList<>(List.of(1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 0 == attackerLosses;
        assert 1 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 6));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6, 1));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 0 == attackerLosses;
        assert 1 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(1, 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;

    }



    @Test
    void test1v1()
    {
        // Attaccante con dadi migliori
        List<Integer> attackerResults = new LinkedList<>(List.of(1));
        List<Integer> defenderResults = new LinkedList<>(List.of(1));

        int attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        int defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;



        attackerResults = new LinkedList<>(List.of(6));
        defenderResults = new LinkedList<>(List.of(1));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 0 == attackerLosses;
        assertEquals(1, defenderLosses);



        attackerResults = new LinkedList<>(List.of( 1));
        defenderResults = new LinkedList<>(List.of(6));

        attackerLosses = AttackResultsCalculation.getAttackerLoose(attackerResults, defenderResults);
        defenderLosses = AttackResultsCalculation.getDefenderLoose(attackerResults, defenderResults);

        assert 1 == attackerLosses;
        assert 0 == defenderLosses;
    }
}
