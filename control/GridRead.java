package control;
import model.Grid;
import model.GridCell;

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
     * Returns all grid cells from a given x, y coordinate.
     * WILL NOT include given coordinate in returned List.
     * 
     * @param matrix The 2d array that the Grid is composed of.
     * @param x X coordinate
     * @param y Y coordinate
     * @return List of surrounding items.
     */
    public List<GridCell> getSurroundings(Grid grid, int x, int y){
        List<GridCell> res = new ArrayList<GridCell>();
        for (int[] direction : directions) {
            int cx = x + direction[0];
            int cy = y + direction[1];
            if(cy >=0 && cy < grid.getGridCells().length)
                if(cx >= 0 && cx < grid.getGridCells()[cy].length)
                    res.add(grid.getGridCells()[cy][cx]);
        }
        
        return res;
    }
    
}
