package shogi;
import shogi.Piece;
import shogi.pieces.Fuhyo;
import shogi.pieces.Ginsho;
import shogi.pieces.Gyokusho;
import shogi.pieces.Hisha;
import shogi.pieces.Kakugyo;
import shogi.pieces.Keima;
import shogi.pieces.Kinsho;
import shogi.pieces.Kyosha;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[9][9]; 
        initialize();
    }
    private void initialize() {
        //先手
        for (int col = 0; col < 9; col++) {
        board[6][col] = new Fuhyo(col, 6, true); //步兵
        }
        board[8][0] = new Kyosha(0, 8, true);//香車
        board[8][1] = new Keima(1, 8, true);//桂馬
        board[8][2] = new Ginsho(2, 8, true);//銀將
        board[8][3] = new Kinsho(3, 8, true);//金將
        board[8][4] = new Gyokusho(4, 8, true); // 玉將
        board[8][5] = new Kinsho(5, 8, true);//金將
        board[8][6] = new Ginsho(6, 8, true);//銀將
        board[8][7] = new Keima(7, 8, true);//桂馬
        board[8][8] = new Kyosha(8, 8, true);//香車
        board[7][1] = new Kakugyo(1, 7, true); // 角行
        board[7][7] = new Hisha(7, 7, true);   // 飛車
        //後手
        for (int col = 0; col < 9; col++) {
            board[2][col] = new Fuhyo(col, 2, false); //步兵
        }
        board[0][0] = new Kyosha(0, 0, false);
        board[0][1] = new Keima(1, 0, false);
        board[0][2] = new Ginsho(2, 0, false);
        board[0][3] = new Kinsho(3, 0, false);
        board[0][4] = new Gyokusho(4, 0, false); // 王將
        board[0][5] = new Kinsho(5, 0, false);
        board[0][6] = new Ginsho(6, 0, false);
        board[0][7] = new Keima(7, 0, false);
        board[0][8] = new Kyosha(8, 0, false);
        board[1][1] = new Hisha(1, 1, false);   
        board[1][7] = new Kakugyo(7, 1, false); 
    }
    public Piece[][] getBoard() {
        return board;
    }
    public Piece getPiece(int x, int y) {
        return board[y][x];
    }
    public void setPiece(int x, int y, Piece piece) {
        board[y][x] = piece;
    }
    public boolean isInEnemyZone(int y, boolean isSente) {
        return isSente ? (y <= 2) : (y >= 6);
    }
    public boolean movePiece(int fromX, int fromY, int toX, int toY, boolean isSente,boolean promoteChoice) {
        Piece piece = board[fromY][fromX];
        if (piece == null) return false;
        if (piece.isSente() != isSente) return false;
        if (!piece.isValidMove(toX, toY, this)) return false;

        Piece target = board[toY][toX];
        if (target != null) {
            if (target.isSente() == isSente) return false; // 同陣營不能吃
            board[toY][toX] = null; // 吃掉對方，變成持駒    
        }
        board[fromY][fromX] = null;
        piece.setPosition(toX, toY);
        board[toY][toX] = piece;
        if (!piece.isPromoted() && isInEnemyZone(fromY, isSente)==true){
            if (promoteChoice) {
            piece.promote();
        }
        }
        return true;
    }
}
