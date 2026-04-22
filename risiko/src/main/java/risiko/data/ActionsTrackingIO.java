package risiko.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ActionsTrackingIO
{

    private static final Path storagePath = Paths.get("data");
    public static final String FILE = "gameActions.txt";

    public static void writeActionToTrackingFile(String actionString)
    {
        Path filePath = getTrackingFilePath();

        if (!Files.exists(storagePath)) createDir();
        if (!Files.exists(getTrackingFilePath())) createFile();

        try
        {
            Files.writeString(filePath, "\n"+actionString+"\n", StandardOpenOption.APPEND);
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile scrivere sul file di tracciamento azioni a causa di un errore");
            throw new RuntimeException(ex);
        }
    }

    public static Path getTrackingFilePath()
    {
        return storagePath.resolve(FILE);
    }

    public static Path getTrackingFileName()
    {
        return Paths.get(FILE);
    }

    public static void createNewTrackingFile()
    {
        if (!Files.exists(storagePath)) createDir();
        if (!Files.exists(getTrackingFilePath())) createFile();
        else cleanFile();
    }

    private static void cleanFile()
    {
        try
        {
            Files.delete(getTrackingFilePath());
            createFile();
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile pulire il file di tracciamento a causa di un errore");
            ex.printStackTrace();
        }
    }

    private static void createDir()
    {
        try
        {
            Files.createDirectories(storagePath);
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile creare la cartella data a causa di un errore");
            throw new RuntimeException(ex);
        }
    }

    private static void createFile()
    {
        Path filePath = getTrackingFilePath();
        try
        {
            Files.createFile(filePath);
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile creare il file di tracciamento azioni a causa di un errore");
            throw new RuntimeException(ex);
        }
    }

    public static void loadTrackingFileFromSavedGame(String gameName)
    {
        try
        {
            if (Files.exists(storagePath) && Files.exists(getTrackingFilePath())) Files.delete(getTrackingFilePath());
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile eliminare il file di tracciamento a causa di un errore");
            ex.printStackTrace();
        }

        try
        {
            Files.copy(GameStorage.getTrackingFileForSavedGame(gameName), getTrackingFilePath());
        }
        catch (IOException ex)
        {
            System.out.println("Impossibile compiare il file di tracciamento dalla partita salvata a causa di un errore");
            ex.printStackTrace();
        }
    }
}
