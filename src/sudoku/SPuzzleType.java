package sudoku;

public enum SPuzzleType {
    SIXBYSIX(6, 6, 3, 2, new String[]{"1", "2", "3", "4", "5", "6"}, "6 x 6 Game"),
    NINEBYNINE(9, 9, 3, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, "9 x 9 Game"),
    TWELVEBYTWELVE(12, 12, 4, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, "12 x 12 Game");

    private final int rows;
    private final int columns;
    private final int boxWidth;
    private final int boxHeight;
    private final String[] validValues;
    private final String desc;

    private SPuzzleType(int rows, int columns, int boxWidth, int boxHeight, String[] validValues, String desc) {
        this.rows = rows;
        this.columns = columns;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.validValues = validValues;
        this.desc = desc;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public String[] getValidValues() {
        return validValues;
    }

    public String toString() {
        return desc;
    }
}
