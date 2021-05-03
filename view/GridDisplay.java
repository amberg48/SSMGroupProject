package view;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * Class for displaying cell information on UI
 * Receives updates from controller
 * @author Brett Amberge
 * @version 4.19.21
 */

public class GridDisplay extends JPanel {

    int width, height;
    int rows;
    int columns;
    static String labelText = "Start Simulation";
    static CellDisplay[][] cells;
    static JFrame f = new JFrame();
    static JLabel l;

    public GridDisplay(int w, int h, int r, int c) {
        this.width = w;
        this.height = h;
        this.rows = r;
        this.columns = c;
        cells = new CellDisplay[rows][columns];
        for(int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                cells[x][y] = new CellDisplay(Color.green, x, y);
            }
        }
    }

    /**
    // Driver for testing
    public static void main(String... args) throws InterruptedException{
        CellDisplay[][] c = new CellDisplay[20][20];
        for(int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                c[x][y] = new CellDisplay(Color.green, x, y);
            }
        }
        initDraw();
        updateGrid(c);
        TimeUnit.SECONDS.sleep(5);
        c[10][10].setColor(Color.red);
        updateGrid(c);
        updateLabel("Updated");
    }
     */


    /**
     * Draw the initial JFrame object
     */
    public static void initDraw() {
        GridDisplay xyz = new GridDisplay(400, 400, 20, 20);
        xyz.setPreferredSize(new Dimension(400, 400));
        l = new JLabel(labelText);
        JPanel p = new JPanel();
        p.add(xyz);
        p.add(l);
        f.getContentPane().add(p);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(750, 450);
        f.setVisible(true);
    }

    /**
     * Update the JFrame object to match the new grid
     * @param c New grid to draw
     */
    public static void updateGrid(CellDisplay[][] c) {
        cells = c;
        f.repaint();
    }

    /**
     * Update the displayed text
     * @param s New String to display
     */
    public static void updateLabel(String s) {
        l.setText(s);
    }

    /**
     * Override the JFrame's paint method to draw grid of colored squares
     * @param g JFrame Graphics
     */
    public void paint(Graphics g) {
        int i;

        int rowHt = height/(rows);
        int rowWid = width/columns;

        // draw the cells
        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < columns; y++) {
                g.setColor(cells[x][y].getColor());
                g.drawRect(x*20, y*20, rowWid, rowHt);
                g.fillRect(x*20, y*20, rowWid, rowHt);
            }
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

    public CellDisplay[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getLabelText() { return labelText; }
}
