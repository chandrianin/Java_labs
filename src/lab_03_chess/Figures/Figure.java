package lab_03_chess.Figures;

public abstract class Figure {
    private final String name;
    private final char color;

    public Figure(String name, char color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public final char getColor() {
        return color;
    }

    public boolean canMove(int row, int col, int row1, int col1) {
        return (row >= 0 && row < 8) && (col >= 0 && col < 8) &&
                (row1 >= 0 && row1 < 8) && (col1 >= 0 && col1 < 8) &&
                ((col != col1) || (row != row1));
    }

    public boolean canAttack(int row, int col, int row1, int col1) {
        return this.canMove(row, col, row1, col1);
    }
}
