package pl.lukaszwilk.weatherApp.models;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static String makeHttpRequest(String url ) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = urlConnection.getInputStream();
            int read = 0;
            while ((read = inputStream.read()) != -1) {
                stringBuilder.append((char) read);
            }


return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
