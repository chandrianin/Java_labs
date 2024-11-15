package lab_03_chess;

import lab_03_chess.Figures.*;

import java.util.Objects;
import java.util.Scanner;

public class Board {

    private char _colorGame;

    /**
     * Массив всех возможных состояний игры для вывода в консоль
     */
    public final String[] textToOut = {"Мат", "Шах Королю!", "Допустимый ход!",
            "Измените ход!\nНельзя оставлять Короля под шахом", "Измените ход!", "Выберите свою фигуру!",
            "Измените ход!\nФигура не может пройти в требуемую клетку"};

    /**
     * Индекс, после которого в <i>textToOut</i> начинаются состояния ошибок хода
     */
    public final int separator = 2;

    /**
     * Индекс состояния <b>шах и мат</b>
     */
    public final int mate = 0;


    public void setColorGame(char _colorGame) {
        this._colorGame = _colorGame;
    }

    public char getColorGame() {
        return _colorGame;
    }

    private final int[] _blackKingCoordinates = {7, 4};
    private final int[] _whiteKingCoordinates = {0, 4};

    private final Figure[][] _fields = new Figure[8][8];


    public void init() {
        this._fields[7] = new Figure[]{
                new Rook('b'), new Knight('b'), new Bishop('b'), new Queen('b'),
                new King('b'), new Bishop('b'), new Knight('b'), new Rook('b'),
        };
        this._fields[6] = new Figure[]{
                new Pawn('b'), new Pawn('b'), new Pawn('b'), new Pawn('b'),
                new Pawn('b'), new Pawn('b'), new Pawn('b'), new Pawn('b')
        };


        this._fields[1] = new Figure[]{
                new Pawn('w'), new Pawn('w'), new Pawn('w'), new Pawn('w'),
                new Pawn('w'), new Pawn('w'), new Pawn('w'), new Pawn('w'),
        };
        this._fields[0] = new Figure[]{
                new Rook('w'), new Knight('w'), new Bishop('w'), new Queen('w'),
                new King('w'), new Bishop('w'), new Knight('w'), new Rook('w'),
        };
    }

