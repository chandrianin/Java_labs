package lab_03_chess.Figures;

public class Rook extends Figure {
    // rook - ладья

    public Rook(char color) {
        super("r", color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return (row == row1 && col != col1 || row != row1 && col == col1) &&
                super.canMove(row, col, row1, col1);
    }
}