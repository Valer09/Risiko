package risiko;

import risiko.infrastructure.*;
import risiko.helper.PrinterHelper;
import risiko.it.infrastructure.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {


	private final RunnerCallback callback;
    private final InputToCommandForward inputToCommandForward;
	public Runner()
	{
		callback = new RunnerCallback()
		{
			@Override
			public void createNewGameCallback() {
				run(true);
			}

			@Override
			public void loadGameCallback() {
				run(false);
			}
		};
		inputToCommandForward = InputToCommandForward.createInstance(callback);
	}

	public void run(boolean isNewGame)
	{
		GameManager gameManager = new GameManager(inputToCommandForward, callback);
		try
		{
			if(isNewGame) gameManager.runNewGame();
			else gameManager.loadGame();
		}
		catch(InterruptedGameException exception)
		{
			System.out.println("Gioco interrotto");
			System.out.println("Eseguo il nuovo comando");
			exception.executeCommand();
		}
		catch(VictoryException exception)
		{
			exception.runVictoryMessage();
			System.exit(0);
		}
	}

	public static void main(String[] args)
	{
		PrinterHelper.printSystemMessage("Benvenuti in Risiko!", false, 1500);
		Scanner scanner = new Scanner(System.in);

		int userChoise = 0;
		while(userChoise != 1 && userChoise != 2 && userChoise != 3)
		{
			PrinterHelper.printSystemMessage("Inserisci 1 per iniziare una nuova partita, 2 per caricare una partita, 3 per terminare e premi Invio", false);
			try
			{
				userChoise = scanner.nextInt();
			}
			catch(InputMismatchException ignored)
			{
				userChoise = 0;
				scanner = new Scanner(System.in);
			}
		}
		PrinterHelper.printSystemMessage("OK. . .", true, 500);
		if(userChoise == 3)
		{
			PrinterHelper.printSystemMessage("Chiusura del gioco", false);
			System.exit(0);
		}

		Runner runner = new Runner();
		runner.run(userChoise == 1);

	}
}
