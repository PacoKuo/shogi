package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//角行
public class Kakugyo extends Piece{
    public Kakugyo(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移
        if (!isPromoted) {
            if((dx != 0 && dy != 0 && Math.abs(dx) == Math.abs(dy))){
                if (!isPathBlocked(newX, newY, board)) {
                    Piece target = board.getPiece(newX, newY);
                    return target == null || target.isSente() != this.isSente;
                }
            }
            return false; 
        }
        else{
            if(isValidNaruMove(dx, dy)){
                Piece target = board.getPiece(newX, newY);
                return target == null || target.isSente() != this.isSente;
            }
        }
        return false;
    }
    private boolean isValidNaruMove(int dx, int dy) {
        return (dx != 0 && dy != 0 && Math.abs(dx) == Math.abs(dy))
               ||(dx==1&&dy==0)||(dx==-1&&dy==0)||(dx==0&&dy==-1)||(dx==0&&dy==1);
    }
    @Override
    public ImageIcon getImageIcon() {
        String path = "";
        if(!isPromoted){
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kakugyo.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kakugyo01.png";
        }
        else{
            path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/ryouma.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/ryouma01.png";
        }
        File imgFile = new File(path);
        return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
    }
}
