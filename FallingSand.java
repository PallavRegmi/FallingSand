/**
 * Sand lab adapted from http://nifty.stanford.edu/2017/feinberg-falling-sand/
 *
 * Student name: PALLAV REGMI
 *
 * TODO: Document expected behavior of various materials here
 */
import java.awt.*;
import java.util.*;
public class SandLab {

    /**
     * Enum for material types of the particles
     */
    public enum Material {
        EMPTY,
        METAL,
        SAND,
        WATER;

        //TODO: add constants for additional particle types here

    }

    /** grid of particles of various materials*/
    private Material[][] grid;

    /** The display window */
    private SandDisplay display;

    /**
     * Create a new SandLab of given size.
     * @param numRows number of rows
     * @param numCols number of columns
     */
    public SandLab(int numRows, int numCols) {
        // TODO: Include names for all Materials used in simulation
        //       (Can you do it without manually listing them all?)
        String[] names = new String[]{"Empty", "Metal","Sand","Water"};
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
        grid = new Material[numRows][numCols];

        // TODO: initialize grid with empty cells
    }

    /**
     * called when the user clicks on a location using the given tool
     * @param row Row of location
     * @param col Column of location
     * @param tool Name of selected tool
     */
    public void locationClicked(int row, int col, String tool) {
        // TODO: update grid location with selected material
        grid[row][col] = Material.valueOf(tool);
    }

    /**
     * copies each element of grid into the display
     */
    public void updateDisplay() {
        // TODO: update display with colors based on grid contents
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == Material.EMPTY) {
                    display.setColor(i, j, Color.BLACK);
                }
                else if (grid[i][j] == Material.METAL){
                    display.setColor(i, j, Color.gray);
                }
                else if (grid[i][j] == Material.SAND){
                    display.setColor(i, j, Color.yellow);
                }
                else if (grid[i][j] == Material.WATER) {
                    display.setColor(i, j, Color.cyan);
                }
            }
        }
    }

    /**
     * Update the simulation by one step.
     * Called repeatedly.
     * Causes one random particle to maybe do something
     */
    public void step() {
        // TODO: select random location and update the particle if relevant
        Random rand = new Random();
        int x = rand.nextInt(grid.length - 1);
        int y = rand.nextInt(grid[0].length);

        if (grid[x][y] == Material.SAND) {
            if (grid[x+1][y] == Material.EMPTY) {
                grid[x][y] = Material.EMPTY;
                grid[x+1][y] = Material.SAND;
            }
        }
        else if(grid[x][y] == Material.WATER) {
            int num = rand.nextInt(3);
            System.out.println("" + num);
            if (grid[x][y] == Material.WATER) {
                if ((( (y+1) < grid[0].length) && (y > 1)) && ((grid[x+1][y] == Material.EMPTY) || (grid[x+1][y] == Material.WATER)) && ((grid[x][y+1] == Material.EMPTY)  || (grid[x][y+1] == Material.WATER)) && ((grid[x][y-1] == Material.EMPTY)  || (grid[x][y-1] == Material.WATER)))
                {

                    //AND out of bounds
                    grid[x][y] = Material.EMPTY;

                    if ((num == 0) && (grid[x][y+1] == Material.EMPTY)) {
                        grid[x][y+1] = Material.WATER; //right
                    }
                    else if ((num == 1) && (grid[x][y-1] == Material.EMPTY)) {
                        grid[x][y-1] = Material.WATER; //left
                    }
                    else if ((num == 2) && (grid[x+1][y] == Material.EMPTY)) {
                        grid[x+1][y] = Material.WATER; //down

                    }
                }
            }
            //if num = 1, go left. if 2, go right. if 3, go down
            // If the location in that randomly chosen direction is empty, the water particle moves there. (Look for ways to minimize duplicate code in your step method.)
            //Test that the ICE behaves roughly like a liquid, taking the shape of a container.


        }

    }
    /**
     * Run the SandLab particle simulation.
     *
     * DO NOT MODIFY THIS METHOD!
     */
    public void run() {
        // keep updating as long as the program is running
        while (true) {
            // update some number of particles, as determined by the speed slider
            for (int i = 0; i < display.getSpeed(); i++) {
                step();
            }
            // Update the display object's colors
            updateDisplay();
            // wait for redrawing and for mouse events
            display.repaintAndPause(1);

            int[] mouseLoc = display.getMouseLocation();
            //test if mouse clicked
            if (mouseLoc != null) {
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool().toUpperCase());
            }
        }
    }

    /** Creates a new SandLab and sets it running */
    public static void main(String[] args) {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }
}
