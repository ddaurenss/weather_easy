package com.example.demo4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class WeatherAppFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Загружаем FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("weather_app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Настройка окна
        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
