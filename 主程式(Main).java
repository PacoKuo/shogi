package shogi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import shogi.Board;
import shogi.Piece;
public class Main extends JFrame {
    private final int TILE_SIZE = 100;
    private final int BOARD_SIZE = 9;

    private int selectedX = -1, selectedY = -1;
    private boolean isSenteTurn = true;
    private java.util.List<Point> validMoves = new ArrayList<>();
    private Board boardRef;
    public boolean isInEnemyZone(int y, boolean isSente) {
        return isSente ? (y <= 2) : (y >= 6);
    }

    public Main(Board board) {
        this.boardRef = board;

        setTitle("將棋");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int boardWidth = TILE_SIZE * BOARD_SIZE;
                int boardHeight = TILE_SIZE * BOARD_SIZE;
                int offsetX = (panelWidth - boardWidth) / 2;
                int offsetY = (panelHeight - boardHeight) / 2;

                // 繪製棋盤格與棋子
                for (int row = 0; row < BOARD_SIZE; row++) {
                    for (int col = 0; col < BOARD_SIZE; col++) {
                        int x = offsetX + col * TILE_SIZE;
                        int y = offsetY + row * TILE_SIZE;

                        g.setColor(new Color(222, 184, 135));
                        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);

                        Piece piece = boardRef.getPiece(col, row);
                        if (piece != null) {
                            ImageIcon icon = piece.getImageIcon();
                            if (icon != null) {
                                g.drawImage(icon.getImage(), x, y, TILE_SIZE, TILE_SIZE, null);
                            } else {
                                g.setFont(new Font("Serif", Font.BOLD, 30));
                            }
                        }
                    }
                }

                // 顯示合法移動範圍（綠色圓圈）
                g.setColor(new Color(0, 255, 0, 100));
                for (Point p : validMoves) {
                    int x = offsetX + p.x * TILE_SIZE;
                    int y = offsetY + p.y * TILE_SIZE;
                    g.fillOval(x + TILE_SIZE / 4, y + TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 2);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
            }
        };

        // 滑鼠點擊處理邏輯
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int panelWidth = panel.getWidth();
                int panelHeight = panel.getHeight();
                int boardWidth = TILE_SIZE * BOARD_SIZE;
                int boardHeight = TILE_SIZE * BOARD_SIZE;
                int offsetX = (panelWidth - boardWidth) / 2;
                int offsetY = (panelHeight - boardHeight) / 2;

                int col = (e.getX() - offsetX) / TILE_SIZE;
                int row = (e.getY() - offsetY) / TILE_SIZE;

                if (col < 0 || col >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) return;

                if (selectedX == -1 && selectedY == -1) {
                    // 第一次點選：選取棋子
                    Piece selectedPiece = boardRef.getPiece(col, row);
                    if (selectedPiece != null && selectedPiece.isSente == isSenteTurn) {
                        selectedX = col;
                        selectedY = row;
                        validMoves.clear();

                        // 顯示所有合法移動點
                        for (int y = 0; y < BOARD_SIZE; y++) {
                            for (int x = 0; x < BOARD_SIZE; x++) {
                                if (selectedPiece.isValidMove(x, y, boardRef)) {
                                    validMoves.add(new Point(x, y));
                                }
                            }
                        }
                        repaint();
                    }
                } else {
                    // 第二次點選：嘗試移動
                    Piece piece = boardRef.getPiece(selectedX, selectedY);
                    if (piece != null && piece.isValidMove(col, row, boardRef)) {
                        boolean promote = false;

                        if (piece.isPromoted==false && isInEnemyZone(selectedY, isSenteTurn)) {
                            Object[] options = {"成る", "成らず"};
                            int choice = JOptionPane.showOptionDialog(null,
                                    "要進化棋子嗎？", "進化與否",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);
                            promote = (choice == 0);
                        }
                        boardRef.movePiece(selectedX, selectedY, col, row, isSenteTurn, promote);
                        if (boardRef.isOute(true)) {
                                JOptionPane.showMessageDialog(null, "王手！");
                        }   
                        if (boardRef.isOute(false)) {
                                JOptionPane.showMessageDialog(null, "王手！");
                        }
                        Point enemyKing = boardRef.findKing(!isSenteTurn);
                    if (enemyKing == null) {
                            JOptionPane.showMessageDialog(null,
                                (isSenteTurn ? "先手" : "後手") + "勝利",
                                "將軍光榮赴死了",
                                JOptionPane.INFORMATION_MESSAGE);
                            int restart = JOptionPane.showConfirmDialog(null, "你是否要選擇雪恥", "再來啊?", JOptionPane.YES_NO_OPTION);
                            if (restart == JOptionPane.YES_OPTION) {
                                JFrame ggg = new JFrame();
                                ggg.dispose();
                                new Main(new Board());
                            } else {
                                System.exit(0);
                            }
                        }
                    else{
                        Piece enemy = boardRef.getPiece(enemyKing.x, enemyKing.y);
                        if ((enemyKing != null && boardRef.isTsumi(enemy, boardRef)) || enemyKing == null) {
                            JOptionPane.showMessageDialog(null,
                            (isSenteTurn ? "先手" : "後手") + "勝利",
                            "詰み",
                            JOptionPane.INFORMATION_MESSAGE);
                            int restart = JOptionPane.showConfirmDialog(null, "你是否要選擇雪恥", "再來啊?", JOptionPane.YES_NO_OPTION);
                            if (restart == JOptionPane.YES_OPTION) {
                                JFrame ggg=new JFrame();
                                ggg.dispose();
                                new Main(new Board());
                            } else {
                                System.exit(0);
                            } 
                        }
                        isSenteTurn = !isSenteTurn; // 換手
                    }
                }

                    // 清除選取狀態
                    selectedX = selectedY = -1;
                    validMoves.clear();
                    repaint();
                }
            }
        });

        add(panel);
        setVisible(true);
        pack();
    }
    public static void main(String[] args) {
        Board board = new Board();
        Main gui = new Main(board);
        GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
        device.setFullScreenWindow(gui);
    }
}
