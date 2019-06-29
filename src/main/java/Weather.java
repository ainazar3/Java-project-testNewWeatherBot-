import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    // https://openweathermap.org/weather-conditions

    // 24deb632b200b47e12dfcd60023c3d3a
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ message +"&units=metric&appid=24deb632b200b47e12dfcd60023c3d3a");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";

        while(in.hasNext()){
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        JSONObject country = object.getJSONObject("sys");
        model.setCountry(country.getString("country"));


        return "Country: " + model.getCountry() + "\n" +
                "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "\n" +
                "Humidity: " + model.getHumidity() + "\n" +
                "Weather: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}
