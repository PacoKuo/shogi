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
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移
        if (!isPromoted) {
            return (dx==0&&dy!=0) || (dy==0&&dx!=0);//橫向縱向隨便走
        }
        else{
            return isValidNaruMove(dx, dy);
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
