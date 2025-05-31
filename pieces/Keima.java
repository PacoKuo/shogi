package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//桂馬
public class Keima extends Piece{
    public Keima(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移
        if (!isPromoted) {
            if(isSente==true){
                if((dx==1 && dy==-2) || (dx==-1 && dy==-2)){
                    if (board.getPiece(newX, newY) == null || board.getPiece(newX, newY).isSente() != this.isSente) {
                        Piece target = board.getPiece(newX, newY);
                        return target == null || target.isSente() != this.isSente;
                    }
                }
                return false;
            }
            else{
                if((dx==1 && dy==2) || (dx==-1 && dy==2)){
                    if (board.getPiece(newX, newY) == null || board.getPiece(newX, newY).isSente() != this.isSente) {
                        Piece target = board.getPiece(newX, newY);
                        return target == null || target.isSente() != this.isSente;
                    }
                }
                return false;
            }
        } else {
            if(isValidNaruMove(dx, dy, isSente)){
                Piece target = board.getPiece(newX, newY);
                return target == null || target.isSente() != this.isSente;
            }
            return false;
        }
    }
    private boolean isValidNaruMove(int dx, int dy, boolean isSente) {
            if(isSente==true){
                return (dx == 0 && dy == -1) ||               // 前
                       (Math.abs(dx) == 1 && dy == 0) ||      // 左右
                       (dx == 0 && dy == 1) ||                // 後
                       (dx == -1 && dy == -1) || (dx == 1 && dy == -1); // 左前、右前
            }
            else{
                return (dx == 0 && dy == 1) ||               // 前
                       (Math.abs(dx) == 1 && dy == 0) ||      // 左右
                       (dx == 0 && dy == -1) ||                // 後
                       (dx == 1 && dy == 1) || (dx == -1 && dy == 1); // 左前、右前
            }
    }
    @Override
    public ImageIcon getImageIcon() {
        String path = "";
        if(!isPromoted){
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/keima.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/keima01.png";
        }
        else{
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/narikei.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/narikei01.png";
        }
        File imgFile = new File(path);
        return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
    }
}
