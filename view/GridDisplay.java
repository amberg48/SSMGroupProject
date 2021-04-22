package view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Class for displaying cell information on UI
 * Receives updates from controller
 * @author Brett Amberge
 * @version 4.19.21
 */

public class GridDisplay extends JPanel{

    int width, height;
    int rows;
    int columns;
    static ArrayList<CellDisplay> cells;

    public GridDisplay(int w, int h, int r, int c) {
        this.width = w;
        this.height = h;
        this.rows = r;
        this.columns = c;
    }

    public static void main(String[] args) {
        cells = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cells.add(new CellDisplay(Color.green, 20*i, 20*j));
            }
        }

        JFrame f = new JFrame();
        GridDisplay xyz = new GridDisplay(200, 200, 20, 20);
        f.add(xyz);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(416, 438);
        f.setVisible(true);
    }

    public void paint(Graphics g) {
        int i;
        width = getSize().width;
        height = getSize().height;

        int rowHt = height/(rows);
        int rowWid = width/columns;

        // draw the cells
        for(CellDisplay cell: cells) {
            g.setColor(cell.getColor());
            g.drawRect(cell.getxPos(), cell.getyPos(), rowWid, rowHt);
            g.fillRect(cell.getxPos(), cell.getyPos(), rowWid, rowHt);
        }

        g.setColor(Color.black);

        // draw the rows
        for(i = 0; i < rows; i++) {
            g.drawLine(0, i*rowHt, width, i*rowHt);
        }

        // draw the columns
        for(i = 0; i < columns; i++) {
            g.drawLine(i*rowWid, 0, i*rowWid, height);
        }
    }
}
