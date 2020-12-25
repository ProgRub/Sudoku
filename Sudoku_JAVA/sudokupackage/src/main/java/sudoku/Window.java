package sudoku;

import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.awt.FileDialog;
import java.awt.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Window extends JFrame implements ActionListener {
    private static final Font FONT = new Font("Times New Roman", Font.CENTER_BASELINE, 14);
    private static final long serialVersionUID = 0;
    private int dimension, squareSize;
    private JButton checkIfCorrect, solveSudoku, resetGrid;
    private JPanel belowGrid;
    private Grid sudokuGrid;
    private JLabel congrats;
    private JMenuBar menuBar;
    private JMenuItem loadGrid, saveGridOnlyClues, saveGridFullProgress, easyGrid, mediumGrid, hardGrid, veryHardGrid;

    public Window(int dimensionIN, int squareSpaceIN) {
        super("Sudoku");

        // Components Creation
        this.dimension = dimensionIN;
        this.squareSize = squareSpaceIN;
        this.belowGrid = new JPanel(new BorderLayout());
        this.checkIfCorrect = new JButton("Is It Correct?");
        this.resetGrid = new JButton("Reset Grid (Only Clues)");
        this.solveSudoku = new JButton("Solve Sudoku");
        this.congrats = new JLabel("Congratulations, you cracked the Sudoku!");
        this.sudokuGrid = new Grid(dimension, squareSize);
        this.menuBar = new JMenuBar();
        JMenu gridMenu = new JMenu("Grid");
        loadGrid = new JMenuItem("Load");
        JMenu saveGridMenu = new JMenu("Save");
        saveGridOnlyClues = new JMenuItem("Only Clues");
        saveGridFullProgress = new JMenuItem("Save All Progress");
        JMenu generateNewGridMenu = new JMenu("Generate New Grid");
        easyGrid = new JMenuItem("Easy");
        mediumGrid = new JMenuItem("Medium");
        hardGrid = new JMenuItem("Hard");
        veryHardGrid = new JMenuItem("Very Hard");

        // Components Configuration
        congrats.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
        congrats.setHorizontalAlignment(JLabel.CENTER);
        congrats.setForeground(Square.CORRECT);
        congrats.setVisible(false);
        loadGrid.addActionListener(this);
        resetGrid.addActionListener(this);
        solveSudoku.addActionListener(this);
        checkIfCorrect.addActionListener(this);
        saveGridOnlyClues.addActionListener(this);
        saveGridFullProgress.addActionListener(this);
        easyGrid.addActionListener(this);
        mediumGrid.addActionListener(this);
        hardGrid.addActionListener(this);
        veryHardGrid.addActionListener(this);
        resetGrid.setFont(FONT);
        solveSudoku.setFont(FONT);
        checkIfCorrect.setFont(FONT);
        easyGrid.setFont(FONT);
        mediumGrid.setFont(FONT);
        hardGrid.setFont(FONT);
        veryHardGrid.setFont(FONT);
        gridMenu.setFont(FONT);
        loadGrid.setFont(FONT);
        saveGridMenu.setFont(FONT);
        saveGridOnlyClues.setFont(FONT);
        saveGridFullProgress.setFont(FONT);
        generateNewGridMenu.setFont(FONT);

        // Window configuration
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);
        setSize(dimension * squareSize, dimension * squareSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setJMenuBar(menuBar);

        // Components placement
        add(congrats, BorderLayout.NORTH);
        add(sudokuGrid, BorderLayout.CENTER);
        add(belowGrid, BorderLayout.SOUTH);
        belowGrid.add(checkIfCorrect, BorderLayout.EAST);
        belowGrid.add(solveSudoku, BorderLayout.WEST);
        belowGrid.add(resetGrid, BorderLayout.CENTER);
        menuBar.add(gridMenu);
        gridMenu.add(loadGrid);
        gridMenu.add(saveGridMenu);
        saveGridMenu.add(saveGridOnlyClues);
        saveGridMenu.add(saveGridFullProgress);
        gridMenu.add(generateNewGridMenu);
        generateNewGridMenu.add(easyGrid);
        generateNewGridMenu.add(mediumGrid);
        generateNewGridMenu.add(hardGrid);
        generateNewGridMenu.add(veryHardGrid);
    }

    public void actionPerformed(ActionEvent e) {
        // To choose a sudoku grid from a file
        if (e.getSource() == loadGrid) {
            congrats.setVisible(false);
            FileDialog chooser = new FileDialog(this, "Choose a File", FileDialog.LOAD);
            chooser.setDirectory(System.getProperty("user.dir"));
            chooser.setFile("*.txt");
            chooser.setVisible(true);
            String filename = chooser.getDirectory() + chooser.getFile();
            if (filename != null) {
                sudokuGrid.setFilename(filename);
            }
        }
        // To check if the grid is correct
        else if (e.getSource() == checkIfCorrect) {
            boolean correct = sudokuGrid.checkIfCorrect();
            if (correct) {
                congrats.setVisible(true);
                revalidate();
            }
        }
        // To solve the sudoku using the backtracking algorithm
        else if (e.getSource() == solveSudoku) {
            new Solver(sudokuGrid).execute();
        }
        // Resets the grid to only show the clues
        else if (e.getSource() == resetGrid) {
            sudokuGrid.clearGrid(false);
        }
        // To save the clues, and only the clues, of the grid
        else if (e.getSource() == saveGridOnlyClues) {
            sudokuGrid.saveGrid(false);
        }
        // To save the grid as it is, the clues and the user inserted values
        else if (e.getSource() == saveGridFullProgress) {
            sudokuGrid.saveGrid(true);
        }
        // Generating a grid of a certain difficulty. Easy difficulty
        else if (e.getSource() == easyGrid) {
            generateGrid(Difficulty.EASY);
        }
        // Medium difficulty
        else if (e.getSource() == mediumGrid) {
            generateGrid(Difficulty.MEDIUM);
        }
        // Hard difficulty
        else if (e.getSource() == hardGrid) {
            generateGrid(Difficulty.HARD);
        }
        // Very Hard difficulty
        else if (e.getSource() == veryHardGrid) {
            generateGrid(Difficulty.VERY_HARD);
        }
    }

    // Method to generate a random sudoku grid of a certain difficulty
    // 1 - Easy
    // 2 - Medium
    // 3 - Hard
    // 4 - Very Hard
    private void generateGrid(Difficulty difficulty) {
        String url = "https://nine.websudoku.com/?level=" + difficulty.difficultyValue;
        sudokuGrid.clearGrid(true);
        try {
            Document page = Jsoup.connect(url).get();
            String id, tdAtributesString, valueAux;
            Element squareElement;
            Elements tdAtributes;
            int value;
            for (int row = 0; row < dimension; row++) {
                for (int column = 0; column < dimension; column++) {
                    id = "c" + Integer.toString(column) + Integer.toString(row);
                    squareElement = page.getElementById(id);
                    tdAtributes = squareElement.select("td");
                    tdAtributesString = tdAtributes.toString();
                    if (tdAtributesString.contains("readonly")) {
                        valueAux = tdAtributesString.substring(tdAtributesString.indexOf("readonly value", 0),
                                tdAtributesString.indexOf("readonly value", 0) + "readonly value=aaa".length());
                        value = Integer
                                .parseInt(valueAux.substring(valueAux.indexOf("\"") + 1, valueAux.lastIndexOf("\"")));
                        sudokuGrid.getGrid()[row][column].setValue(value);
                        sudokuGrid.getGrid()[row][column].setIsClue(true);
                    } else {
                        sudokuGrid.getGrid()[row][column].setValue(0);
                        sudokuGrid.getGrid()[row][column].setIsClue(false);
                    }
                }
            }
            sudokuGrid.setDifficulty(difficulty);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}