package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Grid;
import view.GridDisplay;

public class WildfireSimulation {

	public static void main(String[] args) throws InterruptedException {
		String input = "src/grids/random.ser";
		
		int x = 10;
		int y = 10;
		int count = 0;
		
		GridDisplay display = new GridDisplay(400, 400, 20, 20);
		display.initDraw();
		
		for (int i = 0; i < 100; i++)
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

			
			
			// Create controller
			GridRead controller = new GridRead(grid, display);
			
			display.updateGrid(controller.gridToDisplay(grid));
			boolean onFire = false;
			for (int j = 0; j < 30; j++) 
			{
				// Updates controller
				controller.step();
				// Update display
				display.updateGrid(controller.gridToDisplay(grid));
				Thread.sleep(20);
				if(grid.getGridCell(x, y).getOnFire())
				{
					onFire = true;
				}
			}
			
			if (onFire)
			{
				count++;
				
			}
			String info = "Iteration: " + i + "    Cell On Fire: " + grid.getGridCell(x, y).getOnFire() + "    Count: " + count + "    P: " + count/(i+1.0);
			System.out.println(info);
			display.updateLabel(info);
		}

	}

}
