package model;

import java.io.Serializable;

public class Grid implements Serializable{
	
	// Used for saving Grid objects to a file, increase by 1 when adding / removing fields.
	private static final long serialVersionUID = 1L;
	
	private GridCell[][] grid;
	private int windDirection;
	private int windSpeed;
	
	public Grid(int height, int width)
	{
		grid = new GridCell[height][width];
		windDirection = 0;
		windSpeed = 0;
	}
	
	public Grid(int height, int width, int windDirection, int windSpeed)
	{
		grid = new GridCell[height][width];
		this.windDirection = windDirection;
		this.windSpeed = windSpeed;
	}
	
	public GridCell getGridCell(int x, int y)
	{
		return grid[x][y];
	}

	public int getWindDirection() 
	{
		return windDirection;
	}

	public int getWindSpeed() 
	{
		return windSpeed;
	}
	
	public void setGridCell(GridCell cell, int x, int y)
	{
		grid[x][y] = cell;
	}
	
	public void setWindDirection(int windDirection) 
	{
		this.windDirection = windDirection;
	}

	public void setWindSpeed(int windSpeed) 
	{
		this.windSpeed = windSpeed;
	}
	
	public String toString()
	{
		String result = "";
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				if (grid[i][j].getOnFire())
				{
					result += "F   ";
				}
				else 
				{
					result += String.format("%1$-4s", grid[i][j].getVegetationDensity());
				}
			}
			result += "\n";
		}
		return result;
	}
}
