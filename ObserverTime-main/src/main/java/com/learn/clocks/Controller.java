package com.learn.clocks;

import com.learn.clocks.model.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Controller {

    @FXML private Label centralTimeLabel;
    @FXML private TextField setTimeField;
    @FXML private VBox devicesBox;

    private final CentralTimeTicker ticker = new CentralTimeTicker();

    @FXML
    public void initialize() {
        ticker.addObserver(t -> centralTimeLabel.setText("Центральное время: " + t + " сек"));
        ticker.start();
    }

    @FXML private void startCentral() { ticker.start(); }
    @FXML private void stopCentral() { ticker.stop(); }

    @FXML
    private void applyTime() {
        try {
            long value = Long.parseLong(setTimeField.getText());
            ticker.setTime(value);
            setTimeField.setStyle("");
        } catch (NumberFormatException e) {
            setTimeField.setStyle("-fx-border-color: red;");
        }
    }

    @FXML private void addStopwatch() { addDevice(new StopwatchDevice()); }

    @FXML
    private void addTimer() {
        TextInputDialog d = new TextInputDialog("5");
        d.setTitle("Таймер");
        d.setHeaderText("Установите время таймера в секундах");
        d.setContentText("Секунды:");
        d.showAndWait().ifPresent(v -> {
            try {
                addDevice(new TimerDevice(Long.parseLong(v)));
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Введите корректное число");
            }
        });
    }

    @FXML
    private void addAlarm() {
        TextInputDialog d = new TextInputDialog("10");
        d.setTitle("Будильник");
        d.setHeaderText("Установите время будильника в секундах");
        d.setContentText("Секунды:");
        d.showAndWait().ifPresent(v -> {
            try {
                addDevice(new AlarmClockDevice(Long.parseLong(v)));
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Введите корректное число");
            }
        });
    }

    private void addDevice(DeviceController device) {
        ticker.addObserver(device);

        Label title = new Label(device.getTitle());
        title.getStyleClass().add("device-title");

        Label value = new Label(device.getDisplayText());
        value.getStyleClass().add("device-value");

        // Создаем кнопки в зависимости от типа устройства
        HBox controls = createControlsForDevice(device, value);

        VBox card = new VBox(8, title, value, controls);
        card.getStyleClass().add("device-card");
        card.setUserData(device);

        // Обновляем значение при каждом тике
        ticker.addObserver(t -> {
            if (devicesBox.getChildren().contains(card)) {
                value.setText(device.getDisplayText());
            }
        });

        devicesBox.getChildren().add(card);
    }

    private HBox createControlsForDevice(DeviceController device, Label valueLabel) {
        HBox controls = new HBox(10);

        if (device instanceof StopwatchDevice) {
            // Для секундомера: Старт, Стоп, Сброс, Удалить
            Button startBtn = new Button("Старт");
            Button stopBtn = new Button("Стоп");
            Button resetBtn = new Button("Сброс");
            Button deleteBtn = new Button("Удалить");

            StopwatchDevice stopwatch = (StopwatchDevice) device;

            startBtn.setOnAction(e -> {
                stopwatch.start();
                valueLabel.setText(stopwatch.getDisplayText());
            });

            stopBtn.setOnAction(e -> {
                stopwatch.stop();
                valueLabel.setText(stopwatch.getDisplayText());
            });

            resetBtn.setOnAction(e -> {
                stopwatch.reset();
                valueLabel.setText(stopwatch.getDisplayText());
            });

            deleteBtn.setOnAction(e -> {
                removeDevice(device, (Parent) controls.getParent());
            });

            controls.getChildren().addAll(startBtn, stopBtn, resetBtn, deleteBtn);

        } else if (device instanceof AlarmClockDevice) {
            // Для будильника: Включить, Выключить, Удалить
            Button enableBtn = new Button("Включить");
            Button disableBtn = new Button("Выключить");
            Button deleteBtn = new Button("Удалить");

            AlarmClockDevice alarm = (AlarmClockDevice) device;

            enableBtn.setOnAction(e -> {
                alarm.start();
                valueLabel.setText(alarm.getDisplayText());
            });

            disableBtn.setOnAction(e -> {
                alarm.stop();
                valueLabel.setText(alarm.getDisplayText());
            });

            deleteBtn.setOnAction(e -> {
                removeDevice(device, (Parent) controls.getParent());
            });

            controls.getChildren().addAll(enableBtn, disableBtn, deleteBtn);

        } else {
            // Для таймера: Старт, Стоп, Удалить
            Button startBtn = new Button("Старт");
            Button stopBtn = new Button("Стоп");
            Button deleteBtn = new Button("Удалить");

            startBtn.setOnAction(e -> {
                device.start();
                valueLabel.setText(device.getDisplayText());
            });

            stopBtn.setOnAction(e -> {
                device.stop();
                valueLabel.setText(device.getDisplayText());
            });

            deleteBtn.setOnAction(e -> {
                removeDevice(device, (Parent) controls.getParent());
            });

            controls.getChildren().addAll(startBtn, stopBtn, deleteBtn);
        }

        return controls;
    }

    private void removeDevice(DeviceController device, Parent card) {
        if (card != null) {
            ticker.removeObserver(device);
            devicesBox.getChildren().remove(card);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}