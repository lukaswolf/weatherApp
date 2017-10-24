package pl.lukaszwilk.weatherApp.models.dao;

import pl.lukaszwilk.weatherApp.models.WeatherModel;

import java.util.List;

public interface WeatherDao {
    void addWeather(WeatherModel model);
    List<WeatherModel> getAllWeatherData(String cityname);
    List<String> getCities();
}
