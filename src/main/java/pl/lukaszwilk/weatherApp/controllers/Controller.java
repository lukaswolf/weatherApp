package pl.lukaszwilk.weatherApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.lukaszwilk.weatherApp.models.IWeatherObserver;
import pl.lukaszwilk.weatherApp.models.WeatherInfo;
import pl.lukaszwilk.weatherApp.models.WeatherModel;
import pl.lukaszwilk.weatherApp.models.dao.IMPL.WeatherDaoIMPL;
import pl.lukaszwilk.weatherApp.models.dao.WeatherDao;
import pl.lukaszwilk.weatherApp.models.service.WeatherService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable,IWeatherObserver {

    private WeatherService weatherService = WeatherService.getService();

    @FXML
    TextField tekstCity ;

    @FXML
    Label Labeltext ;

    @FXML
    javafx.scene.control.Button buttonSend;

    @FXML
    ProgressIndicator progresid;

    @FXML
    Button buttonWykresy;

private WeatherDao weatherDao = new WeatherDaoIMPL();
    public void initialize(URL location, ResourceBundle resources) {

        progresid.setVisible(false);
        Labeltext.setVisible(false);

        weatherService.registerObserver(this);
      buttonSend.setOnMouseClicked(e ->{
          if (!tekstCity.getText().isEmpty()){
              progresid.setVisible(true);
              Labeltext.setVisible(false);
              weatherService.makeRequest(tekstCity.getText());
              tekstCity.clear();

          }
      });
tekstCity.setOnKeyPressed(e -> {
    if (e.getCode()== KeyCode.ENTER){
        progresid.setVisible(true);
        Labeltext.setVisible(false);
        weatherService.makeRequest(tekstCity.getText());
        tekstCity.clear();
    }
});
buttonWykresy.setOnMouseClicked(e-> goToCharte());
    }

    private void goToCharte() {
        Stage stage = (Stage) buttonWykresy.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chartView.fxml"));
            stage.setScene(new Scene(root,600,400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWeatherUpdate(WeatherInfo info) {

        Labeltext.setText("Temp " + info.getTemp() + "|Cisnienie " +info.getPressure());
        progresid.setVisible(false);
        Labeltext.setVisible(true);
        weatherDao.addWeather(new WeatherModel(info));


    }

}
