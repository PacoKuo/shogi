package shogi.pieces;
import javax.swing.ImageIcon;
import java.io.File;
import shogi.Board;
import shogi.Piece;
import shogi.Player;
//步兵
public class Fuhyo extends Piece {

    // （true = 先手，false = 後手）
    public Fuhyo(int x, int y, boolean isSente) {
        super(x, y, isSente);
    }

    /**
     * 判斷移動是否合法
     * @param newX 目標 x 座標
     * @param newY 目標 y 座標
     * @param board 棋盤物件，用於查詢其他棋子
     * @return true 表示合法移動，false 表示不合法
     */
    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int dx = newX - x; // 橫向位移
        int dy = newY - y; // 縱向位移

        if (!isPromoted) {
            // 未升變：只能向前一步
            return dx == 0 && dy == (isSente ? -1 : 1);
        } else {
            // 升變後，走法與金將相同
            return isValidNaruMove(dx, dy , isSente);
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
        path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/fuhyo.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/fuhyo01.png";
    }
    else{
        path= isSente? "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/tokin.png" : "C:/Users/PACO KUO/eclipse-workspace/shogi/src/shogi/images/tokin01.png";
    }
    File imgFile = new File(path);
    return imgFile.exists() ? new ImageIcon(imgFile.getAbsolutePath()) : null;
}

}
