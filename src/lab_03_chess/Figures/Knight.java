package lab_03_chess.Figures;

public class Knight extends Figure {
    // knight - конь

    public Knight(char color) {
        super("k", color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return (Math.abs(row - row1) == 2 && Math.abs(col - col1) == 1 || Math.abs(row - row1) == 1 && Math.abs(col - col1) == 2) &&
                super.canMove(row, col, row1, col1);
    }
}
