package lab_03_chess.Figures;

public class Pawn extends Figure {
    // pawn - пешка

    private boolean isFirstStep = true;

    public Pawn(char color) {
        super("p", color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (this.isFirstStep) {
            if (((((row + 2 == row1) || (row + 1 == row1)) && this.getColor() == 'w') ||
                    (((row - 2 == row1) || (row - 1 == row1)) && this.getColor() == 'b')) && (col == col1) &&
                    super.canMove(row, col, row1, col1)) {
                this.isFirstStep = false;
                return true;
            }
        } else {
            return ((((row + 1 == row1)) && this.getColor() == 'w') || (((row - 1 == row1)) && this.getColor() == 'b')) && (col == col1) &&
                    super.canMove(row, col, row1, col1);
        }
        return false;
    }


    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        return Math.abs(col - col1) == 1 && ((row - row1 == 1 && this.getColor() == 'b') || (row - row1 == -1 && this.getColor() == 'w')) &&
                super.canMove(row, col, row1, col1);
    }
}
