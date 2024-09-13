import java.util.Objects;
import java.util.Scanner;

public class lab_01 {
    Scanner in = new Scanner(System.in);

    int task_1() {

        System.out.print("\nTask_1\nInput number: ");
        int inputNumber = in.nextInt();
        int i = 0;
        while (inputNumber != 1) {
            ++i;
            if (inputNumber % 2 == 0) {
                inputNumber /= 2;
            } else {
                inputNumber *= 3;
                inputNumber++;
            }
        }
        return i;
    }

    int task_2() {
        System.out.print("\nTask_2\nInput n: ");
        int n = in.nextInt();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                sum += in.nextInt();
            } else {
                sum -= in.nextInt();
            }
        }
        return sum;
    }

    int task_3() {
        System.out.print("\nTask_3\nInput coordinates:\n");
        int x = in.nextInt();
        int y = in.nextInt();
        int currentX = 0;
        int currentY = 0;
        String destination = "";
        int count = 0;
        while (true) {
            if (in.hasNextInt()) {
                int currentValue = in.nextInt();
                if (x == currentX & y == currentY) {
                    continue;
                } else {
                    switch (destination) {
                        case "север":
                            currentY += currentValue;
                            break;
                        case "юг":
                            currentY -= currentValue;
                            break;
                        case "запад":
                            currentX -= currentValue;
                            break;
                        case "восток":
                            currentX += currentValue;
                            break;
                        default:
                            throw new RuntimeException("incorrect value");
                    }
                    ++count;
                }
            } else {
                destination = in.nextLine();
                if (Objects.equals(destination, "стоп")) {
                    break;
                }
            }
        }
        return count;
    }

    int task_4() {
        System.out.print("\nTask_4\nInput count: ");
        int pathCount = in.nextInt();
        int pathID = -1;
        int maxHeight = 0;
        for (int i = 0; i < pathCount; i++) {
            int tunnelCount = in.nextInt();
            int minPathHeight = Integer.MAX_VALUE;
            for (int j = 0; j < tunnelCount; j++) {
                int currentHeight = in.nextInt();
                minPathHeight = Integer.min(currentHeight, minPathHeight);
            }
            if (minPathHeight > maxHeight) {
                maxHeight = minPathHeight;
                pathID = i + 1;
            }
        }
        System.out.print(pathID + " ");
        return maxHeight;
    }

    boolean task_5() {
        System.out.print("\nTask_5\nInput number: ");
        int number = in.nextInt();
        if (number < 1000 & number > 99) {
            int a = number / 100;
            int b = (number / 10) % 10;
            int c = number % 10;
            return (a * b * c % 2 == (a + b + c) % 2) & (a * b * c % 2 == 0);
        }
        return false;
    }
}
