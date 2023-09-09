/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part PlayingField
 * 
 * @author Chris D'Mello 1566105
 * @author Jayin Nejal 1537199
 * assignment group 9
 * 
 * assignment copyright Kees Huizing
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

class PlayingField extends JPanel implements ActionListener, MouseListener {

    private Patch[][] grid; // grid of patches of type Patch

    private double alpha = 0.0; // defection award factor

    private int freq = 1; // speed / frequency

    private Timer timer; // Timer variable of the class

    private boolean click = false; // checks whether a patch was clicked or not

    // stores the positions of the patch that was clicked
    private int i = 0;
    private int j = 0;

    // decides which rule the program should work on
    // true : No change of strategy if player’s score is highest in the neighbourhood
    // false : Otherwise
    private boolean rule;

    // random number genrator
    private static final long SEED = 37L; // seed for random number generator; any number goes
    public static final Random random = new Random(SEED);

    private static final int gridSizeX = 50; // number of rows in the grid
    private static final int gridSizeY = 50; // number of columns in the grid
    private static final int patchSize = 500 / gridSizeX; // size of a patch in pixels

    public PlayingField() {
        grid = new Patch[gridSizeX][gridSizeY];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Patch();
                grid[i][j].setCooperating(random.nextBoolean());
            }
        }
        this.timer = new Timer(1000, this);

        // loops to store neighbours of each patch in an ArrayList
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int[] xNeighbour = new int[] { i - 1, i - 1, i - 1, i, i, i + 1, i + 1, i + 1 };
                int[] yNeighbour = new int[] { j - 1, j, j + 1, j - 1, j + 1, j - 1, j, j + 1 };
                for (int z = 0; z < xNeighbour.length; z++) {
                    xNeighbour[z] %= grid.length;
                    yNeighbour[z] %= grid[0].length;
                    if (xNeighbour[z] < 0) {
                        xNeighbour[z] += grid.length;
                    }
                    if (yNeighbour[z] < 0) {
                        yNeighbour[z] += grid[0].length;
                    }
                    grid[i][j].addNeighbours(grid[xNeighbour[z]][yNeighbour[z]]);
                }
            }
        }
    }

    // displays the updated grid each time paintComponent is called
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (click) {
            click = false;
            grid[i][j].toggleStrategy();
        }
        g.fillRect(0, 0, patchSize * gridSizeX + 4, patchSize * gridSizeY + 4);
        g.setColor(Color.BLUE);
        g.fillRect(2, 2, patchSize * gridSizeX, patchSize * gridSizeY);
        this.addMouseListener(this);
        this.setLayout(new GridLayout(gridSizeX, gridSizeY));
        this.setPreferredSize(new Dimension(patchSize * gridSizeX + 4, patchSize * gridSizeY + 4));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j].switched()) {
                    if (grid[i][j].isCooperating()) {
                        g.setColor(Color.CYAN);
                    } else {
                        g.setColor(Color.ORANGE);
                    }
                    grid[i][j].setSwitch(false);
                } else {
                    if (grid[i][j].isCooperating()) {
                        g.setColor(new Color(96, 64, 255));
                    } else {
                        g.setColor(new Color(236, 77, 78));
                    }
                }
                g.fillRoundRect(j * patchSize + 2, i * patchSize + 2, patchSize, patchSize, 2, 2);
            }
        }
    }

    // resets the strategies, scores, and nature of switch of the entire grid
    void resetGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].setCooperating(random.nextBoolean());
                grid[i][j].resetScore();
                grid[i][j].setSwitch(false);
            }
        }
        repaint();
    }

    // event to toggle strategies when a particular patch is clicked
    @Override
    public void mouseClicked(MouseEvent e) {
        i = e.getY() / patchSize;
        j = e.getX() / patchSize;
        click = true;
        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    // calls the step function and the paintComponent function after every (1000 / freq) ms
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            step();
            repaint();
        }
    }

    // starts and stops the timer when goPause button is pressed
    void startStop(boolean timeCount) {
        if (timeCount == true) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    // sets the value of the defection award factor
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    // returns the value of the defection award factor
    public double getAlpha() {
        return this.alpha;
    }

    // sets the frequency
    public void setFrequency(int freq) {
        if (freq == 0) {
            this.freq = freq + 1;
        } else {
            this.freq = freq;
        }
    }

    // sets speed / frequency of the program
    void speed() {
        timer.setDelay(1000 / this.freq);
    }

    void setRule(boolean rule) {
        this.rule = rule;
    }

    /**
     * calculate and execute one step in the simulation
     */
    public void step() {
        boolean[][] resultGrid = new boolean[grid.length][grid[0].length]; // stores updated strategies
        resultGrid = getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].resetScore();
                grid[i][j].calcScore(getAlpha());
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                double max = grid[i][j].getMax(); // stores the maximum score amongst the patch and its neigbours

                ArrayList<Boolean> strategies = new ArrayList<Boolean>(); // stores strategies of max score neighbours
                if (rule == true) {
                    if (max == grid[i][j].getScore()) {
                        strategies.add(grid[i][j].isCooperating());
                    } else {
                        strategies = grid[i][j].getNeighbourStrategy(max);
                    }
                } else {
                    strategies = grid[i][j].getNeighbourStrategy(max);
                    if (max == grid[i][j].getScore()) {
                        strategies.add(grid[i][j].isCooperating());
                    }
                }
                resultGrid[i][j] = strategies.get(random.nextInt(strategies.size()));
                strategies.clear();
                if (grid[i][j].isCooperating() != resultGrid[i][j]) {
                    grid[i][j].setSwitch(true);
                }
            }
        }

        // assigning new strategies to patches of grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].setCooperating(resultGrid[i][j]);
            }
        }
    }

    // return grid as 2D array of booleans
    // true for cooperators, false for defectors
    // precondition: grid is rectangular, has non-zero size and elements are non-null
    public boolean[][] getGrid() {
        boolean[][] resultGrid = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                resultGrid[x][y] = grid[x][y].isCooperating();
            }
        }
        return resultGrid;
    }

    // sets grid according to parameter inGrid
    // a patch should become cooperating if the corresponding
    // item in inGrid is true
    public void setGrid(boolean[][] inGrid) {
        // ...
    }
}