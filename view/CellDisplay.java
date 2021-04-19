package view;

import java.awt.Color;

/**
 * Stores information for cell displayed in UI
 * @author Brett Amberge
 * @version 4.19.21
 */

public class CellDisplay {

    private Color col;  // cell color
    private int xPos;   // x position in grid
    private int yPos;   // y position in grid

    public CellDisplay(Color col, int xPos, int yPos) {
        this.col = col;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /*
     * Getters and setters
     */
    public void setColor(Color col) {
        this.col = col;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Color getColor() {
        return col;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}


