package sudoku;

import java.util.Arrays;
import javax.swing.JOptionPane;

public class SPuzzle {

    protected String[][] board;
    // Table to determine if a slot is mutable
    protected boolean[][] mutable;
    private final int ROWS;
    private final int COLUMNS;
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final String[] VALIDVALUES;

    public SPuzzle(int rows, int columns, int boxWidth, int boxHeight, String[] validValues) {
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOXWIDTH = boxWidth;
        this.BOXHEIGHT = boxHeight;
        this.VALIDVALUES = validValues;
        this.board = new String[ROWS][COLUMNS];
        this.mutable = new boolean[ROWS][COLUMNS];
        initializeBoard();
        initializeMutableSlots();
    }

    public SPuzzle(SPuzzle puzzle) {
        this.ROWS = puzzle.ROWS;
        this.COLUMNS = puzzle.COLUMNS;
        this.BOXWIDTH = puzzle.BOXWIDTH;
        this.BOXHEIGHT = puzzle.BOXHEIGHT;
        this.VALIDVALUES = puzzle.VALIDVALUES;
        this.board = new String[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                board[r][c] = puzzle.board[r][c];
            }
        }
        this.mutable = new boolean[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                this.mutable[r][c] = puzzle.mutable[r][c];
            }
        }
    }

    public SPuzzle(String[][] puzzle) {
        this.ROWS = puzzle.length;
        this.COLUMNS = puzzle[0].length;
        this.BOXWIDTH = (int) Math.sqrt(this.COLUMNS);
        this.BOXHEIGHT = (int) Math.sqrt(this.ROWS);
        this.VALIDVALUES = new String[this.ROWS * this.COLUMNS];
        this.board = new String[this.ROWS][this.COLUMNS];
        this.mutable = new boolean[this.ROWS][this.COLUMNS];

        // Initialize valid values
        for (int i = 0; i < this.VALIDVALUES.length; i++) {
            this.VALIDVALUES[i] = String.valueOf((char) (i + '1'));
        }

        // Initialize board and mutable slots
        for (int row = 0; row < this.ROWS; row++) {
            System.arraycopy(puzzle[row], 0, this.board[row], 0, this.COLUMNS);
            Arrays.fill(this.mutable[row], true);
        }
    }

    public int getNumRows() {
        return this.ROWS;
    }

    public int getNumColumns() {
        return this.COLUMNS;
    }

    public int getBoxWidth() {
        return this.BOXWIDTH;
    }

    public int getBoxHeight() {
        return this.BOXHEIGHT;
    }

    public String[] getValidValues() {
        return this.VALIDVALUES;
    }

    public void makeMove(int row, int col, String value, boolean isMutable) {
        if (this.isValidValue(value) && this.isValidMove(row, col, value) && this.isSlotMutable(row, col)) {
            this.board[row][col] = value;
            this.mutable[row][col] = isMutable;
        }
    }

    public boolean isValidMove(int row, int col, String value) {
        if (this.inRange(row, col)) {
            if (!this.numInCol(col, value) && !this.numInRow(row, value) && !this.numInBox(row, col, value)) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(null,
                "Lỗi: Bạn đã nhập giá trị không hợp lệ!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public boolean isValidMoveooo(int row, int col, String value) {
        if (this.inRange(row, col)) {
            if (!this.numInCol(col, value)) {
                if (!this.numInRow(row, value)) {
                    if (!this.numInBox(row, col, value)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Lỗi: Bạn đã nhập giá trị không hợp lệ!\n Trùng trong 3x3",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi: Bạn đã nhập giá trị không hợp lệ!\n Trùng hàng",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            } else {
                JOptionPane.showMessageDialog(null,
                        "Lỗi: Bạn đã nhập giá trị không hợp lệ!\n Trùng cột",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        JOptionPane.showMessageDialog(null,
                "Lỗi: Bạn đã nhập giá trị không hợp lệ!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public boolean numInCol(int col, String value) {
        if (col <= this.COLUMNS) {
            for (int row = 0; row < this.ROWS; row++) {
                if (this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInRow(int row, String value) {
        if (row <= this.ROWS) {
            for (int col = 0; col < this.COLUMNS; col++) {
                if (this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInBox(int row, int col, String value) {
        if (this.inRange(row, col)) {
            int boxRow = row / this.BOXHEIGHT;
            int boxCol = col / this.BOXWIDTH;

            int startingRow = (boxRow * this.BOXHEIGHT);
            int startingCol = (boxCol * this.BOXWIDTH);

            for (int r = startingRow; r <= (startingRow + this.BOXHEIGHT) - 1; r++) {
                for (int c = startingCol; c <= (startingCol + this.BOXWIDTH) - 1; c++) {
                    if (this.board[r][c].equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSlotAvailable(int row, int col) {
        return (this.inRange(row, col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
    }

    public boolean isSlotMutable(int row, int col) {
        if (this.mutable[row][col] == false) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi: Đây là bài toán. Không thể thay đổi đơn vị này",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        };
        return this.mutable[row][col];
    }

    public String getValue(int row, int col) {
        if (this.inRange(row, col)) {
            return this.board[row][col];
        }
        return "";
    }

    public String[][] getBoard() {
        return this.board;
    }

    public boolean isValidValue(String value) {
        for (String str : this.VALIDVALUES) {
            if (str.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean inRange(int row, int col) {
        return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }

    public boolean boardFull() {
        for (int r = 0; r < this.ROWS; r++) {
            for (int c = 0; c < this.COLUMNS; c++) {
                if (this.board[r][c].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeSlotEmpty(int row, int col) {
        this.board[row][col] = "";
    }

    @Override
    public String toString() {
        String str = "Game Board:\n";
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                str += this.board[row][col] + " ";
            }
            str += "\n";
        }
        return str + "\n";
    }

    private void initializeBoard() {
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                this.board[row][col] = "";
            }
        }
    }

    private void initializeMutableSlots() {
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                this.mutable[row][col] = true;
            }
        }
    }

}
