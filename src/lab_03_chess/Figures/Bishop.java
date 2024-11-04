package lab_03_chess.Figures;

public class Bishop extends Figure {
    // bishop - слон

    public Bishop(char color) {
        super("b", color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        return Math.abs(row - row1) == Math.abs(col - col1) &&
                super.canMove(row, col, row1, col1);
    }
}
