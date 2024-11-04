package lab_03_chess;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        board.init();
        board.setColorGame('w');

        System.out.println();

        boolean isGame = true;
        Scanner in = new Scanner(System.in);


        while (isGame) {
            // TODO Добавить конец игры
            board.print_board();
            System.out.println();

            System.out.println("Управление: ");
            System.out.println("Ход фигуры из клетки (row, col) в (row1, col1)");
            System.out.println("> row col row1 col1");

            switch (board.getColorGame()) {
                case 'w':
                    System.out.println("Ход белых");
                    System.out.print("> ");
                    break;
                case 'b':
                    System.out.println("Ход черных");
                    System.out.print("> ");
                    break;
            }

            String inputLine = in.nextLine();
            int row, col, row1, col1;
            String[] coords = inputLine.split(" ");
            row = Integer.parseInt(coords[0]);
            col = Integer.parseInt(coords[1]);
            row1 = Integer.parseInt(coords[2]);
            col1 = Integer.parseInt(coords[3]);

            while (!board.move_figure(row, col, row1, col1)) {
                System.out.println("Ошибка! Повторите ход фигуры!");
                switch (board.getColorGame()) {
                    case 'w':
                        System.out.println("Ход белых");
                        System.out.print("> ");
                        break;
                    case 'b':
                        System.out.println("Ход черных");
                        System.out.print("> ");
                        break;
                }
                inputLine = in.nextLine();
                coords = inputLine.split(" ");
                row = Integer.parseInt(coords[0]);
                col = Integer.parseInt(coords[1]);
                row1 = Integer.parseInt(coords[2]);
                col1 = Integer.parseInt(coords[3]);
            }

            switch (board.getColorGame()) {
                case 'w':
                    board.setColorGame('b');
                    break;
                case 'b':
                    board.setColorGame('w');
                    break;
            }
        }
    }
}