package lab_03_chess.Figures;

public class King extends Figure {
    public boolean isFirstStep = true;
    // King - Король

    public King(char color) {
        super("K", color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return (Math.abs(row - row1) <= 1 && Math.abs(col - col1) <= 1) &&
                super.canMove(row, col, row1, col1);
    }
}
