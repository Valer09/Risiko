package risiko.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import risiko.data.TerritoryDefinition;
import risiko.model.phases.CoreGamePhase;
import risiko.model.phases.GamePhase;
import risiko.model.phases.InitializationGamePhase;

import java.util.*;

public final class GameState
{
    private static GameState instance;
    private int currentPhaseIndex;
    private List<Player> players;
    private boolean hasChanged;
    @JsonProperty("deck")
    private Deck deck;
    @JsonProperty("mainPlayer")
    private Player mainPlayer;
    @JsonProperty("gamePhases")
    private List<GamePhase> gamePhases;
    @JsonProperty("currentPlayerIndex")
    private int currentPlayerIndex;
    @JsonProperty("isWon")
    private boolean isWon;
    @JsonProperty("isTestEnabled")
    private boolean isTestEnabled;
    @JsonProperty("reinforcementStates")
    private List<ReinforcementState> reinforcementStates;

    /**
     * JSON constructor
     * */
    @JsonCreator
    public GameState(
            @JsonProperty("players") List<Player> players,
            @JsonProperty("currentPhaseIndex") int currentPhaseIndex,
            @JsonProperty("deck") Deck deck,
            @JsonProperty("mainPlayer") Player mainPlayer,
            @JsonProperty("gamePhases") List<GamePhase> gamePhases,
            @JsonProperty("currentPlayerIndex") int currentPlayerIndex,
            @JsonProperty("isWon") boolean isWon,
            @JsonProperty("isTestEnabled") boolean isTestEnabled,
            @JsonProperty("reinforcementStates") List<ReinforcementState> reinforcementStates)
    {
        this.players = players;
        this.currentPhaseIndex = currentPhaseIndex;
        this.deck = deck;
        this.mainPlayer = mainPlayer;
        this.gamePhases = gamePhases;
        this.currentPlayerIndex = currentPlayerIndex;
        this.isWon = isWon;
        this.isTestEnabled = isTestEnabled;
        this.reinforcementStates = reinforcementStates;
        hasChanged = false;
    }

    private GameState(List<Player> players, boolean hasChanged, Deck deck)
    {
        this.players = players;
        this.hasChanged = hasChanged;
        this.deck = deck;
        this.gamePhases = new ArrayList<>(Arrays.asList(new InitializationGamePhase(), new CoreGamePhase()));
        currentPhaseIndex = 0;
    }

    public static void createNewState(boolean isTest)
    {
        if (instance == null)
        {
            instance = new GameState(new ArrayList<>(), true, Deck.createTerritoriesCardsForNewGame());
            if(isTest) instance.isTestEnabled = true;
            instance.reinforcementStates = new ArrayList<>();
        }
    }

    public static void loadGameState(GameState serializedGameState)
    {
        if (instance == null)
        {
            var mainPlayer = serializedGameState.getOptionalMainPlayer();
            instance = new GameState(
                    serializedGameState.getPlayers(),
                    serializedGameState.getCurrentPhaseIndex(),
                    serializedGameState.getDeck(),
                    mainPlayer.orElse(null),
                    serializedGameState.gamePhases,
                    serializedGameState.currentPlayerIndex,
                    serializedGameState.isWon,
                    serializedGameState.isTestEnabled,
                    serializedGameState.reinforcementStates);
        }
    }

    public static void cleanGameState()
    {
        instance = null;
    }

    public static GameState getInstance()
    {
        return instance;
    }

    public List<Player> getPlayers()
    {
        if (players != null) return players;
        else return new LinkedList<>();
    }

    public Deck getDeck()
    {
        return this.deck;
    }

    @JsonIgnore
    public Optional<Player> getOptionalMainPlayer()
    {
        return this.mainPlayer == null ? Optional.empty() : Optional.of(mainPlayer);
    }

    @JsonIgnore
    public String getPhasesInfo()
    {
        StringBuilder s = new StringBuilder();
        for(GamePhase gamePhase : gamePhases)
        {
            var completato = gamePhase.isCompleted() ? "Completato" : "NON completato";
            s.append("Stato della fase ").append(gamePhase.getClass().getSimpleName()).append(" ").append(completato).append("\n");
        }
        return s.toString();
    }

    @JsonIgnore
    public void setPlayers(List<Player> players)
    {
        this.players = players;
        hasChanged = true;
        setReinforcementPerPlayer(players);
    }

    public void setMainPlayer(@JsonProperty("mainPlayer")Player mainPLayer)
    {
        this.mainPlayer = players.stream().filter(el -> el.equals(mainPLayer)).findFirst().orElseThrow();
        hasChanged = true;
    }

    public void saved()
    {
        this.hasChanged = false;
    }

    @JsonIgnore
    public boolean isWon()
    {
        return this.isWon;
    }

    public void runPhases()
    {
        if(instance == null) return;

        while(currentPhaseIndex < gamePhases.size())
        {
            while(!gamePhases.get(currentPhaseIndex).isCompleted()) gamePhases.get(currentPhaseIndex).execute();
            currentPhaseIndex++;
        }
        System.exit(0);
    }

    public boolean hasChanged()
    {
        return this.hasChanged;
    }

    public void nextPlayer()
    {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }


    @JsonIgnore
    public Player getCurrentPlayer()
    {
        if(players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    @JsonIgnore
    public void setCurrentPlayerIndex(int currentPlayerIndex)
    {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public Optional<Player> findTerritoryOwner(TerritoryDefinition territory)
    {
        for(Player player : players)
            if (!player.getTerritories().stream().filter(el -> el.getId() == territory.getId()).toList().isEmpty())
                return Optional.of(player);
        return Optional.empty();
    }

    @JsonIgnore
    public boolean isTestGame()
    {
        return this.isTestEnabled;
    }
    @JsonIgnore
    public void resetReinforcementPerPlayer()
    {
        this.reinforcementStates.stream().filter(el -> el.getPlayer().equals(getCurrentPlayer())).findFirst().orElse(null).reset();
    }

    @JsonIgnore
    public void completeReinforcementForPlayer()
    {
        this.reinforcementStates.stream().filter(el -> el.getPlayer().equals(getCurrentPlayer())).findFirst().orElse(null).complete();
    }

    @JsonIgnore
    public boolean isReinforcementCompletedForCurrentPlayer()
    {
        return this.reinforcementStates.stream().filter(el -> el.getPlayer().equals(getCurrentPlayer())).findFirst().orElse(null).isCompleted();
    }

    @JsonIgnore
    public int getReinforcementForPlayer()
    {
        return this.reinforcementStates.stream().filter(el -> el.getPlayer().equals(getCurrentPlayer())).findFirst().orElse(null).getReinforcementToPlace();
    }

    @JsonIgnore
    public void updateReinforcementForPlayer(int count)
    {
        this.reinforcementStates.stream().filter(el -> el.getPlayer().equals(getCurrentPlayer())).findFirst().orElse(null).setReinforcementToPlace(count);
    }

    @JsonIgnore
    private void setReinforcementPerPlayer(List<Player> players)
    {
        for(Player player : instance.players) instance.reinforcementStates.add(new ReinforcementState(player));
    }
    
    @JsonIgnore
    private int getCurrentPhaseIndex()
    {
        return this.currentPhaseIndex;
    }
}
