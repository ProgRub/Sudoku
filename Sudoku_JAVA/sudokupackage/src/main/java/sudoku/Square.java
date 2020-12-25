package sudoku;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;
import java.awt.event.*;

public class Square extends JTextField implements KeyListener, FocusListener {
    private static final long serialVersionUID = 0;
    private static final Font FONT = new Font("Times New Roman", Font.CENTER_BASELINE, 25);
    private int value, row, column;
    private boolean isClue;
    private Grid grid;
    protected static final Color CLUE = new ColorUIResource(1, 5, 56);
    protected static final Color DEFAULT = new ColorUIResource(9, 62, 176);
    protected static final Color DEFAULTBG = new ColorUIResource(155, 158, 155);
    protected static final Color WRONG = Color.RED;
    protected static final Color WRONGBG = new ColorUIResource(245, 71, 80);
    protected static final Color CORRECT = new ColorUIResource(1, 107, 1);

    public Square(int x, int y, Grid grid) {
        super();

        // Component Creation
        this.row = x;
        this.column = y;
        this.grid = grid;
        this.isClue = false;
        this.value = 0;

        // Text Field Configuration
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setHorizontalAlignment(CENTER);
        setBackground(DEFAULTBG);
        setForeground(Square.DEFAULT);
        setHighlighter(null);
        setFont(FONT);
        setEditable(true);
        addKeyListener(this);
        addFocusListener(this);
        setVisible(true);
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean getIsClue() {
        return isClue;
    }

    public int getValue() {
        return value;
    }

    // Setters

    public void setValue(int value, boolean clue) {
        this.value = value;
        setText(this.value != 0 ? Integer.toString(this.value) : "");
        this.isClue = clue;
        setEditable(!isClue);
        setForeground(isClue ? CLUE : DEFAULT);
        setBackground(DEFAULTBG);
    }

    public void setValue(int value) {
        this.value = value;
        setText(this.value != 0 ? Integer.toString(this.value) : "");
    }

    public void setIsClue(boolean isClue) {
        this.isClue = isClue;
        if (this.isClue) {
            setEditable(false);
            setBackground(DEFAULTBG);
            setForeground(CLUE);
        }
    }

    // Method to reset the look of the grid after the user checks the correction of
    // the grid when a square gets focus
    public void focusGained(FocusEvent e) {
        if (grid.getCheckedCorrection()) {
            grid.setCheckedCorrection(false);
            grid.resetSquares();
        }
    }

    // Mandatory implementation
    public void focusLost(FocusEvent e) {

    }

    // Method that restricts the input to the square to the digits (1 through 9) and
    // deletes and resets the value if the backspace is pressed
    public void keyPressed(KeyEvent e) {
        if (value == 0 && ((e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_9)
                || (e.getKeyCode() >= KeyEvent.VK_NUMPAD1 && e.getKeyCode() <= KeyEvent.VK_NUMPAD9))) {
            setEditable(true);
            value = Integer.parseInt(Character.toString(e.getKeyChar()));
        } else {
            if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                setEditable(false);
                e.consume();
                setBackground(DEFAULTBG);
            } else {
                setEditable(true);
                value = 0;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        setEditable(true);
        setBackground(DEFAULTBG);
    }

    // Mandatory implementation
    public void keyTyped(KeyEvent e) {
    }
}