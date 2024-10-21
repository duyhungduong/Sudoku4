package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SFrame extends JFrame {

    private JPanel buttonSelectionPanel;
    private SudokuPanel sPanel;
    private SPuzzleType type;

    public SFrame() {
        //initComponent();

        this.setTitle("Sudoku");
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(900, 750));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(1000, 600));

        sPanel = new SudokuPanel();

        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SPuzzleType.NINEBYNINE, 26);
    }

    public SFrame(SPuzzle puzzle) {
        //initComponent();

        this.setTitle("Sudoku");
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(900, 750));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(1000, 600));

        sPanel = new SudokuPanel(puzzle);

        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SPuzzleType.NINEBYNINE, 26);

    }

    public void rebuildInterface(SPuzzleType puzzleType, int fontSize) {
        initComponent(puzzleType);
        SPuzzle generatedPuzzle = new SGenerator().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        buttonSelectionPanel.removeAll();
        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            int buttonSize = (puzzleType == SPuzzleType.TWELVEBYTWELVE) ? 47 : 50;
            b.setPreferredSize(new Dimension(buttonSize, buttonSize));
            b.setBackground(new Color(224,224,224));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    private class NewGameListener implements ActionListener {

        private SPuzzleType puzzleType;
        private int fontSize;

        public NewGameListener(SPuzzleType puzzleType, int fontSize) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType, fontSize);
        }
    }

    private void solveSudoku() {
        SPuzzle puzzle = sPanel.getPuzzle(); // Get the current puzzle from the panel
        int[][] board = new int[puzzle.getNumRows()][puzzle.getNumColumns()];

        // Convert the puzzle to a 2D integer array
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                String value = puzzle.getValue(row, col);
                if (!value.isEmpty()) {
                    board[row][col] = Integer.parseInt(value);
                } else {
                    board[row][col] = 0;
                }
            }
        }

        SudokuSolver solver = new SudokuSolver();
        if (solver.solveSudoku(board)) {
            // Display the solution using a JOptionPane
            StringBuilder solution = new StringBuilder("Solution:\n");
            for (int row = 0; row < puzzle.getNumRows(); row++) {
                for (int col = 0; col < puzzle.getNumColumns(); col++) {
                    solution.append(board[row][col]).append(" ");
                }
                solution.append("\n");
            }
            JOptionPane.showMessageDialog(this, solution.toString(), "Sudoku Solution", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No solution found.", "Sudoku Solution", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void solveSudoku1() {
        SPuzzle puzzle = sPanel.getPuzzle(); // Get the current puzzle from the panel
        int[][] board = new int[puzzle.getNumRows()][puzzle.getNumColumns()];

        // Convert the puzzle to a 2D integer array
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                String value = puzzle.getValue(row, col);
                if (!value.isEmpty()) {
                    board[row][col] = Integer.parseInt(value);
                } else {
                    board[row][col] = 0;
                }
            }
        }

        SudokuSolver solver = new SudokuSolver();
        if (solver.solveSudokuElon(board, SPuzzleType.SIXBYSIX)) {
            // Display the solution using a JOptionPane
            StringBuilder solution = new StringBuilder("Solution:\n");
            for (int row = 0; row < puzzle.getNumRows(); row++) {
                for (int col = 0; col < puzzle.getNumColumns(); col++) {
                    solution.append(board[row][col]).append(" ");
                }
                solution.append("\n");
            }
            JOptionPane.showMessageDialog(this, solution.toString(), "Sudoku Solution", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No solution found.", "Sudoku Solution", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void initComponent(SPuzzleType puzzleType) {
        type = puzzleType;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 750));
        this.setMaximumSize(new Dimension(900, 750));

        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenu help = new JMenu("Help");

        JMenuItem homepage = new JMenuItem("Home Page");
        JMenuItem about = new JMenuItem("About");
        JMenuItem rules = new JMenuItem("Rules");
        
        
        JMenuItem exit = new JMenuItem("Exit");

        JMenu newGame = new JMenu("New SudokuGame");
        JMenu solve = new JMenu("Solve");
        JMenuItem sixBySixGame = new JMenuItem("Easy");
        sixBySixGame.addActionListener(new NewGameListener(SPuzzleType.SIXBYSIX, 30));
        JMenuItem nineByNineGame = new JMenuItem("Normal");
        nineByNineGame.addActionListener(new NewGameListener(SPuzzleType.NINEBYNINE, 26));
        JMenuItem twelveByTwelveGame = new JMenuItem("Hard");
        twelveByTwelveGame.addActionListener(new NewGameListener(SPuzzleType.TWELVEBYTWELVE, 12));

        JMenuItem savegame = new JMenuItem("Save");

        help.add(solve);
        help.add(rules);
        help.add(homepage);
        help.add(about);
        help.add(exit);

        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);
        newGame.add(twelveByTwelveGame);
        //newGame.add(sixteenBySizteenGame);
        game.add(newGame);
        
        //JMenuItem print = new JMenuItem("Print");
        
        game.add(savegame);
        //file.add(print);
        
        menuBar.add(game);
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        JMenuItem six = new JMenuItem("6x6");
        JMenuItem nine = new JMenuItem("9x9");
        JMenuItem twleve = new JMenuItem("12x12");
        

        if (puzzleType == SPuzzleType.SIXBYSIX) {
            solve.add(six);
        } else if (puzzleType == SPuzzleType.NINEBYNINE) {
            solve.add(nine);
        } 
