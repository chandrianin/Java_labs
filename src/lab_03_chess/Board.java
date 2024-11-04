package lab_03_chess;

import lab_03_chess.Figures.*;

import java.util.Objects;

public class Board {

    private char colorGame;

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public char getColorGame() {
        return colorGame;
    }


    private final Figure[][] fields = new Figure[8][8];


    public void init() {
        this.fields[7] = new Figure[]{
                new Rook('b'), new Knight('b'), new Bishop('b'), new Queen('b'),
                new King('b'), new Bishop('b'), new Knight('b'), new Rook('b'),
        };
        this.fields[6] = new Figure[]{
                new Pawn('b'), new Pawn('b'), new Pawn('b'), new Pawn('b'),
                new Pawn('b'), new Pawn('b'), new Pawn('b'), new Pawn('b')
        };

        this.fields[1] = new Figure[]{
                new Pawn('w'), new Pawn('w'), new Pawn('w'), new Pawn('w'),
                new Pawn('w'), new Pawn('w'), new Pawn('w'), new Pawn('w'),
        };
        this.fields[0] = new Figure[]{
                new Rook('w'), new Knight('w'), new Bishop('w'), new Queen('w'),
                new King('w'), new Bishop('w'), new Knight('w'), new Rook('w'),
        };
    }

    public String getCell(int row, int col) {
        Figure figure = this.fields[row][col];
        if (figure == null) {
            return "    ";
        }
        return " " + figure.getColor() + figure.getName() + " ";
    }

    public void print_board() {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1; row--) {
            System.out.print(row);
            for (int col = 0; col < 8; col++) {
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for (int col = 0; col < 8; col++) {
            System.out.print("    " + col);
        }
    }

    public boolean move_figure(int row, int col, int row1, int col1) {
        // TODO добавить состояние шаха королю
        Figure figure = this.fields[row][col];
        if (figure == null) {
            return false;
        }
        boolean flag = true;
        switch (figure.getName()) { // проверяем, что на пути к новой клетке не стоят другие фигуры
            //TODO первый ход пешки
            case "r": // rook - ладья
                if (row == row1) {
                    for (int i = Math.min(col, col1) + 1; i < Math.max(col, col1); i++) {
                        if (fields[row][i] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else if (col == col1) {
                    for (int i = Math.min(row, row1) + 1; i < Math.max(row, row1); i++) {
                        if (fields[i][col] != null) {
                            flag = false;
                            break;
                        }
                    }
                }
                break;
            case "b": // bishop - слон
                for (int i = 1; i < Math.abs(row - row1); i++) {
                    if (row < row1 && col < col1 && fields[row + i][col + i] != null) {        // I четверть
                        flag = false;
                        break;
                    } else if (row > row1 && col < col1 && fields[row - i][col + i] != null) { // IV четверть
                        flag = false;
                        break;
                    } else if (row > row1 && col > col1 && fields[row - i][col - i] != null) { // III четверть
                        flag = false;
                        break;
                    } else if (row < row1 && col > col1 && fields[row + i][col - i] != null) { // II четверть
                        flag = false;
                        break;
                    }
                }
                break;
            case "Q": // Queen - Королева
                if (row == row1) {
                    for (int i = Math.min(col, col1) + 1; i < Math.max(col, col1); i++) {
                        if (fields[row][i] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else if (col == col1) {
                    for (int i = Math.min(row, row1) + 1; i < Math.max(row, row1); i++) {
                        if (fields[i][col] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    for (int i = 1; i < Math.abs(row - row1); i++) {
                        if (row < row1 && col < col1 && fields[row + i][col + i] != null) {        // I четверть
                            flag = false;
                            break;
                        } else if (row > row1 && col < col1 && fields[row - i][col + i] != null) { // IV четверть
                            flag = false;
                            break;
                        } else if (row > row1 && col > col1 && fields[row - i][col - i] != null) { // III четверть
                            flag = false;
                            break;
                        } else if (row < row1 && col > col1 && fields[row + i][col - i] != null) { // II четверть
                            flag = false;
                            break;
                        }
                    }
                }
                break;
        }
        flag = flag && figure.canMove(row, col, row1, col1);
        if (flag && this.fields[row1][col1] == null && figure.getColor() == this.colorGame) {
            this.fields[row1][col1] = figure;
            this.fields[row][col] = null;
            return true;
        } else if (flag && figure.canAttack(row, col, row1, col1) && this.fields[row1][col1] != null &&
                !Objects.equals(this.fields[row1][col1].getName(), "K") &&
                this.fields[row1][col1].getColor() != this.fields[row][col].getColor()) {
            this.fields[row1][col1] = figure;
            this.fields[row][col] = null;
            return true;
        }
        return false;
    }
}
