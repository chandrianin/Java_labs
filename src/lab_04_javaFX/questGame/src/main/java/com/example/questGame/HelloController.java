package com.example.questGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static com.example.questGame.Functions.isNumeric;

public class HelloController implements Initializable {
    DBAdapter adapter = new DBAdapter();

    int randomValue;
    int minValue;
    int maxValue;
    boolean isGame = false;
    int checksCount = 0;
    ArrayList<Integer> checks = new ArrayList<>();

    @FXML
    public Label countLabel;

    @FXML
    public TextField targetValue;

    @FXML
    private Label statusText;

    @FXML
    private TextField minValueField;

    @FXML
    private TextField maxValueField;

    @FXML
    protected void onCheckButtonClick() throws IOException {
        if (isGame) {
            if (targetValue.getText().isEmpty()) {
                statusText.setText("Вы не ввели угадываемое число!");
            } else if (isNumeric(targetValue.getText()) && Integer.parseInt(targetValue.getText()) <= maxValue && Integer.parseInt(targetValue.getText()) >= minValue) {
                int tempValue = Integer.parseInt(targetValue.getText());
                if (tempValue <= maxValue && tempValue >= minValue) {
                    for (Integer check : checks) {
                        if (check == tempValue) {
                            statusText.setText(tempValue + " уже проверено. Не подходит");
                            return;
                        }
                    }

                    if (randomValue == tempValue) {
                        adapter.insertSecond(checksCount, randomValue);
                        closeHelloAndOpenGoodbye();
                    }
                    countLabel.setText("Попыток: " + ++checksCount);
                    checks.add(tempValue);
                    statusText.setText("Неизвестное число " + ((randomValue > tempValue) ? "больше " : "меньше ") + tempValue);
                }
            } else {
                statusText.setText("Число " + targetValue.getText() + " не входит в диапазон");
            }
        } else if (!isNumeric(minValueField.getText()) || !isNumeric(maxValueField.getText())) {
            statusText.setText("Введите целые числа!");
        } else if (Integer.parseInt(minValueField.getText()) >= Integer.parseInt(maxValueField.getText())) {
            statusText.setText("Первое число должно быть меньше!");
        } else if (isNumeric(minValueField.getText()) && isNumeric(maxValueField.getText()) && Integer.parseInt(minValueField.getText()) < Integer.parseInt(maxValueField.getText())) {

            minValueField.setEditable(false);
            maxValueField.setEditable(false);
            targetValue.setEditable(true);

            isGame = true;

            minValue = Integer.parseInt(minValueField.getText());
            maxValue = Integer.parseInt(maxValueField.getText());
            adapter.insertFirst(minValue, maxValue);

            final Random random = new Random();
            randomValue = random.nextInt(maxValue - minValue) + minValue;

            targetValue.setPromptText(String.valueOf((minValue + maxValue) / 2));

            statusText.setText("Введите угадываемое число");
        } else {
            statusText.setText("Произошла ошибка");
        }
    }

    @FXML
    protected void closeHelloAndOpenGoodbye() throws IOException {
        Stage stage = (Stage) (targetValue.getScene().getWindow());
        stage.hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("goodbye-view.fxml"));
        Scene scene = new Scene(loader.load(), 230, 230);
        stage = new Stage();
        stage.setTitle("Результат");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        GoodbyeController goodbyeController = loader.getController();
        goodbyeController.addData(checks, randomValue, checksCount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        adapter.connect();

    }
}