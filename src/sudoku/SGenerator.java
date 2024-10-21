package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SGenerator {

    public SPuzzle generateRandomSudoku(SPuzzleType puzzleType) {
        SPuzzle puzzle = new SPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
        SPuzzle copy = new SPuzzle(puzzle);

        Random randomGenerator = new Random();

        List<String> notUsedValidValues = new ArrayList<String>(Arrays.asList(copy.getValidValues()));
        for (int r = 0; r < copy.getNumRows(); r++) {
            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
            copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
            notUsedValidValues.remove(randomValue);
        }

        //Bottleneck here need to improve this so that way 16x16 puzzles can be generated
        backtrackSudokuSolver(0, 0, copy);

        //0.22222
        // 0.3 is radom value
        int numberOfValuesToKeep = (int) (0.3 * (copy.getNumRows() * copy.getNumRows()));
        

        for (int i = 0; i < numberOfValuesToKeep;) {
            int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
            int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());

            if (puzzle.isSlotAvailable(randomRow, randomColumn)) {
                puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
                i++;
            }
        }

        return puzzle;
    }

    private boolean backtrackSudokuSolver(int r, int c, SPuzzle puzzle) {
        //If the move is not valid return false
        if (!puzzle.inRange(r, c)) {
            return false;
        }

        //if the current space is empty
        if (puzzle.isSlotAvailable(r, c)) {

            //loop to find the correct value for the space
            for (int i = 0; i < puzzle.getValidValues().length; i++) {

                //if the current number works in the space
                if (!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c, puzzle.getValidValues()[i]) && !puzzle.numInBox(r, c, puzzle.getValidValues()[i])) {

                    //make the move
                    puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);

                    //if puzzle solved return true
                    if (puzzle.boardFull()) {
                        return true;
                    }

                    //go to next move
                    if (r == puzzle.getNumRows() - 1) {
                        if (backtrackSudokuSolver(0, c + 1, puzzle)) {
                            return true;
                        }
                    } else {
                        if (backtrackSudokuSolver(r + 1, c, puzzle)) {
                            return true;
                        }
                    }
                }
            }
        } //if the current space is not empty
        else {
            //got to the next move
            if (r == puzzle.getNumRows() - 1) {
                return backtrackSudokuSolver(0, c + 1, puzzle);
            } else {
                return backtrackSudokuSolver(r + 1, c, puzzle);
            }
        }

        //undo move
        puzzle.makeSlotEmpty(r, c);

        //backtrack
        return false;
    }
}