//        else if (puzzleType == SPuzzleType.TWELVEBYTWELVE) {
//            solve.add(twleve);
//        } 

        nine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        six.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku1();
            }
        });
        
        twleve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku1();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        homepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Sudoku.showHomePage();
            }
        });

        savegame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mode = "Normal 9x9";
                if (type == SPuzzleType.NINEBYNINE) {
                    mode = "Nomarl 9x9";
                } else if (type == SPuzzleType.SIXBYSIX) {
                    mode = "Easy 6x6";
                } else {
                    mode = "Hard";
                }
                String state = sPanel.checkIfFilled();
                Sudoku.saveGame(state, mode , sPanel.getPuzzle());
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null,"Sudoku Game version 1.4, Developed by [Duong Duy Hung B2103500]", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null,"Luật chơi Sudoku không hề khó nhưng đòi hỏi sự tập trung và khả năng suy luận logic của người chơi. Cụ thể, mục tiêu của bạn là điền các chữ số 1 đến 9 vào các vị trí thích hợp trên bảng lưới 9x9, sao cho:\n\n"+"Nguyên tắc 1: Mỗi hàng ngang, mỗi hàng dọc và mỗi khối con 3x3 đều chứa tất cả 9 chữ số (1 đến 9), không cần theo đúng thứ tự từ thấp đến cao.\n\n" +
"Nguyên tắc 2: Mỗi chữ số chỉ xuất hiện duy nhất 1 lần trong mỗi hàng, mỗi cột và mỗi khối con 3x3.\n\n"+"Đảm bảo hai điều này thì bạn có thể hoàn thành một câu đố Sudoku. \nTuy nhiên, mỗi câu đố thường có nhiều cách giải và đáp án khác nhau. Khi vị trí các con số cho trước trên bảng 9x9 thay đổi thì cách giải đố cũng thay đổi. \nChính sức hấp dẫn, tò mò tìm câu trả lời đã khiến trò chơi trở thành cơn lốc tại Anh kể từ sau năm 2004, khiến khách đi tàu lỡ ga và học sinh thì quên bài tập về nhà.\n"
                        +"", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //JOptionPane.showMessageDialog(null, "Sudoku Game version 1.0, Developed by [Duong Duy Hung B2103500]", "About", JOptionPane.INFORMATION_MESSAGE);

    }


    public SPuzzle returnPuzzle() {
        return sPanel.getPuzzle();
    }

    public String[][] returnBoard() {
        return sPanel.getBoard();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
