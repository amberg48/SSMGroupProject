package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Grid;
import view.GridDisplay;

public class WildfireSimulation {

	public static void main(String[] args) throws InterruptedException {
		String input = "grids/random.ser";
		
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

		GridDisplay display = new GridDisplay(400, 400, 20, 20);

		// Create controller
		GridRead controller = new GridRead(grid, display);
		display.initDraw();
		display.updateGrid(controller.gridToDisplay(grid));
		
		// Temporary, runs Simulation for 20 steps then ends.
		for (int i = 0; i < 20; i++) 
		{
			// Updates controller
			controller.step();
			// Update display
			System.out.println(grid.toString());
			display.updateGrid(controller.gridToDisplay(grid));
			Thread.sleep(500);
		}

	}

}
