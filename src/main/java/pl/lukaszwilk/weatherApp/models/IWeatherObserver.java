package pl.lukaszwilk.weatherApp.models;

public interface IWeatherObserver {
    void onWeatherUpdate (WeatherInfo info);
}
