package eminentdevs.maze;

public class Cell {
    private int col;
    private int row;
    private byte status;

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public byte getStatus() {
        return status;
    }

    public Cell(int row, int col){
        status = 0;
        this.row = row;
        this.col = col;
    }

    public void setStatus(byte stid){
        status = stid;
    }
}
