package pl.lukaszwilk.weatherApp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import pl.lukaszwilk.weatherApp.models.WeatherModel;
import pl.lukaszwilk.weatherApp.models.dao.IMPL.WeatherDaoIMPL;
import pl.lukaszwilk.weatherApp.models.dao.WeatherDao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    @FXML
    BarChart charTemp;

    @FXML
    ListView<String> listCities;


private ObservableList<String> cityObservableList;
private WeatherDao weatherDao = new WeatherDaoIMPL();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cityObservableList = FXCollections.observableList(weatherDao.getCities());
        listCities.setItems(cityObservableList);

       listCities.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> generateChart(newValue)
       );



    }

    private void generateChart(String cityname) {
        List<WeatherModel> weatherList = weatherDao.getAllWeatherData(cityname);
        XYChart.Series<String,Number> series = new XYChart.Series<>();

        for (WeatherModel weatherModel : weatherList) {
            series.getData().add(new XYChart.Data<>(weatherModel.getDate().toString(),
                    weatherModel.getTemp()-273));
        }
       charTemp.getData().clear();
        charTemp.getData().add(series);

    }
}
