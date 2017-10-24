package pl.lukaszwilk.weatherApp.models.dao.IMPL;

import pl.lukaszwilk.weatherApp.models.MysqlConntector;
import pl.lukaszwilk.weatherApp.models.WeatherModel;
import pl.lukaszwilk.weatherApp.models.dao.WeatherDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDaoIMPL implements WeatherDao {
    MysqlConntector mysqlConntector = MysqlConntector.getInstace();

    @Override
    public void addWeather(WeatherModel model) {
        try {
            PreparedStatement preparedStatement = mysqlConntector.getConnection().prepareStatement(
                    "INSERT INTO weather VALUES (?,?,?,?)");
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, model.getCityname());
            preparedStatement.setFloat(3, model.getTemp());
            preparedStatement.setDate(4, null);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<WeatherModel> getAllWeatherData(String cityname) {
        List<WeatherModel> cityList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = mysqlConntector.getConnection().prepareStatement(
                    "SELECT * FROM weather WHERE cityname = ?"
            );
            preparedStatement.setString(1, cityname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cityList.add(new WeatherModel(resultSet.getString("cityname")
                        , resultSet.getFloat("temp"), resultSet.getDate("date")));

            }
            return cityList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getCities() {
        List<String> cityNames = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = mysqlConntector.getConnection().prepareStatement(
                    "SELECT DISTINCT cityname FROM weather"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cityNames.add(resultSet.getString("cityname"));
            }
            return cityNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
return null;
    }
}
