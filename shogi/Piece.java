package shogi;

import javax.swing.ImageIcon;

public abstract class Piece {
    protected int x, y;
    public boolean isSente;
    public boolean isPromoted = false;

    public Piece(int x, int y, boolean isSente) {
        this.x = x;
        this.y = y;
        this.isSente = isSente;
    }
    protected ImageIcon image;
    public abstract boolean isValidMove(int newX, int newY, Board board);
    public void promote() {
        isPromoted = true;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public ImageIcon getImageIcon() {
        return image;
    }
    public boolean isSente() {
        return isSente;
    }
    public boolean isPromoted() {
        return isPromoted;
    }
}
