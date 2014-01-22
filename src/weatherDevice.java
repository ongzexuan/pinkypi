/**
 * Created by ongzexuan on 20/1/14.
 */

import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;
import sun.org.mozilla.javascript.internal.json.JsonParser;


import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.io.*;

public class weatherDevice {

    private String in;
    private String out;
    private URL url = null;

    public weatherDevice() {
        this.in = "";
        this.out = "";
    }

    public weatherDevice(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public weatherDevice(String in, String out, String link) {
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

    public void pullDataFromSource(String content) {

        try {

            Gson gson = new Gson();
            WeatherObject weatherObject = gson.fromJson(content, WeatherObject.class);


            System.out.println(weatherObject.getDt());


//            JsonParserFactory factory = JsonParserFactory.getInstance();
//            JSONParser parser = factory.newJsonParser();
//            Map jsonData = parser.parseJson(content);
//
//            ArrayList weather = (ArrayList)jsonData.get("weather");
//
//
//
//            //String newcontent = weather.toString();
//
////            System.out.println((String)jsonData.get("id"));
////            System.out.println((String)jsonData.get("main"));
////            System.out.println((String)jsonData.get("description"));
//
//            //System.out.println(weather.get(0));
//            //System.out.println(weather.get(1));
//            //String string = (String)jsonData.get("name");
//            //System.out.println(string);

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
