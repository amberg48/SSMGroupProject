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
    // Directions below are from the perspective of the adjacent cell --> cell we're checking to see will be on fire
    private static String[] directions_String = new String[]{"North, East", "East", "South, East",  "South", "South, West",  "West",  "North, West",  "North"};
    
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
     * @param x X coordinate
     * @param y Y coordinate
     * @return List of surrounding items.
     */
    public List<GridCell> getSurroundings(int x, int y, Grid oldGrid){
        List<GridCell> res = new ArrayList<GridCell>();
        for (int[] direction : directions) {
            int cx = x + direction[0];
            int cy = y + direction[1];
            if(cx >=0 && cx < oldGrid.getGridCells().length)
                if(cy >= 0 && cy < oldGrid.getGridCells()[cx].length)
                    res.add(oldGrid.getGridCells()[cx][cy]);
        }
        
        return res;
    }

    public List<String> getSurroundings_NSEW(int x, int y){
        List<String> res = new ArrayList<String>();
        int currDirectionIndex = 0;
        for (int[] direction : directions) {
            int cx = x + direction[0];
            int cy = y + direction[1];
            // Only get directions of cells that are on fire
            if(cx >=0 && cx < grid.getGridCells().length)
                if(cy >= 0 && cy < grid.getGridCells()[cx].length)
                    if(grid.getGridCell(cx,cy).getOnFire())
                        res.add(directions_String[currDirectionIndex]);
            currDirectionIndex++;
        }
        
        return res;
    }

    /**
     * Gets the number of cells that have the help of wind.
     * @param directions Cardinal directions of adjacent cells (from adjacent cell to given cell)
     * @return Number of cells that wind blows in direction of given cell
     */
    private int getCellsWindHelps(List<String> directions)
    {

        int result = 0;
        int windDirection = grid.getWindDirection();
        String windDirection_String = "";
        switch (windDirection){
            case 1:
                windDirection_String = "North";
                break;
            case 2:
                windDirection_String = "East";
                break;
            case 3:
                windDirection_String = "South";
                break;
            case 4:
                windDirection_String = "West";
                break;
            default:
                System.out.println("Invalid wind direction encoding.");
                break;
        }

        for(String dir : directions){
            if(windDirection_String.equals(dir)){
                result++;
            }
        }

        return result;
    }

    public double getSpreadChance(int i, int j, Grid oldGrid){
        // Get number of adjacent cells
        List<GridCell> adjacentCells = getSurroundings(i, j, oldGrid);
        int numCells = adjacentCells.size();
        // Get number of adjacent cells that are on fire
        double numCellsOnFire = 0;
        for(GridCell gc : adjacentCells){
            if(gc.getOnFire() == true){
                numCellsOnFire++;
            }
        }

        // Check if any adjacent cell that's on fire has wind blowing it towards the given cell
        List<String> adjacentCells_NSEW = getSurroundings_NSEW(i, j); // Checks cells that are on fire only
        int numCellsWithWindHelp = getCellsWindHelps(adjacentCells_NSEW);
        if(numCellsWithWindHelp > 0){
            numCellsOnFire += numCellsWithWindHelp + 0.1 * oldGrid.getWindSpeed();
        }

        // Get vegetation density
        int vegetationDensity = oldGrid.getGridCell(i, j).getVegetationDensity();

        // Calculate chance to spread
        return (numCellsOnFire / numCells) * vegetationDensity / 100;
    }
    
    public void step()
    {
    	Grid oldGrid = cloneGrid(grid);
    	for (int i = 0; i < 20; i++)
    	{
    		for (int j = 0; j < 20; j++)
    		{
    			if (!oldGrid.getGridCell(i, j).getOnFire())
    			{
    				double spreadChance = getSpreadChance(i, j, oldGrid);	// Chance of fire to spread to current square
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
    
    public Grid cloneGrid(Grid grid) {
    	Grid oldGrid = new Grid(20, 20);
    	for(int i = 0; i<20; i++) {
			for (int j = 0; j < 20; j++) {
				GridCell cell = grid.getGridCell(i, j);
				oldGrid.setGridCell(new GridCell(cell.getVegetationDensity(), cell.getOnFire()), i, j);
			}
		}
    	oldGrid.setWindSpeed(grid.getWindSpeed());
    	oldGrid.setWindDirection(grid.getWindDirection());
    	return oldGrid;
	}
}
