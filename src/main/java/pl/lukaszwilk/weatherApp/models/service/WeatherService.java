package pl.lukaszwilk.weatherApp.models.service;

import javafx.application.Platform;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.json.JSONPointer;
import pl.lukaszwilk.weatherApp.models.Config;
import pl.lukaszwilk.weatherApp.models.IWeatherObserver;
import pl.lukaszwilk.weatherApp.models.Utils;
import pl.lukaszwilk.weatherApp.models.WeatherInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();
    private ExecutorService executorService;

    private List<IWeatherObserver> observer = new ArrayList<>();

    public static WeatherService getService() {
        return ourInstance;
    }

    private WeatherService() {
        executorService = Executors.newSingleThreadExecutor();
    }





    public void makeRequest(String city){
        Runnable runnable = () -> readJsonData(Utils.makeHttpRequest(Config.APP_BASE_URL + city +"&appid=" +  Config.APP_ID),city);
executorService.execute(runnable);
    }



    private void readJsonData(String json,String cityname) {
        JSONObject root = new JSONObject(json);
        JSONObject main = root.getJSONObject("main");
        JSONObject clouds = root.getJSONObject("clouds");
        JSONObject visibility = new JSONObject(json);


        double temp = main.getDouble("temp");
        System.out.println("Temperatura to : " + "" + temp);

        double pressure = main.getDouble("pressure");
        System.out.println("Cisnienie wynosi :" + " " + pressure);

        double widocznosc = visibility.getDouble("visibility");
        System.out.println("widocznosc to :" + " " + widocznosc);


        double chmury = clouds.getDouble("all");
        System.out.println("Chmury to :" + " " + chmury);

        //przekazuje labelke do watku pierwszego
        observer.forEach(s -> {
            Platform.runLater(() -> s.onWeatherUpdate(new WeatherInfo(temp, (int) pressure,cityname) ));

        });
    }



            


    public void registerObserver (IWeatherObserver observer){
        this.observer.add(observer);
    }

}
