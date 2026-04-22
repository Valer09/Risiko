# Risiko

Java console implementation of the classic **Risk** board game. Features turn-based play with multiple players, territory/continent logic, attack-dice resolution, card combinations, secret goals, and a command-based console UI with save/load support.

## Learning goal

Practise **Java** and the **Maven** build system on a non-trivial project — applying OOP design, a small command pattern for navigation, JSON serialization (Jackson) for save games, and clean separation between model, infrastructure and I/O.

## Tech stack

- **Language:** Java
- **Build:** Maven
- **Serialization:** Jackson (save / load game state)
- **UI:** text-based console

## Project structure

```
risiko/
├── pom.xml
└── src/main/java/risikounivaq/it/
    ├── Runner.java               # entry point
    ├── data/                     # territory/continent/goal definitions, storage
    ├── model/                    # game model (Territory, Continent, Card, attack logic, ...)
    ├── infrastructure/           # command pattern: new/load/save game, show map, help, ...
    └── helper/                   # input parsing, printing, color translation
```

## Build & run

```bash
cd risiko
mvn package
java -jar target/<artifact>.jar
```

Once running, type `help` in the console to see the available commands (new game, load, save, show map, end turn, etc.).
