package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Grid;

public class WildfireSimulation {

	public static void main(String[] args) throws InterruptedException {
		String input = "src/grids/random.ser";
		
		// Read grid from given file
		Grid grid = null;
		try 
		{
			FileInputStream fileIn = new FileInputStream(input);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			grid = (Grid) in.readObject();
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
		
		System.out.println(grid);
		
		// Create controller
		GridRead controller = new GridRead(grid);
		
		// Temporary, runs Simulation for 20 steps then ends.
		for (int i = 0; i < 20; i++) 
		{
			// Updates controller
			controller.step();
			// Update display
			System.out.println(grid.toString());
			Thread.sleep(500);
		}

	}

}
