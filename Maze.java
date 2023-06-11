/**
 * A program to store information about the maze
 *
 * Course Info:
 * ICS4UO
 * Valentina Krasteva
 *
 * @author Angelina Jiang
 */

public class Maze {

    /**
     * 0 = Up
     * 1 = Right
     * 2 = Down
     * 3 = Left
     */

    /** A matrix with a 3rd dimension, which returns whether this spot contains a wall or not.
     * Example: maze[0][0][1] means that on the right of the top left cell, there is a wall */
    private boolean[][][] maze;

    /**
     * Maze constructr
     *
     * Manually inputted information on the maze
     */
    public Maze() {
        maze = new boolean[10][16][4];
        {
            maze[0][0][0] = true;
            maze[0][0][2] = true;
            maze[0][0][3] = true;
            maze[0][1][0] = true;
            maze[0][2][0] = true;
            maze[0][2][2] = true;
            maze[0][3][0] = true;
            maze[0][3][1] = true;
            maze[0][3][2] = true;
            maze[0][4][0] = true;
            maze[0][4][3] = true;
            maze[0][5][0] = true;
            maze[0][5][2] = true;
            maze[0][6][0] = true;
            maze[0][6][1] = true;
            maze[0][7][3] = true;
            maze[0][8][0] = true;
            maze[0][8][2] = true;
            maze[0][9][0] = true;
            maze[0][10][0] = true;
            maze[0][10][2] = true;
            maze[0][11][0] = true;
            maze[0][11][2] = true;
            maze[0][12][0] = true;
            maze[0][12][1] = true;
            maze[0][12][2] = true;
            maze[0][13][0] = true;
            maze[0][13][3] = true;
            maze[0][14][0] = true;
            maze[0][14][2] = true;
            maze[0][15][0] = true;
            maze[0][15][1] = true;
            maze[1][0][0] = true;
            maze[1][0][3] = true;
            maze[1][1][1] = true;
            maze[1][1][2] = true;
            maze[1][2][0] = true;
            maze[1][2][3] = true;
            maze[1][3][0] = true;
            maze[1][3][2] = true;
            maze[1][4][1] = true;
            maze[1][4][2] = true;
            maze[1][5][0] = true;

            maze[1][5][3] = true;
            maze[1][5][2] = true;
            maze[1][6][1] = true;
            maze[1][7][1] = true;
            maze[1][7][3] = true;
            maze[1][8][0] = true;
            maze[1][8][1] = true;
            maze[1][8][3] = true;
            maze[1][9][1] = true;
            maze[1][9][3] = true;
            maze[1][10][0] = true;
            maze[1][10][3] = true;
            maze[1][11][0] = true;
            maze[1][11][2] = true;
            maze[1][12][2] = true;
            maze[1][12][0] = true;
            maze[1][13][2] = true;
            maze[1][13][1] = true;
            maze[1][14][0] = true;
            maze[1][14][3] = true;
            maze[1][14][1] = true;
            maze[1][15][1] = true;
            maze[1][15][3] = true;

            maze[2][0][2] = true;
            maze[2][0][3] = true;
            maze[2][1][0] = true;
            maze[2][2][2] = true;
            maze[2][3][0] = true;
            maze[2][3][2] = true;
            maze[2][4][0] = true;
            maze[2][4][2] = true;
            maze[2][5][1] = true;
            maze[2][5][0] = true;
            maze[2][6][3] = true;
            maze[2][7][2] = true;
            maze[2][8][1] = true;
            maze[2][8][2] = true;
            maze[2][9][2] = true;
            maze[2][9][3] = true;
            maze[2][10][1] = true;
            maze[2][11][0] = true;
            maze[2][11][3] = true;
            maze[2][12][0] = true;
            maze[2][13][2] = true;
            maze[2][13][0] = true;
            maze[2][14][1] = true;
            maze[2][14][2] = true;
            maze[2][15][1] = true;
            maze[2][15][3] = true;

            maze[3][0][0] = true;
            maze[3][0][3] = true;
            maze[3][1][1] = true;
            maze[3][1][2] = true;
            maze[3][2][0] = true;
            maze[3][2][3] = true;
            maze[3][3][0] = true;
            maze[3][3][1] = true;
            maze[3][4][0] = true;
            maze[3][4][3] = true;
            maze[3][5][1] = true;
            maze[3][5][2] = true;
            maze[3][6][2] = true;
            maze[3][6][3] = true;
            maze[3][7][0] = true;
            maze[3][7][2] = true;
            maze[3][8][0] = true;
            maze[3][8][1] = true;
            maze[3][8][2] = true;
            maze[3][9][0] = true;
            maze[3][9][3] = true;
            maze[3][10][1] = true;
            maze[3][10][2] = true;
            maze[3][11][1] = true;
            maze[3][11][3] = true;
            maze[3][12][3] = true;
            maze[3][13][0] = true;
            maze[3][13][1] = true;
            maze[3][14][3] = true;
            maze[3][14][0] = true;
            maze[3][15][1] = true;


            maze[4][0][1] = true;
            maze[4][0][3] = true;
            maze[4][1][1] = true;
            maze[4][1][3] = true;
            maze[4][1][0] = true;
            maze[4][2][3] = true;
            maze[4][2][1] = true;
            maze[4][3][1] = true;
            maze[4][3][3] = true;
            maze[4][4][3] = true;
            maze[4][4][2] = true;
            maze[4][5][0] = true;
            maze[4][6][0] = true;
            maze[4][6][2] = true;
            maze[4][7][0] = true;
            maze[4][7][1] = true;
            maze[4][8][0] = true;
            maze[4][8][1] = true;
            maze[4][8][3] = true;
            maze[4][9][1] = true;
            maze[4][9][3] = true;
            maze[4][10][0] = true;
            maze[4][10][2] = true;
            maze[4][10][3] = true;
            maze[4][11][1] = true;
            maze[4][12][3] = true;
            maze[4][12][1] = true;
            maze[4][13][1] = true;
            maze[4][13][2] = true;
            maze[4][13][3] = true;
            maze[4][14][1] = true;
            maze[4][14][3] = true;
            maze[4][15][1] = true;
            maze[4][15][3] = true;

            maze[5][0][1] = true;
            maze[5][0][3] = true;
            maze[5][1][1] = true;
            maze[5][1][3] = true;
            maze[5][2][3] = true;
            maze[5][2][1] = true;
            maze[5][3][1] = true;
            maze[5][3][3] = true;
            maze[5][3][2] = true;
            maze[5][4][0] = true;
            maze[5][4][3] = true;
            maze[5][5][1] = true;
            maze[5][5][2] = true;
            maze[5][6][0] = true;
            maze[5][6][3] = true;
            maze[5][7][2] = true;
            maze[5][8][1] = true;
            maze[5][8][2] = true;
            maze[5][9][2] = true;
            maze[5][9][3] = true;
            maze[5][10][0] = true;
            maze[5][10][2] = true;
            maze[5][11][1] = true;
            maze[5][11][2] = true;
            maze[5][12][3] = true;
            maze[5][12][1] = true;
            maze[5][12][2] = true;
            maze[5][13][0] = true;
            maze[5][13][3] = true;
            maze[5][14][1] = true;
            maze[5][14][2] = true;
            maze[5][15][1] = true;
            maze[5][15][3] = true;

            maze[6][0][1] = true;
            maze[6][0][3] = true;
            maze[6][1][1] = true;
            maze[6][1][3] = true;
            maze[6][2][3] = true;
            maze[6][2][2] = true;
            maze[6][3][0] = true;
            maze[6][3][2] = true;
            maze[6][4][1] = true;
            maze[6][5][0] = true;
            maze[6][5][2] = true;
            maze[6][5][3] = true;
            maze[6][6][1] = true;
            maze[6][6][2] = true;
            maze[6][7][0] = true;
            maze[6][7][3] = true;
            maze[6][8][0] = true;
            maze[6][8][2] = true;
            maze[6][9][0] = true;
            maze[6][9][2] = true;
            maze[6][10][0] = true;
            maze[6][10][1] = true;
            maze[6][11][0] = true;
            maze[6][11][3] = true;
            maze[6][12][0] = true;
            maze[6][12][2] = true;
            maze[6][13][1] = true;
            maze[6][13][2] = true;
            maze[6][14][0] = true;
            maze[6][14][3] = true;
            maze[6][15][1] = true;
            maze[6][15][2] = true;

            maze[7][0][2] = true;
            maze[7][0][3] = true;
            maze[7][1][1] = true;
            maze[7][1][2] = true;
            maze[7][2][0] = true;
            maze[7][2][3] = true;
            maze[7][3][0] = true;
            maze[7][3][1] = true;
            maze[7][3][2] = true;
            maze[7][4][1] = true;
            maze[7][4][3] = true;
            maze[7][5][0] = true;
            maze[7][5][3] = true;
            maze[7][6][0] = true;
            maze[7][7][1] = true;
            maze[7][7][2] = true;
            maze[7][8][0] = true;
            maze[7][8][3] = true;
            maze[7][9][0] = true;
            maze[7][9][1] = true;
            maze[7][10][1] = true;
            maze[7][10][2] = true;
            maze[7][10][3] = true;
            maze[7][11][3] = true;
            maze[7][11][1] = true;
            maze[7][12][0] = true;
            maze[7][12][3] = true;
            maze[7][13][0] = true;
            maze[7][13][1] = true;
            maze[7][14][2] = true;
            maze[7][14][3] = true;
            maze[7][15][0] = true;
            maze[7][15][1] = true;

            maze[8][0][0] = true;
            maze[8][0][3] = true;
            maze[8][1][0] = true;
            maze[8][2][2] = true;
            maze[8][3][0] = true;
            maze[8][3][2] = true;
            maze[8][4][1] = true;
            maze[8][4][2] = true;
            maze[8][5][1] = true;
            maze[8][5][3] = true;
            maze[8][6][2] = true;
            maze[8][6][3] = true;
            maze[8][7][0] = true;
            maze[8][7][1] = true;
            maze[8][8][1] = true;
            maze[8][8][3] = true;
            maze[8][9][1] = true;
            maze[8][9][3] = true;
            maze[8][10][0] = true;
            maze[8][10][3] = true;
            maze[8][11][1] = true;
            maze[8][11][2] = true;
            maze[8][12][1] = true;
            maze[8][12][2] = true;
            maze[8][12][3] = true;
            maze[8][13][3] = true;
            maze[8][14][0] = true;
            maze[8][14][2] = true;
            maze[8][15][1] = true;
            maze[8][15][2] = true;

            maze[9][0][1] = true;
            maze[9][0][2] = true;
            maze[9][0][3] = true;
            maze[9][1][3] = true;
            maze[9][1][2] = true;
            maze[9][2][0] = true;
            maze[9][2][2] = true;
            maze[9][3][0] = true;
            maze[9][3][2] = true;
            maze[9][4][0] = true;
            maze[9][4][2] = true;
            maze[9][5][1] = true;
            maze[9][5][2] = true;
            maze[9][6][0] = true;
            maze[9][6][2] = true;
            maze[9][6][3] = true;
            maze[9][7][1] = true;
            maze[9][7][2] = true;
            maze[9][8][1] = true;
            maze[9][8][3] = true;
            maze[9][9][2] = true;
            maze[9][9][3] = true;
            maze[9][10][1] = true;
            maze[9][10][2] = true;
            maze[9][11][0] = true;
            maze[9][11][2] = true;
            maze[9][11][3] = true;
            maze[9][12][0] = true;
            maze[9][12][2] = true;
            maze[9][13][2] = true;
            maze[9][14][0] = true;
            maze[9][14][2] = true;
            maze[9][15][0] = true;
            maze[9][15][1] = true;
            maze[9][15][2] = true;
        }
    }
    public boolean check (int i, int j, int k) {
        return maze[i][j][k];
    }
}
