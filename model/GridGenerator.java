package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GridGenerator {
	
	public static Grid uniformGenerator()
	{
		Grid grid = new Grid(50, 50);
		
		for(int i = 0; i < 50; i++)
		{
			for(int j = 0; j < 50; j++)
			{
				grid.setGridCell(new GridCell(50, false), i, j);
			}
		}
		grid.getGridCell(0, 0).setOnFire(true);
		return grid;
	}

	public static void main(String[] args)
	{
		Grid grid = uniformGenerator();
		String output = "grids/uniform.ser";
		System.out.println(grid);
		// Writes grid to file
		try 
		{
			FileOutputStream fileOut = new FileOutputStream(output);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(grid);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + output);
		} 
		catch (IOException i) 
		{
			i.printStackTrace();
		}
		/*
		// Example of reading object file
		Grid grid2 = null;
		try 
		{
			FileInputStream fileIn = new FileInputStream(output);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			grid2 = (Grid) in.readObject();
			in.close();
			fileIn.close();
		} 
		catch (IOException i) 
		{
			i.printStackTrace();
		} 
		catch (ClassNotFoundException c) 
		{
			System.out.println("Grid class not found");
			c.printStackTrace();
		}
		
		System.out.println(grid2);
		*/
	}
}
