# Prisoner's Dilemma
This assignment is part of the Programming (2IP90) course at TU/e. The task was to create a program that simulaties an iterated version of one of the most prominently studied phenomena in Game Theory, 'Prisoner’s Dilemma'. Here are some of the features of this mulit-player multi-round Prisoner’s Dilemma:

## About the simulation
- Players are positioned on a 50 by 50 gird, Blue indicates a cooperating player, and Red indicates a defecting player.
- Each player can interact with a total of 8 other players (in horizontal, vertical, and diagonal directions with patches at the end connected to patcehs on the other side of the board).
- In each cycle, the score of each patch will be determined by the interactions:
  - For a patch that cooperated, the score will be the number of neighbours that also cooperated.
  - For a patch that defected, the score for this patch will be the product of the Defection-Award factor (α) and the number of neighbours that cooperated.
- In the next round, each player adopts the strategy of the neighbour that scored the highest in the previous round, including itself (if there is a tie, then a random strategy is adopted).

## Features and Functionalities

- The user can set the value of α (0.0 to 3.0) in the UI using the slider.
- The user can set the simulation speed using the frequency slider
- The user can set an alternative update rule in the UI where when a player’s own score is highest in the neighbourhood (but possibly not unique) the player will not change strategy.
- Clicking on the patches will toggle its strategy.
- Patches that just switched from defection to cooperation are light blue and patches that switched from defection to cooperation are orange.
- The 'Go' button can start and pause the simulation.
- The 'Reset' button initializes the grid to random strategies.
