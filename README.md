                                                    My First UI
# My First UI - Stick Hero Desktop Clone

A desktop clone of the popular mobile game **Stick Hero**, built with Java, JavaFX, and Scene Builder (FXML).  
Developed as a project under the guidance of Dr. Raghava Muthuraju.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Running the Game](#running-the-game)
- [Gameplay](#gameplay)
- [Saving & Progress](#saving--progress)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [Credits](#credits)
- [License](#license)

---

## About

**My First UI** recreates the mechanics and feel of Stick Hero for the desktop using JavaFX.  
The player controls a hero who must cross platforms by growing a stick to the correct length.  
Game state (like cherries collected) is saved locally so you can continue your progress.

## Features

- Faithful recreation of Stick Hero game mechanics for desktop
- Interactive UI built with JavaFX and FXML (Scene Builder)
- Object-oriented and event-driven architecture
- Save and load game progress (including cherry count and high scores) via Java Serializable
- Sound effects and background music
- Pause, resume, and restart functionality
- Polished UI with custom icons and images

## Tech Stack

- **Java 21**
- **JavaFX 21** (controls, FXML, media)
- **Scene Builder** (FXML UI layout)
- **Maven** for build automation and dependency management

## Project Structure

```
Prajil/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/example/prajil/
        │       ├── HelloApplication.java        # Main application entry point
        │       ├── HomePageController.java      # Controller for home screen
        │       ├── PlaySceneController.java     # Controller for gameplay
        │       ├── GameLoop.java                # Main game logic and loop
        │       ├── GameOver.java                # Game over logic & UI
        │       ├── HelperHero.java              # Hero character logic
        │       ├── HelperPlatform.java          # Platform logic
        │       └── ... (other helpers, assets)
        ├── resources/
        │   └── com/example/prajil/
        │       ├── homePage.fxml                # Home page UI layout
        │       ├── playScene.fxml               # Gameplay UI layout
        │       ├── pauseScene.fxml              # Pause menu UI layout
        │       ├── icon.jpg                     # App icon
        │       ├── arcade.mp3                   # Background music
        │       ├── running.mp3, stickSound.mp3  # Sound effects
        │       ├── cherry.png, hero_0.png       # Game assets
        │       └── ... (other assets)
        └── java/module-info.java
```

## Installation

### Prerequisites

- Java 21 or newer
- Maven 3.x
- JavaFX SDK (if not included in your JDK)

### Clone the Repository

```bash
git clone https://github.com/prajil22359/My-First-UI.git
cd My-First-UI/Prajil
```

### Build the Project

```bash
mvn clean install
```

### Run the Game

You can run the game using the JavaFX Maven plugin:

```bash
mvn javafx:run
```

Or, if you want to run directly (after building):

```bash
java -jar target/Prajil-1.0-SNAPSHOT.jar
```
> **Note:** Make sure JavaFX is in your module path if your JDK does not bundle JavaFX.

## Gameplay

- **Objective:** Cross from platform to platform by growing the stick to the appropriate length. The hero will attempt to walk across the stick. If the stick is too short or too long, the hero will fall, and the game ends.
- **Cherries:** Collect cherries for bonus points.
- **Controls:**
  - Click and hold (or press and hold) to grow the stick.
  - Release to let the stick fall and let the hero walk.
  - Use the pause menu to pause or restart the game.

## Saving & Progress

- The game automatically saves your cherry count and high score using Java's Serializable interface.
- Progress is saved in a file named `cherryCount.txt` within your working directory.

## Screenshots

*Add screenshots of the UI/gameplay here for better visualization.*  
*(You can use ![image](path/to/screenshot.png) syntax.)*

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

## Credits

- **Guide:** Dr. Raghava Muthuraju
- **Developer:** [Your Name or Team]
- **Sound & Art Assets:** All assets used are for educational, non-commercial purposes.

## License

This project is licensed for educational use only. If you wish to reuse any part of this project, please contact the author.

---

### For more details, see the [source code on GitHub](https://github.com/prajil22359/My-First-UI).

