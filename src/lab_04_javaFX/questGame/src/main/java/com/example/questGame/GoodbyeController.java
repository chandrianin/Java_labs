package com.example.questGame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class GoodbyeController {
    @FXML
    public Label resultText;

    @FXML
    public void addData(ArrayList<Integer> array, int randomValue, int checksCount) {
        StringBuilder temp = new StringBuilder("Поздравляем! Вы угадали число " + randomValue + " после " + checksCount + " попыток. Числа, которые не подошли: \n");
        for (Integer number : array) {
            temp.append(number).append(", ");
        }
        resultText.setText(temp.substring(0, temp.length()-2));
    }

}
