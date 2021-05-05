package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Grid;
import view.GridDisplay;

public class WildfireSimulation {

	public static void main(String[] args) throws InterruptedException {
		
		// Default argument values
		String input = "src/grids/random.ser";
		int simulations = 100;
		int iterations  = 30;
		int timestep = 20;
		int x = 10;
		int y = 10;
		
		// Iterate through arguments, sets values as needed
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals("-s"))					// Sets number of simulations to run
			{
				i++;
				simulations = Integer.parseInt(args[i]);
			}
			else if (args[i].equals("-i"))				// Sets steps per simulation
			{
				i++;
				iterations=Integer.parseInt(args[i]);
			}
			else if (args[i].equals("-t"))				// Sets time between steps in ms
			{
				i++;
				timestep = Integer.parseInt(args[i]);
			}
			else if (args[i].equals("-x"))				// Sets x coordinate of cell to look at
			{
				i++;
				x = Integer.parseInt(args[i]);
			}
			else if (args[i].equals("-y"))				// Sets y coordinate of cell to look at
			{
				i++;
				y = Integer.parseInt(args[i]);
			}
			else if (args[i].equals("-g"))				// Sets grid being used
			{
				i++;
				input = args[i];
			}
		}
		
		System.out.println("Wildfire Simulation:\tGrid: " + input);
		System.out.println("Simulations: " + simulations + "\tIterations: "  + iterations + "\tTimestep: " + timestep + "\tX: " + x + "\tY: " + y);
		
		int count = 0;
		
		GridDisplay display = new GridDisplay(400, 400, 20, 20);
		display.initDraw();
    
		for (int i = 0; i < simulations; i++)
		{
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
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException c) 
			{
				System.out.println("Grid class not found");
				c.printStackTrace();
			}

			if (i == 0)
			{
				System.out.println("Wind Direction: " + grid.getWindDirection() + "\tWind Speed: " + grid.getWindSpeed());
			}
			
			// Create controller
			GridRead controller = new GridRead(grid, display);
			
			display.updateGrid(controller.gridToDisplay(grid));
			boolean onFire = false;
			for (int j = 0; j < iterations; j++) 
			{
				// Updates controller
				controller.step();
				// Update display
				display.updateGrid(controller.gridToDisplay(grid));
				Thread.sleep(timestep);
				if(grid.getGridCell(x, y).getOnFire())
				{
					onFire = true;
				}
			}
			
			if (onFire)
			{
				count++;
				
			}

			String info = "Iteration: " + i + "    Cell On Fire: " + onFire + "    Count: " 
					+ count + "    P: " + String.format("%.3f", count/(i+1.0));
			System.out.println(info);
			display.updateLabel(info);
		}

	}

}
