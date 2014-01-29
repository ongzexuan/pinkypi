package pdfParser; /**
 * Created by ongzexuan on 20/1/14.
 */

import com.google.gson.Gson;


import java.net.URL;
import java.net.URLConnection;
import java.io.*;

/**
 * A extension of the project to provide weather data to improve user experience. Not completed.
 * Fetches data from openweatherapi.org as a JSON object.
 */
public class WeatherDevice extends WeatherObject {

    private String in;
    private String out;
    private URL url = null;

    public WeatherDevice() {
        this.in = "";
        this.out = "";
    }

    public WeatherDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public WeatherDevice(String in, String out, String link) {
        this.in = in;
        this.out = out;
        try {
            this.url = new URL(link);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pullDataFromWeb() {
        String filename = "weatherjson.txt";

        if (url == null) url = getURL("http://api.openweathermap.org/data/2.5/weather?lat=1.29&lon=103.85");
        String contents = readData(url);
        writeData(contents, new File(filename));
        System.out.println("pullData() executed successfully");

        pullDataFromSource(contents);
    }

    //TESTING
    public void pullDataFromSource(String content) {

        try {

            Gson gson = new Gson();
            WeatherObject weatherObject = gson.fromJson(content, WeatherObject.class);

            System.out.println(weatherObject.getDt());

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    //method to get url
    public URL getURL(String link) {
        try {
            return new URL(link);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readData(URL url) {
        BufferedReader inReader = null;
        try {
            URLConnection conn = url.openConnection();
            inReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = inReader.readLine();
            StringBuilder builder = new StringBuilder();
            do {
                builder.append(line+"\n");
            } while((line = inReader.readLine()) != null);
            return builder.toString();


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (inReader != null) try {
                inReader.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String writeData(String content, File file) {
        BufferedWriter outWriter = null;
        try {
            outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            outWriter.write(content);
            outWriter.flush();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) try {
                outWriter.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
