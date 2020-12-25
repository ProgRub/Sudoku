package sudoku;

public enum Difficulty {
    EASY(0), MEDIUM(1), HARD(2), VERY_HARD(3);

    protected final int difficultyValue;

    private Difficulty(int value) {
        this.difficultyValue = value;
    }
}