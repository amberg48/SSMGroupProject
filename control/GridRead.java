package control;
import model.Grid;

import java.util.ArrayList;
import java.util.List;

public class GridRead {

    // Used to get adjacen t cells
    private static int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};

    public GridRead() {

    }

    /**
     * Checks whether or not a grid cell at a given point is on fire or not.
     * @param grid Grid being used for simulation.
     * @param x X coordinate of grid.
     * @param y Y coordinate of grid.
     * @return Whether or not the grid cell is on fire.
     */
    public boolean checkForFire(Grid grid, int x, int y){
        return grid.getGridCell(x, y).getOnFire();
    }

    /**
     * Returns all grids from a given x, y coordinate
     * @param matrix The 2d array that the Grid is composed of.
     * @param x X coordinate
     * @param y Y coordinate
     * @return List of surrounding items.
     */
    public static List<Integer> getSurroundings(int[][] matrix, int x, int y){
      List<Integer> res = new ArrayList<Integer>();
      for (int[] direction : directions) {
          int cx = x + direction[0];
          int cy = y + direction[1];
          if(cy >=0 && cy < matrix.length)
              if(cx >= 0 && cx < matrix[cy].length)
                  res.add(matrix[cy][cx]);
      }
      
      return res;
}
    
}
