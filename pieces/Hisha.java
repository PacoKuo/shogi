package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//飛車
public class Hisha extends Piece {
    public Hisha(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    public boolean isValidMove(int newX, int newY, Board board) {
    int dx = newX - x;
    int dy = newY - y;
    if (!isPromoted) {
        if ((dx == 0 && dy != 0) || (dy == 0 && dx != 0)) {
            if (!isPathBlocked(newX, newY, board)) {
                Piece target = board.getPiece(newX, newY);
                return target == null || target.isSente() != this.isSente;
            }
        }
        return false;
    } else {
        if ((dx == 0 && dy != 0) || (dy == 0 && dx != 0)) {
            if (!isPathBlocked(newX, newY, board)) {
                Piece target = board.getPiece(newX, newY);
                return target == null || target.isSente() != this.isSente;
            }
            return false;
        } else if (isValidNaruMove(dx, dy)) {
            Piece target = board.getPiece(newX, newY);
            return target == null || target.isSente() != this.isSente;
        }
        return false;
    }
}
    private boolean isValidNaruMove(int dx, int dy) {
        return (dx==0&&dy!=0) || (dy==0&&dx!=0)
               ||(dx==1&&dy==1)||(dx==-1&&dy==1)||(dx==-1&&dy==-1)||(dx==1&&dy==-1);//多了左前右前左後右後各一格
    }
    @Override
    public ImageIcon getImageIcon() {
        String path = "";
        if(!isPromoted){
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/hisha.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/hisha01.png";
        }
        else{
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/ryuou.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/ryuou01.png";
        }
        File imgFile = new File(path);
        return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
    }

}
