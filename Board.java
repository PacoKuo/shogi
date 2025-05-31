package shogi;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
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
            if (target.isSente() == isSente){
                return false; // 同陣營不能吃
            }
            else
                if(target.isPromoted()){
                target.setPromoted(false);
            }
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
    // 找出指定方的王的位置
    public Point findKing(boolean isSente) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Piece p = board[y][x];
                if (p != null && p instanceof Gyokusho && p.isSente() == isSente) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }
    public boolean isOute(boolean isSente) {
        Point kingPos = findKing(isSente);
        if (kingPos == null) return false;
        return isSquareUnderAttack(kingPos.x, kingPos.y, !isSente);
    }
    public boolean isSquareUnderAttack(int x, int y, boolean bySente) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Piece attacker = board[row][col];
                if (attacker != null && attacker.isSente() == bySente) {
                    if (attacker.isValidMove(x, y, this) && !attacker.isPathBlocked(x, y,this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean isInsideBoard(int x, int y) {
            return x >= 0 && x < 9 && y >= 0 && y < 9;
        }
    public List<int[]> getPathBetween(int fromX, int fromY, int toX, int toY) {
        List<int[]> path = new ArrayList<>();

        int dx = Integer.compare(toX, fromX);
        int dy = Integer.compare(toY, fromY);

        int x = fromX + dx;
        int y = fromY + dy;

        while (x != toX || y != toY) {
            path.add(new int[]{x, y});
            x += dx;
            y += dy;
        }

        return path;
    }
    public List<Piece> getAllPieces(boolean isSente) {
        List<Piece> pieces = new ArrayList<>();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Piece p = board[y][x];
                if (p != null && p.isSente() == isSente) {
                pieces.add(p);
                }
            }
        }
        return pieces;
    }
    public boolean isTsumi(Piece king, Board board) {
        boolean isSente = king.isSente();
        int kingX = king.getX();
        int kingY = king.getY();
        // Step 1: 尋找攻擊王的對方棋子
        List<Piece> attackers = new ArrayList<>();
        for (Piece piece : board.getAllPieces(!isSente)) {
            if (piece.isValidMove(kingX, kingY, board)) {
                attackers.add(piece);
            }
        }
        if (attackers.isEmpty()) {
            return false; 
        }
        // Step 2: 移動將軍逃出攻擊範圍
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int newX = kingX + dx;
                int newY = kingY + dy;

                if (!board.isInsideBoard(newX, newY)) continue;

                Piece target = board.getPiece(newX, newY);
                if (target != null && target.isSente() == isSente) continue; // 自家棋子

                boolean safe = true;
                for (Piece attacker : board.getAllPieces(!isSente)) {
                    if (attacker.isValidMove(newX, newY, board)) {
                        safe = false;
                        break;
                    }
                }
                if (safe) {
                    return false; // 有一格可以逃就好
                }
            }
        }
        if (attackers.size() == 1) {
            Piece attacker = attackers.get(0);
            int ax = attacker.getX();
            int ay = attacker.getY();

            for (Piece ally : board.getAllPieces(isSente)) {
                if (ally == king) continue; // 王已經不能動
                if (ally.isValidMove(ax, ay, board)) {
                    return false; // 有人可以吃掉攻擊者
                }
            }
            // Step 4: 有沒有可以擋住路徑的我方棋子
            List<int[]> path = getPathBetween(attacker.getX(), attacker.getY(), kingX, kingY);
            for (int[] square : path) {
                for (Piece ally : board.getAllPieces(isSente)) {
                    if (ally == king) continue;
                    if (ally.isValidMove(square[0], square[1], board)) {
                        return false; // 可以擋住
                    }
                }
            }
        }
        return true;
    }
}
