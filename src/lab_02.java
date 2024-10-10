import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Character.toLowerCase;

public class lab_02 {
    final Scanner _in = new Scanner(System.in);

    public String stringInput() {
        System.out.print("Введите строку:\n");
        return _in.nextLine();
    }

    public int intInput() {
        System.out.print("Введите число: ");
        if (_in.hasNextInt()) {
            return _in.nextInt();
        }
        throw new RuntimeException("Необходимо ввести числовое значение");
    }

    public int[] intArrayInput() {
        int[] inputArray;
        System.out.print("Введите количество чисел массива: ");
        int n_1 = _in.nextInt();
        inputArray = new int[n_1];
        System.out.print("Введите элементы массива: ");
        for (int i = 0; i < n_1; i++) {
            if (_in.hasNextInt()) {
                inputArray[i] = _in.nextInt();
            }
        }
        return inputArray;
    }

    public int[][] twoDimensionalIntArrayInput() {
        int[][] target;
        System.out.print("Введите количество строк: ");
        int rows = _in.nextInt();
        if (rows < 1) {
            throw new RuntimeException("В массиве должна быть хотя бы одна строка");
        }
        System.out.print("Введите количество столбцов: ");
        int columns = _in.nextInt();
        if (columns < 1) {
            throw new RuntimeException("В массиве должен быть хотя бы один столбец");
        }
        target = new int[rows][columns];
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            System.out.print((rowIndex + 1) + ": ");
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                if (_in.hasNextInt()) {
                    target[rowIndex][columnIndex] = _in.nextInt();
                }
            }
        }
        return target;
    }

    public String task_1(String inputLine) {
        StringBuilder result = new StringBuilder();
        char[] maxSubLine = new char[inputLine.length()];
        char[] currentSubLine = new char[inputLine.length()];
        int currentSubLineIndex = 0;
        int firstSymbolIndex;
        int realMaxSubLineSize = 0;
        for (int i = 0; i < inputLine.length(); i++) {
            boolean flag = true;
            char newSymbol = inputLine.charAt(i);
            for (int j = 0; j < currentSubLineIndex; j++) {
                if (toLowerCase(currentSubLine[j]) == toLowerCase(newSymbol)) {
                    firstSymbolIndex = i - currentSubLineIndex;
                    i = firstSymbolIndex;

                    currentSubLine = new char[inputLine.length() - firstSymbolIndex];
                    currentSubLineIndex = 0;
                    flag = false;
                }
            }
            if (flag) {
                currentSubLine[currentSubLineIndex++] = newSymbol;
                if (realMaxSubLineSize <= currentSubLineIndex) {
                    maxSubLine = currentSubLine;
                    realMaxSubLineSize = currentSubLineIndex;
                }
            }
        }

        for (int i = 0; i < realMaxSubLineSize; i++) {
            result.append(maxSubLine[i]);
        }
        return result.toString();
    }

    public String task_2(int[] firstArray, int[] secondArray) {
        int n_1 = firstArray.length;
        int n_2 = secondArray.length;
        int[] resultArray = new int[n_1 + n_2];
        for (int i = 0; i < n_1; i++) {
            resultArray[i] = firstArray[i];
        }
        for (int i = 0; i < n_1; i++) {
            resultArray[i + n_1] = secondArray[i];
        }
        Arrays.sort(resultArray);
        StringBuilder result = new StringBuilder();
        for (int number : resultArray) {
            result.append(number).append(" ");
        }
        return result.toString();
    }

    public String task_3(int[] numbers) {
        if (numbers.length == 0) {
            return "0";
        }
        int smallMax = 0;
        int bigMax = 0;
        for (int number : numbers) {
            smallMax = Math.max(smallMax, smallMax + number);
            bigMax = Math.max(smallMax, bigMax);
        }
        return Integer.toString(bigMax);
    }

    public String task_4(int[][] target) {
        int columns = target.length;
        int rows = target[0].length;
        int[][] resultArray = new int[columns][rows];
        StringBuilder result = new StringBuilder();
        for (int rowIndex = target.length - 1; rowIndex >= 0; rowIndex--) {
            for (int columnIndex = target[rowIndex].length - 1; columnIndex >= 0; columnIndex--) {
                resultArray[columnIndex][rows - rowIndex - 1] = target[rowIndex][columnIndex];
            }
        }
        for (int rowIndex = 0; rowIndex < resultArray.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < resultArray[rowIndex].length; columnIndex++) {
                result.append(resultArray[rowIndex][columnIndex]).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String task_5(int[] numbers, int target) {
        int n_1 = numbers.length;
        for (int i = 0; i < n_1 - 1; i++) {
            for (int j = i + 1; j < n_1; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return numbers[i] + " " + numbers[j];
                }
            }
        }
        return null;
    }

    public String task_6(int[][] target) {
        int columns = target.length;
        int rows = target[0].length;
        int sum = 0;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                sum += target[rowIndex][columnIndex];
            }
        }
        return Integer.toString(sum);
    }

    public String task_7(int[][] target) {
        int columns = target.length;
        int rows = target[0].length;
        int[] resultArray = new int[rows];
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            resultArray[rowIndex] = target[rowIndex][0];
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                if (target[rowIndex][columnIndex] > resultArray[rowIndex]) {
                    resultArray[rowIndex] = target[rowIndex][columnIndex];
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int rowMax : resultArray) {
            result.append(rowMax).append(" ");
        }
        return result.toString();
    }

    public String task_8(int[][] target) {
        int columns = target.length;
        int rows = target[0].length;
        int[][] resultArray = new int[rows][columns];
        StringBuilder result = new StringBuilder();
        for (int rowIndex = target.length - 1; rowIndex >= 0; rowIndex--) {
            for (int columnIndex = target[rowIndex].length - 1; columnIndex >= 0; columnIndex--) {
                resultArray[columns - columnIndex - 1][rowIndex] = target[rowIndex][columnIndex];
            }
        }
        for (int rowIndex = 0; rowIndex < resultArray.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < resultArray[rowIndex].length; columnIndex++) {
                result.append(resultArray[rowIndex][columnIndex]).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
