package sudoku;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

public class Solver extends SwingWorker<Boolean, Object> {
    private Grid grid;

    public Solver(Grid grid) {
        this.grid = grid;
        this.grid.clearGrid(false);
    }

    // The sudoku solver backtracking algorithm
    public Boolean doInBackground() {
        if (grid.checkIfCorrect()) {
            return true;
        }
        Square sq = grid.getFirstBlankSquare();
        for (int i = 1; i <= grid.getDimension(); i++) {
            sq.setValue(i);
            try {
                TimeUnit.MILLISECONDS.sleep(15);// to help the visualization of the backtracking algorithm
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            if (grid.checkIfValid(i, sq)) {
                if (doInBackground())
                    return true;
                else {
                    sq.setValue(0);
                    sq.setText("");
                }
            }
            else {
                sq.setValue(0);
                sq.setText("");
            }
        }
        return false;
    }

    public void done() {
    }

}