    public String getCell(int row, int col) {
        Figure figure = this._fields[row][col];
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


    /**
     * @param row  строка, на которой находится выбранная фигура
     * @param col  столбец, на котором находится выбранная фигура
     * @param row1 строка, куда направится выбранная фигура
     * @param col1 столбец, куда направится выбранная фигура
     * @return Индекс, по которому необходимо обратиться к массиву {@link Board#textToOut} для получения статуса хода
     */
    public int move_figure(int row, int col, int row1, int col1) {

        // проверяем выбрал ли пользователь доступную для хода фигуру
        if (_fields[row][col] == null || _fields[row][col].getColor() != _colorGame) {
            return separator + 3;
        }

        Figure figure = _fields[row][col];
//
//        // Рокировка
//        if (figure.getName() == "K" && ((King) figure).isFirstStep && row == row1 && Math.abs(col - col1) == 3) {
//            Figure rightRook = _fields[row][7];
//            Figure leftRook = _fields[row][0];
//            if (col < col1 && _fields[row][col + 1] == null && _fields[row][col + 2] == null // рокировка с правой ладьей
//                    && rightRook != null && rightRook.getName() == "R" && ((Rook) rightRook).isFirstStep) {
//
//            }
//        }

        // фигура должна ходить в пустую клетку или атаковать фигуру другого цвета
        boolean flag = !figuresInWay(row, col, row1, col1) && (figure.canMove(row, col, row1, col1) && _fields[row1][col1] == null ||
                figure.canAttack(row, col, row1, col1) && _fields[row1][col1] != null && _fields[row1][col1].getColor() != _colorGame);

        if (!flag) {
            return separator + 4; // неправильно выбрана точка назначения
        } else { // если фигура ничего не мешает, то сделать ход
            Figure temp = _fields[row1][col1];
            move(row, col, row1, col1);
            if (_checkToKing(_colorGame)) { // если из-за нового хода возникает шах королю текущего цвета, то отмена хода
                _fields[row][col] = _fields[row1][col1];
                _fields[row1][col1] = temp;
                return separator + 1; // невозможно идти под шах
            }
            char anotherColor = switch (_colorGame) {
                case 'b':
                    yield 'w';
                case 'w':
                    yield 'b';
                default:
                    throw new IllegalStateException("Unexpected _colorGame value: " + _colorGame);
            };

            if (_checkMate(anotherColor)) { // проверяем не поставлен ли ходом мат
                textToOut[mate] = switch (_colorGame) {
                    case 'b':
                        yield "Выиграли чёрные!";
                    case 'w':
                        yield "Выиграли белые!";
                    default:
                        throw new IllegalStateException("Unexpected value: " + _colorGame);
                };
                return mate; // мат
            } else if (_checkToKing(anotherColor)) { // проверяем, не поставлен ли новым ходом шах
                return separator - 1;
            } else {
                if (figure.getName() == "K" && ((King) figure).isFirstStep) {
                    ((King) figure).isFirstStep = false;
                } else if (figure.getName() == "R" && ((Rook) figure).isFirstStep) {
                    ((Rook) figure).isFirstStep = false;
                }
                return separator; // успешный ход
            }
        }
    }

    private void move(int row, int col, int row1, int col1) {
        _fields[row1][col1] = _fields[row][col];
        _fields[row][col] = null;
        if (row1 == 7 && _fields[row1][col1].getColor() == 'w' || row1 == 0 && _fields[row1][col1].getColor() == 'b') {
            changePawnTo(row1, col1);
        } else if (Objects.equals(_fields[row1][col1].getName(), "K")) {
            if (_colorGame == 'b') {
                _blackKingCoordinates[0] = row1;
                _blackKingCoordinates[1] = col1;
            } else {
                _whiteKingCoordinates[0] = row1;
                _whiteKingCoordinates[1] = col1;
            }
        }
    }

    /**
     * Метод проверяет, находится ли Король выбранного цвета под шахом
     *
     * @return True, если Король под шахом<br>False, если шаха Королю нет
     */
    private boolean _checkToKing(char color) {
        int kingRow;
        int kingCol;

        switch (color) {
            case 'b':
                kingRow = _blackKingCoordinates[0];
                kingCol = _blackKingCoordinates[1];
                break;
            case 'w':
                kingRow = _whiteKingCoordinates[0];
                kingCol = _whiteKingCoordinates[1];
                break;
            default:
                throw new IllegalStateException("Unexpected color value in _checkToKing: " + color);
        }
        for (int row = 0; row < _fields.length; row++) {
            for (int col = 0; col < _fields[0].length; col++) {
                if (_fields[row][col] != null && _fields[row][col].getColor() != color &&
                        _fields[row][col].canAttack(row, col, kingRow, kingCol) &&
                        !figuresInWay(row, col, kingRow, kingCol)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод проверяет, настал ли шах и мат
     *
     * @return True, если мат текущему игроку<br>False, если мата нет
     */
    private boolean _checkMate(char color) {
        if (!_checkToKing(color)) {
            return false;
        }
        int kingRow;
        int kingCol;

        switch (color) {
            case 'b':
                kingRow = _blackKingCoordinates[0];
                kingCol = _blackKingCoordinates[1];
                break;
            case 'w':
                kingRow = _whiteKingCoordinates[0];
                kingCol = _whiteKingCoordinates[1];
                break;
            default:
                throw new RuntimeException("Неизвестный цвет");
        }

        // Король может уйти в сторону от шаха?
        boolean escapeFlag = false;
        for (int i = Math.max(kingRow - 1, 0); i <= Math.min(kingRow + 1, 7); i++) {
            for (int j = Math.max(kingCol - 1, 0); j <= Math.min(kingCol + 1, 7); j++) {
                if ((_fields[i][j] == null || _fields[i][j] != null && _fields[i][j].getColor() != color)) {
                    Figure temp = _fields[i][j];
                    _fields[i][j] = _fields[kingRow][kingCol];
                    _fields[kingRow][kingCol] = null;
                    if (color == 'b') {
                        _blackKingCoordinates[0] = i;
                        _blackKingCoordinates[1] = j;
                    } else {
                        _whiteKingCoordinates[0] = i;
                        _whiteKingCoordinates[1] = j;
                    }

                    if (!_checkToKing(color)) {
                        _fields[kingRow][kingCol] = _fields[i][j];
                        _fields[i][j] = temp;
                        if (color == 'b') {
                            _blackKingCoordinates[0] = kingRow;
                            _blackKingCoordinates[1] = kingCol;
                        } else {
                            _whiteKingCoordinates[0] = kingRow;
                            _whiteKingCoordinates[1] = kingCol;
                        }
                        escapeFlag = true;
                        break;
                    }

                    _fields[kingRow][kingCol] = _fields[i][j];
                    _fields[i][j] = temp;
                    if (color == 'b') {
                        _blackKingCoordinates[0] = kingRow;
                        _blackKingCoordinates[1] = kingCol;
                    } else {
                        _whiteKingCoordinates[0] = kingRow;
                        _whiteKingCoordinates[1] = kingCol;
                    }
                }
            }
            if (escapeFlag) {
                break;
            }
        }


        // Кто-нибудь может защитить короля или срубить атакующего?
        boolean defenseFlag = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (_fields[i][j] != null && _fields[i][j].getColor() == color) { // выбираем фигуру-защитника Короля
                    for (int row = 0; row < 8; row++) {
                        for (int col = 0; col < 8; col++) {
                            if ((_fields[row][col] == null && _fields[i][j].canMove(i, j, row, col) || _fields[row][col] != null && _fields[row][col].getColor() != color && _fields[i][j].canAttack(i, j, row, col))
                                    && !figuresInWay(i, j, row, col)) { // может ли защитник закрыть Короля или срубить атакующего
                                Figure temp = _fields[row][col];
                                _fields[row][col] = _fields[i][j];
                                _fields[i][j] = null;
                                if (_fields[row][col] != null && Objects.equals(_fields[row][col].getName(), "K")) {
                                    if (color == 'b') {
                                        _blackKingCoordinates[0] = row;
                                        _blackKingCoordinates[1] = col;
                                    } else {
                                        _whiteKingCoordinates[0] = row;
                                        _whiteKingCoordinates[1] = col;
                                    }
                                }
                                if (!_checkToKing(color)) {
                                    _fields[i][j] = _fields[row][col];
                                    _fields[row][col] = temp;
                                    if (_fields[row][col] != null && Objects.equals(_fields[row][col].getName(), "K")) {
                                        if (color == 'b') {
                                            _blackKingCoordinates[0] = i;
                                            _blackKingCoordinates[1] = j;
                                        } else {
                                            _whiteKingCoordinates[0] = i;
                                            _whiteKingCoordinates[1] = j;
                                        }
                                    }
                                    defenseFlag = true;
                                    break;
                                }
                                _fields[i][j] = _fields[row][col];
                                _fields[row][col] = temp;
                                if (_fields[row][col] != null && Objects.equals(_fields[row][col].getName(), "K")) {
                                    if (color == 'b') {
                                        _blackKingCoordinates[0] = i;
                                        _blackKingCoordinates[1] = j;
                                    } else {
                                        _whiteKingCoordinates[0] = i;
                                        _whiteKingCoordinates[1] = j;
                                    }
                                }
                            }
                        }
                        if (defenseFlag) {
                            break;
                        }
                    }
                }
                if (defenseFlag) {
                    break;
                }
            }
            if (defenseFlag) {
                break;
            }
        }
        return !(escapeFlag || defenseFlag);
    }

    /**
     * Метод проверяет, не мешает ли выбранной фигуре другая фигура по пути для хода
     *
     * @return True, если другая фигура мешает<br>False, если другая фигура не мешает
     */
    private boolean figuresInWay(int row, int col, int row1, int col1) {
        Figure figure = _fields[row][col];
        boolean flag = true;
        switch (figure.getName()) { // проверяем, что на пути к новой клетке не стоят другие фигуры
            case "r": // rook - ладья
                if (row == row1) {
                    for (int i = Math.min(col, col1) + 1; i < Math.max(col, col1); i++) {
                        if (_fields[row][i] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else if (col == col1) {
                    for (int i = Math.min(row, row1) + 1; i < Math.max(row, row1); i++) {
                        if (_fields[i][col] != null) {
                            flag = false;
                            break;
                        }
                    }
                }
                break;
            case "b": // bishop - слон
                for (int i = 1; i < Math.abs(row - row1); i++) {
                    if (row < row1 && col < col1 && _fields[row + i][col + i] != null) {        // I четверть
                        flag = false;
                        break;
                    } else if (row > row1 && col < col1 && _fields[row - i][col + i] != null) { // IV четверть
                        flag = false;
                        break;
                    } else if (row > row1 && col > col1 && _fields[row - i][col - i] != null) { // III четверть
                        flag = false;
                        break;
                    } else if (row < row1 && col > col1 && _fields[row + i][col - i] != null) { // II четверть
                        flag = false;
                        break;
                    }
                }
                break;
            case "Q": // Queen - Королева
                if (row == row1) {
                    for (int i = Math.min(col, col1) + 1; i < Math.max(col, col1); i++) {
                        if (_fields[row][i] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else if (col == col1) {
                    for (int i = Math.min(row, row1) + 1; i < Math.max(row, row1); i++) {
                        if (_fields[i][col] != null) {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    for (int i = 1; i < Math.abs(row - row1); i++) {
                        if (row < row1 && col < col1 && _fields[row + i][col + i] != null) {        // I четверть
                            flag = false;
                            break;
                        } else if (row > row1 && col < col1 && _fields[row - i][col + i] != null) { // IV четверть
                            flag = false;
                            break;
                        } else if (row > row1 && col > col1 && _fields[row - i][col - i] != null) { // III четверть
                            flag = false;
                            break;
                        } else if (row < row1 && col > col1 && _fields[row + i][col - i] != null) { // II четверть
                            flag = false;
                            break;
                        }
                    }
                }
                break;
            case "p": // первый ход пешки
                if ((row == 1 && figure.getColor() == 'w' || row == 6 && figure.getColor() == 'b') && (Math.abs(row - row1) == 2) &&
                        (figure.getColor() == 'b' && _fields[row - 1][col] != null ||
                                figure.getColor() == 'w' && _fields[row + 1][col] != null)) {
                    flag = false;
                }
        }
        return !flag;
    }

    /**
     * Метод для инициации выбора пользователем фигуры на замену дошедшей до конца пешке
     *
     * @param row1 номер строки, куда направляется пешка
     * @param col1 номер столбца, куда направляется пешка
     */
    private void changePawnTo(int row1, int col1) {
        Scanner in = new Scanner(System.in);
        System.out.println("Пешка дошла до конца. Выберите новую фигуру!");
        System.out.println(_colorGame + "Q" + ", " + _colorGame + "r" + ", " + _colorGame + "k" + ", " + _colorGame + "b");
        String newFigureLine = in.nextLine().toLowerCase();
        switch (newFigureLine.charAt(newFigureLine.length() - 1)) {
            case 'q':
                _fields[row1][col1] = new Queen(_colorGame);
                break;
            case 'r':
                _fields[row1][col1] = new Rook(_colorGame);
                break;
            case 'k':
                _fields[row1][col1] = new Knight(_colorGame);
                break;
            case 'b':
                _fields[row1][col1] = new Bishop(_colorGame);
                break;
        }
    }
}