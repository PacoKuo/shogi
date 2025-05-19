package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//香車
public class Kyosha extends Piece{
    public Kyosha(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移
        if (!isPromoted) {
        if(isSente==true){
            return (dx==0 && dy<=-1);//只許往前
        }
        else{
            return (dx==0 && dy>=1);
        }
        }
        else{
            return isValidNaruMove(dx, dy, isSente);//形變後走法
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
        String path="";
        if(!isPromoted){
            path = isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kyosha.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/kyosha01.png";
        }
        else{
            path = isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/narikyo.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/narikyo01.png";
        }
        File imgFile = new File(path);
        return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
    }
}
