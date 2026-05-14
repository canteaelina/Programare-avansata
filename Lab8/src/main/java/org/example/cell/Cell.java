package org.example.cell;

import java.io.Serializable;

public class Cell implements Serializable {
    public int row, col;
    public boolean[] walls = {true, true, true, true}; // Sus, Dreapta, Jos, Stânga

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
