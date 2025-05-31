package shogi.pieces;
import java.io.File;

import javax.swing.ImageIcon;

import shogi.Board;
import shogi.Piece;
import shogi.Player;
//金將
public class Kinsho extends Piece {
    public Kinsho(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移
        if(isSente==true){
            if((dx == 0 && dy == -1) ||               // 前
                   (Math.abs(dx) == 1 && dy == 0) ||      // 左右
                   (dx == 0 && dy == 1) ||                // 後
                   (dx == -1 && dy == -1) || (dx == 1 && dy == -1)){
                if (!isPathBlocked(newX, newY, board)) {
                    Piece target = board.getPiece(newX, newY);
                    return target == null || target.isSente() != this.isSente;
            } // 左前、右前
        }
        return false;
    }
    else{
            if((dx == 0 && dy == 1) ||               // 前
                   (Math.abs(dx) == 1 && dy == 0) ||      // 左右
                   (dx == 0 && dy == -1) ||                // 後
                   (dx == 1 && dy == 1) || (dx == -1 && dy == 1)){
                if (!isPathBlocked(newX, newY, board)) {
                    Piece target = board.getPiece(newX, newY);
                    return target == null || target.isSente() != this.isSente;
            }// 左前、右前
        }
        return false;
    }
}
    @Override
    public ImageIcon getImageIcon() {
    String path = "";
    path= isSente ? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kinsho.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kinsho01.png";
    File imgFile = new File(path);
    return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
}
}
