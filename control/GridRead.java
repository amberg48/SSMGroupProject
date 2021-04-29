package control;
import model.Grid;
import model.GridCell;

import java.util.ArrayList;
import java.util.List;

public class GridRead {

    // Used to get adjacen t cells
    private static int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};
    
    
    private Grid grid;
    
    
    public GridRead(Grid grid) {
    	this.grid = grid;
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
    public List<GridCell> getSurroundings(int x, int y){
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

    public double getSpreadChance(int i, int j){
        // Get number of adjacent cells
        List<GridCell> adjacentCells = getSurroundings(i, j);
        int numCells = adjacentCells.size();

        // Get number of adjacent cells that are on fire
        double numCellsOnFire = 0; // This needs to be a double so that we can get regular division
                                  // and not get thrown 0 as our return value.
        for(GridCell gc : adjacentCells){
            if(gc.getOnFire() == true){
                numCellsOnFire++;
            }
        }

        // Get vegetation density
        int vegetationDensity = this.grid.getGridCell(i, j).getVegetationDensity();

        // Calculate chance to spread
        return (numCellsOnFire / numCells) * vegetationDensity;
    }
    
    public void step()
    {
    	for (int i = 0; i < 20; i++)
    	{
    		for (int j = 0; j < 20; j++)
    		{
    			if (!grid.getGridCell(i, j).getOnFire())
    			{
    				double spreadChance = getSpreadChance(i, j);	// Chance of fire to spread to current square
        			// Calculate probability here
        			
        			
        			// Sets cell on fire based on calculated spreadChance
        			if (spreadChance > Math.random()) {
        				grid.getGridCell(i, j).setOnFire(true);
        			}
    			}
    			else 
    			{
    				double extinguishChance = 0.0;	// Chance of fire to extinguish / balk
    				// Calculate probability here
    				
    				
    				if (extinguishChance > Math.random()) {
    					grid.getGridCell(i, j).setOnFire(false);
    				}
    				
    				// Decrement vegetation density, and check if any fuel is left
    				grid.getGridCell(i, j).setVegetationDensity(grid.getGridCell(i, j).getVegetationDensity() - 1);
    				if (grid.getGridCell(i, j).getVegetationDensity() == 0)
    				{
    					grid.getGridCell(i, j).setOnFire(false);
    				}
    				
    			}
    			
    		}
    	}
    }
    
    
}
