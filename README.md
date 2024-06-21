

## README for Thief vs. Police Android Game



# Thief vs. Police

**Thief vs. Police** is an Android game where players control a thief trying to avoid police officers. The objective is to navigate through a grid while dodging the randomly placed police officers.

### Features

-   **Grid Layout:** A 3x8 grid where the thief can move left or right.
-   **Random Police Placement:** Police officers appear randomly in the grid, ensuring no diagonal or consecutive same-column placements.
-   **Game Over Conditions:** The game ends if the thief collides with a police officer three times.
-   **Vibration and Toast Notifications:** Provides haptic feedback and on-screen messages upon collisions.

### Screenshots
-   **Main activity:**
  
![mainActivity](https://github.com/nSella10/Thief_Escape/assets/166402852/60eacf9a-4dea-46ff-9050-805f75dd79b8)

-   **Lost Activity:**
  
![lostActivty](https://github.com/nSella10/Thief_Escape/assets/166402852/58623c35-5c47-4a7b-8344-20776ac4beb3)

### Code Structure

-   **MainActivity.java:** Manages the game logic, including the movement of the thief and police, collision detection, and UI updates.
-   **GameManager.java:** Handles the creation of police officers, ensuring no diagonal or consecutive same-column placements.
-   **EndGameActivity.java:** Displays the game over screen.
-   **activity_main.xml:** Defines the layout for the main game screen.
-   **end_game_activity.xml:** Defines the layout for the game over screen.

### How to Play

1.  Use the left and right buttons to move the thief within the grid.
2.  Avoid the police officers who appear randomly in the grid.
3.  The game ends after three collisions with the police officers.
4.  Restart the game from the game over screen.
