package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//王將or玉將
public class Gyokusho extends Piece{
    public Gyokusho(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; 
        int dy = newY - y;
        return(dx==1&&dy==1)||(dx==0&&dy==1)||(dx==0&&dy==-1)||(dx==1&&dy==-1)
              ||(dx==-1&&dy==-1)||(dx==1&&dy==0)||(dx==-1&&dy==0)||(dx==-1&&dy==1);
    }
    @Override
    public ImageIcon getImageIcon() {
    String path = "";
    path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/Gyokusho.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/Gyokusho01.png";
    File imgFile = new File(path);
    return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
}

}
