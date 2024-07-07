

## README for Thief vs. Police Android Game



# Thief vs. Police

**Thief vs. Police** is an Android game where players control a thief trying to avoid police officers. The objective is to navigate through a grid while dodging the randomly placed police officers.

### Features

-   **Grid Layout:** A 3x8 grid where the thief can move left or right.
-   **Random Police Placement:** Police officers appear randomly in the grid, ensuring no diagonal or consecutive same-column placements.
-   **Game Over Conditions:** The game ends if the thief collides with a police officer three times.
-   **Vibration and Toast Notifications:** Provides haptic feedback and on-screen messages upon collisions.
-   **Location-based High Scores:** Save the player's high score along with the location where the game was played.
-   **High Score List with Rankings:** Display the high scores with rankings and allow navigation to the location on the map.
-   **Back Button:** Allows users to return to the main activity from the high score list.

### Screenshots
-   **Menu:**

  ![menu](https://github.com/nSella10/Thief_Escape/assets/166402852/0102bfeb-e6e7-4784-90e6-25000802a4d7)
-
-   **Play Game:**
   
![playgame](https://github.com/nSella10/Thief_Escape/assets/166402852/e63f98cb-353b-462b-84ef-643bc1c10d51)
-
-   **Game Over:**

![gameOver](https://github.com/nSella10/Thief_Escape/assets/166402852/0ee8a924-c7fa-4eb9-9046-9a2aea9f789d)
-
-   **HighScore:**
  
  ![highScore](https://github.com/nSella10/Thief_Escape/assets/166402852/866172ca-05f5-4804-a06d-bb4fcebbb108)
-
### Code Structure

-   **MainActivity.java:** Manages the game logic, including the movement of the thief and police, collision detection, and UI updates.
-   **GameManager.java:** Handles the creation of police officers, ensuring no diagonal or consecutive same-column placements.
-   **EndGameActivity.java:** Displays the game over screen.
-   **activity_main.xml:** Defines the layout for the main game screen.
-   **end_game_activity.xml:** Defines the layout for the game over screen.
-   **HighScoreActivity.java:** Displays the list of high scores.
-   **ListFragment.java:** Manages the high score list display and interactions.
-   **MapFragment.java:** Displays the location on the map based on high score data.
-   **activity_main.xml:** Defines the layout for the main game screen.
-   **end_game_activity.xml:** Defines the layout for the game over screen.
-   **fragment_list.xml:** Defines the layout for the high score list.
-   **fragment_map.xml:** Defines the layout for the map view.


### video Game
[https://github.com/nSella10/Thief_Escape/assets/166402852/ac016f1c-63e7-4f5b-9634-49e38656c62a](https://github.com/nSella10/Thief_Escape/assets/166402852/219f7f5b-b565-4165-8730-21704b5de034)

### How to Play

-1.Use the left and right buttons to move the thief within the grid.
-2.Avoid the police officers who appear randomly in the grid.
-3.Collect coins that appear randomly based on a random condition.
-4.The game ends after three collisions with the police officers.
-5.Your high score along with your location will be saved.
-6.View and navigate to your high score locations from the high score list.
-7.Restart the game from the game over screen.
