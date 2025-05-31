package shogi;

import javax.swing.ImageIcon;

public abstract class Piece {
    protected int x, y;
    public boolean isSente;
    public boolean isPromoted = false;
    public boolean isPathBlocked(int newX, int newY, Board board) {
    int dx = Integer.compare(newX, x); // 方向：-1、0、1
    int dy = Integer.compare(newY, y);
    int currX = x + dx;
    int currY = y + dy;
    while (currX != newX || currY != newY) {
        if (board.getPiece(currX, currY) != null) {
            return true; // 有棋子擋住
        }
        currX += dx;
        currY += dy;
        }
        return false; // 沒有阻擋
    }

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
    public void setPromoted(boolean value) {
    this.isPromoted = value;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
