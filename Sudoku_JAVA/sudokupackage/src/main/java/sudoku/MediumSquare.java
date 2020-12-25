package sudoku;

import javax.swing.*;

import java.awt.*;

public class MediumSquare extends JPanel {
    private static final long serialVersionUID = 0;
    private Square[][] squares;
    private int dimension, squareSize;

    public MediumSquare(int dimensionIN, int squareSizeIN, Grid grid) {
        super();

        // Component Creation
        this.dimension = dimensionIN;
        this.squareSize = squareSizeIN;
        this.squares = new Square[dimension][dimension];

        // Panel configuration
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        setPreferredSize(new Dimension(squareSize, squareSize));
        setLayout(new GridLayout(dimension, dimension));
        setVisible(true);
    }

    // Method that adds the square to the 2d grid of the medium square and adds it
    // visually
    public void addSquare(int row, int column, Square sq) {
        this.squares[row][column] = sq;
        add(sq);
    }

    // Method that checks if the value is different than the value of every other
    // square in the medium square
    public boolean checkMediumSquare(int value, Square square) {
        for (Square[] line : squares) {
            for (Square sq : line) {
                if (sq.getValue() == value && sq != square) {
                    return false;
                }
            }
        }
        return true;
    }
}