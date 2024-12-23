package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

public class WeatherAppController {

    @FXML
    private TextField citySearchBar;
    @FXML
    private Label mainTemperatureLabel;
    @FXML
    private Label mainWeatherLabel;
    @FXML
    private ImageView mainWeatherImage;
    @FXML
    private Label humidityValue;
    @FXML
    private Label windSpeedValue;

    // Ваш API-ключ OpenWeatherMap
    private static final String API_KEY = "effa45737336f420e711d1e331d135ab";

    @FXML
    private void onSearchButtonClicked(ActionEvent event) {
        String city = citySearchBar.getText().trim();
        if (city.isEmpty()) {
            return;
        }
        fetchWeatherData(city);
    }

    private void fetchWeatherData(String city) {
        try {
            // Формируем URL для запроса к API OpenWeatherMap
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric&lang=ru";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Преобразуем строку ответа в JSON объект
            JSONObject jsonResponse = new JSONObject(response.toString());
            System.out.println(jsonResponse);

            // Извлекаем данные о погоде
            JSONObject main = jsonResponse.getJSONObject("main");
            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            JSONObject wind = jsonResponse.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            String weatherDescription = weather.getString("main");
            String weatherMain = weather.getString("main").toLowerCase();

            // Обновляем интерфейс
            mainTemperatureLabel.setText(String.format("%.0f °C", temperature));
            mainWeatherLabel.setText(weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1));
            humidityValue.setText(humidity + "%");
            windSpeedValue.setText(windSpeed + " km/h");

            // Меняем изображение в зависимости от погоды
            String imagePath = getImagePathForWeather(weatherMain);
            try {
                mainWeatherImage.setImage(new Image(imagePath));
            } catch (IllegalArgumentException e) {
                // В случае ошибки загрузки изображения, используем изображение по умолчанию
                mainWeatherImage.setImage(new Image("file:/path/to/default/image.png")); // Замените на путь к вашему изображению по умолчанию
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImagePathForWeather(String weatherMain) {
        // Путь к изображению зависит от состояния погоды
        switch (weatherMain) {
            case "clear":
                return "file:/C://Users//RSS//IdeaProjects//demo4//src//main//resources//com//example//demo4//assets//clear.png"; // Замените на абсолютный путь или URL
            case "clouds":
                return "file:/C://Users//RSS//IdeaProjects//demo4//src//main//resources//com//example//demo4//assets//cloudy.png"; // Замените на абсолютный путь или URL
            case "rain":
                return "file:/C://Users//RSS//IdeaProjects//demo4//src//main//resources//com//example//demo4//assets//rain.png"; // Замените на абсолютный путь или URL
            case "snow":
                return "file:/C://Users//RSS//IdeaProjects//demo4//src//main//resources//com//example//demo4//assets//snow.png"; // Замените на абсолютный путь или URL
            default:
                return "file:/C://Users//RSS//IdeaProjects//demo4//src//main//resources//com//example//demo4//assets//clear.png"; // Файл по умолчанию
        }
    }
}
