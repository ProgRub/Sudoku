package sudoku;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Grid extends JPanel {
    private static final long serialVersionUID = 0;
    private Square[][] grid;
    private MediumSquare[][] mediumSquares;
    private int dimension, squareSize;
    private String filename;
    private boolean checkedCorrection;
    private Difficulty difficulty;

    public Grid(int dimensionIN, int squareSizeIN) {
        super();

        // Component creation
        this.checkedCorrection = false;
        this.dimension = dimensionIN;
        this.squareSize = squareSizeIN;
        this.grid = new Square[dimension][dimension];
        int mediumSquareDimension = (int) Math.sqrt(dimension);
        this.mediumSquares = new MediumSquare[mediumSquareDimension][mediumSquareDimension];

        // Panel Configuration
        setLayout(new GridLayout(mediumSquareDimension, mediumSquareDimension));
        setVisible(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Component Placement
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                Square sq = new Square(x, y, this);
                grid[x][y] = sq;
                try {
                    mediumSquares[x / 3][y / 3].addSquare(x % 3, y % 3, sq);
                } catch (NullPointerException e) {
                    MediumSquare medSquare = new MediumSquare(mediumSquareDimension, squareSize, this);
                    mediumSquares[x / 3][y / 3] = medSquare;
                    mediumSquares[x / 3][y / 3].addSquare(x % 3, y % 3, sq);
                    add(medSquare);
                }
            }
        }
    }

    // Getters
    public Square[][] getGrid() {
        return grid;
    }

    public int getDimension() {
        return dimension;
    }

    public Square getFirstBlankSquare() {
        for (Square[] line : grid) {
            for (Square sq : line) {
                if (sq.getValue() == 0 && !sq.getIsClue())
                    return sq;
            }
        }
        return null;
    }

    public boolean getCheckedCorrection() {
        return checkedCorrection;
    }

    // Setters

    public void setCheckedCorrection(boolean value) {
        checkedCorrection = value;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        loadGridFromFile();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // Method to clear the grid, except for the clues, used before the backtracking
    // algorithm commences
    public void clearGrid(boolean allSquares) {
        for (Square[] line : grid) {
            for (Square sq : line) {
                if (allSquares || !sq.getIsClue()) {
                    sq.setValue(0);
                    sq.setText("");
                }
            }
        }
    }

    // Method to reset the visual appearance of the squares, except for the clues,
    // because those cannot be edited
    public void resetSquares() {
        for (Square[] line : grid) {
            for (Square sq : line) {
                if (!sq.getIsClue()) {
                    sq.setForeground(Square.DEFAULT);
                    sq.setBackground(Square.DEFAULTBG);
                }
            }
        }
    }

    // Method to load a sudoku grid from a file
    public void loadGridFromFile() {
        try {
            clearGrid(true);
            FileReader inStream = new FileReader(new File(filename));
            BufferedReader reader = new BufferedReader(inStream);
            String line = reader.readLine();
            int row, column;
            char[] lineInChars;
            row = 0;
            while (line != null) {
                column = 0;
                lineInChars = line.trim().toCharArray();
                for (int index = 0; index < lineInChars.length; index += 2) {
                    grid[row][column].setIsClue(lineInChars[index + 1] == 'T');
                    grid[row][column].setValue(lineInChars[index] - 48, lineInChars[index + 1] == 'T');
                    column++;
                }
                column = 0;
                row++;
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            //e.printStackTrace();
        } 
    }

    // Method to check if the number is different than every other number in its
    // column
    public boolean checkRows(int value, Square sq) {
        int column = sq.getColumn();
        for (int i = 0; i < dimension; i++) {
            if (grid[i][column].getValue() == value && grid[i][column] != sq) {
                return false;
            }
        }
        return true;
    }

    // Method to check if the number is different than every other number in its
    // row
    public boolean checkColumns(int value, Square sq) {
        int row = sq.getRow();
        for (int i = 0; i < dimension; i++) {
            if (grid[row][i].getValue() == value && grid[row][i] != sq) {
                return false;
            }
        }
        return true;
    }

    // Method to check if the number is different than every other number in its
    // "medium square"
    public boolean check3x3Square(int value, Square sq) {
        return mediumSquares[sq.getRow() / 3][sq.getColumn() / 3].checkMediumSquare(value, sq);
    }

    // Method to check if the number is valid in all three senses, different than
    // every other number in its row, column and "medium square"
    public boolean checkIfValid(int value, Square sq) {
        return checkRows(value, sq) && checkColumns(value, sq) && check3x3Square(value, sq);
    }

    // Method that checks if the grid is solved and changes the look of the squares
    // accordingly
    public boolean checkIfCorrect() {
        resetSquares();
        checkedCorrection = true;
        boolean result = true;
        for (Square[] line : grid) {
            for (Square sq : line) {
                if (!sq.getIsClue()) {
                    if (sq.getValue() == 0) {
                        sq.setBackground(Square.WRONGBG);
                        result = false;
                    } else if (!checkIfValid(sq.getValue(), sq)) {
                        sq.setForeground(Square.WRONG);
                        result = false;
                    } else
                        sq.setForeground(Square.CORRECT);
                }
            }
        }
        return result;
    }

    public void saveGrid(boolean fullProgress) {
        File directory = new File("SudokuGrids" + File.separator + difficulty.name().substring(0, 1).toUpperCase()+difficulty.name().substring(1).toLowerCase());
        if (!directory.exists())
            directory.mkdirs();
        File path = new File(directory + File.separator + "Sudoku" + Integer.toString(directory.list().length + 1) + ".txt");
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bW = new BufferedWriter(fw);
            PrintWriter output = new PrintWriter(bW);
            for (Square[] line : grid) {
                for (Square sq : line) {
                    output.print((fullProgress || sq.getIsClue() ? sq.getValue() : "0") + (sq.getIsClue() ? "T" : "F"));
                }
                output.println();
            }
            output.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
