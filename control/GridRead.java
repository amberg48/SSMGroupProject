package control;
import model.Grid;
import model.GridCell;
import view.CellDisplay;
import view.GridDisplay;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class GridRead {

    // Used to get adjacent cells
    private static int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};
    
    
    private Grid grid;
    private GridDisplay display;
    
    
    public GridRead(Grid grid, GridDisplay display) {
    	this.grid = grid;
    	this.display = display;
    }

	/**
	 * Convert the information from a grid object to a celldisplay
	 * @param grid Grid information
	 * @return Display information
	 */
	public CellDisplay[][] gridToDisplay(Grid grid) {
    	CellDisplay[][] cells = display.getCells();
    	for(int i=0; i<display.getRows(); i++) {
    		for(int j=0; j<display.getColumns(); j++) {
    			Color color;
    			if(grid.getGridCell(i, j).getOnFire()) {
    				color = new Color(1f, 0f, 0f);
				} else {
    				float g = (float)(grid.getGridCell(i, j).getVegetationDensity())/100;
    				color = new Color(0f, g, 0f);
				}
    			cells[i][j].setColor(color);
			}
		}
    	return cells;
	}

    /**
     * Checks whether or not a grid cell at a given point is on fire or not.
     * @param grid Grid being used for simulation.
     * @param x X coordinate of grid.
     * @param y Y coordinate of grid.
     * @return Whether or not the grid cell is on fire.
     */
    public boolean checkForFire(int x, int y){
        return grid.getGridCell(x, y).getOnFire();
    }

    /**
     * Returns all grid cells from a given x, y coordinate.
     * WILL NOT include given coordinate in returned List.
     * 
     * @param grid The 2d array that the Grid is composed of.
     * @param x X coordinate
     * @param y Y coordinate
     * @return List of surrounding items.
     */
    public List<GridCell> getSurroundings(int x, int y){
        List<GridCell> res = new ArrayList<GridCell>();
        for (int[] direction : directions) {
            int cx = x + direction[0];
            int cy = y + direction[1];
            if(cx >=0 && cx < grid.getGridCells().length)
                if(cy >= 0 && cy < grid.getGridCells()[cx].length)
                    res.add(grid.getGridCells()[cx][cy]);
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
        return (numCellsOnFire / numCells) * vegetationDensity / 100;
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
    				double extinguishChance = (.01 - (0.0001 * grid.getGridCell(i, j).getVegetationDensity()));	// Chance of fire to extinguish / balk
    				
    				if (extinguishChance > Math.random()) {
    					grid.getGridCell(i, j).setOnFire(false);
    				}
    				
    				// Decrement vegetation density, and check if any fuel is left
    				grid.getGridCell(i, j).setVegetationDensity(grid.getGridCell(i, j).getVegetationDensity() - 5);
    				if (grid.getGridCell(i, j).getVegetationDensity() < 0)
    				{
    					grid.getGridCell(i, j).setVegetationDensity(0);
    				}
    				if (grid.getGridCell(i, j).getVegetationDensity() == 0)
    				{
    					grid.getGridCell(i, j).setOnFire(false);
    				}
    				
    			}
    			
    		}
    	}
    }
    
    
}
