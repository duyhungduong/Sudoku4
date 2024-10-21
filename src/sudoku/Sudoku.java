package sudoku;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Sudoku {

    private static SHomePage home;
    private static SFrame game;
    private static SSavedGame save;

    public static void startGame() {
        save.setVisible(false);
        home.setVisible(false);
        game.setVisible(true);
    }

    public static void showHomePage() {
        save.setVisible(false);
        game.setVisible(false);
        home.setVisible(true);
    }

    public static void showSavedGame() {
        home.setVisible(false);
        game.setVisible(false);
        save.setVisible(true);
    }

    public static void saveGame(String state, String mode, SPuzzle sPuzzle) {
        String playerName = JOptionPane.showInputDialog("!!!\nPlease enter your name:");
        int rows = sPuzzle.getNumRows();
        int cols = sPuzzle.getNumColumns();

        try {
            File file = new File(playerName+mode+"sudoku.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(playerName+"\n"+state+"\n" +mode+ "\n");

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if(sPuzzle.getValue(row, col) == "") writer.write("0");
                    writer.write(sPuzzle.getValue(row, col));
                    if (col < cols - 1) {
                        writer.write(", ");
                    }
                }
                writer.write("\n");
            }

            writer.close();
            JOptionPane.showMessageDialog(null, "Bài toán đã được in thành công.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi in bài toán.");
        }
        home.setTitleNewGame();
        game.setVisible(false);
        showHomePage();
        save.addPlayer(playerName, state, mode);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                home = new SHomePage();
                game = new SFrame();
                save = new SSavedGame();
                home.setVisible(true);
            }
        });
    }
}
