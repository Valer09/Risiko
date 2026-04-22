package risiko.data;

import risiko.model.PlayerGoal;

import java.util.Arrays;
import java.util.List;

public class GoalData
{
    public static List<PlayerGoal> getGoals()
    {
        return Arrays.asList
                (
                    new PlayerGoal(1,"Conquista 18 territori presidiandoli con almeno due armate ciascuno")
                    {
                    },
                    new PlayerGoal(2,"Conquista 24 territori")
                    {
                    },
                    new PlayerGoal(3,"Conquista la totalità del Nord America e dell'Africa")
                    {
                    },
                    new PlayerGoal(4,"Conquista la totalità del Nord America e dell'Oceania")
                    {
                    },
                    new PlayerGoal(5,"Conquista la totalità dell'Asia e del Sud America")
                    {
                    },
                    new PlayerGoal(6,"Conquista la totalità dell'Asia e dell'Africa")
                    {
                    },
                    new PlayerGoal(7, "Conquista la totalità dell'Europa, del Sud America e di un terzo continente a scelta")
                    {
                    },
                    new PlayerGoal(8,"Conquista la totalità dell'Europa, dell'Oceania e di un terzo continente a scelta")
                    {
                    },
                    new PlayerGoal(9, "Distruggi completamente l'armata blu. \nSe le armate blu non sono presenti nel gioco, se le armate blu sono tue o se l'ultima armata viene distrutta da un altro giocatore, \nl'obiettivo diventa conquistare 24 territori")
                    {
                    }
                );
    }

    public static PlayerGoal testGoal()
    {
        return new PlayerGoal(99, "Conquista un territorio");
    }
}
