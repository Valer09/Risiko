package risiko.data;
import com.fasterxml.jackson.databind.ObjectMapper;
import risiko.model.GameState;
import risiko.infrastructure.JacksonFactory;
import risiko.helper.PrinterHelper;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GameStorage
{
    public static final String GAME_DATA_FILENAME = "gameData.json";
    private static final String savedGamesFolderName = "saved_games";
    private static final Path storagePath = Paths.get("data");
    private static final Path savedGamesFolder = storagePath.resolve(savedGamesFolderName);

    public static boolean saveGame(String folderName, GameState gameState)
    {
        createFolderIfNotExists();
        ObjectMapper objectMapper = JacksonFactory.getJackson();

        if(Files.exists(storagePath.resolve(savedGamesFolderName).resolve(folderName)))
        {
            PrinterHelper.printSystemMessage("Esiste giá una partita salvata con questo nome", false);
            return false;
        }
        try
        {
            Files.createDirectories(storagePath.resolve(savedGamesFolderName).resolve(folderName));
            Path jsonFile = storagePath.resolve(savedGamesFolderName).resolve(folderName).resolve(GAME_DATA_FILENAME);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile.toFile(), gameState);

            Files.copy(ActionsTrackingIO.getTrackingFilePath(), storagePath.resolve(savedGamesFolderName).resolve(folderName).resolve(ActionsTrackingIO.getTrackingFileName()));

            return true;
        }catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public static GameState loadGame(String gameName)
    {
        createFolderIfNotExists();

        ObjectMapper objectMapper = JacksonFactory.getJackson();
        Path jsonFilePath = storagePath.resolve(savedGamesFolderName).resolve(gameName).resolve(GAME_DATA_FILENAME);
        try
        {
            String jsonContent = Files.readString(jsonFilePath);
            return objectMapper.readValue(jsonContent, GameState.class);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public static List<String> getSavedGameNamesList()
    {
        List<String> subfolders = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(savedGamesFolder))
        {
            for (Path entry : stream)
                if (Files.isDirectory(entry)) subfolders.add(entry.getFileName().toString());
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }

        return subfolders;
    }

    private static void createFolderIfNotExists()
    {
        try
        {
            if (Files.notExists(storagePath))
            {
                Files.createDirectories(storagePath);
                Files.createDirectories(savedGamesFolder);
            }
        }catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public static Path getTrackingFileForSavedGame(String gameName)
    {
        return storagePath.resolve(savedGamesFolderName).resolve(gameName).resolve(ActionsTrackingIO.getTrackingFileName());
    }
}
