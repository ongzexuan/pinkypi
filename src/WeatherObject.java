/**
 * Created by ongzexuan on 21/1/14.
 */
public class WeatherObject {

    private int dt;
    private int id;
    private String name;
    private int cod;
    private String base;

    //GETTERS
    public int getDt() {
        return dt;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCod() {
        return cod;
    }
    public String getBase() {
        return base;
    }

    //SETTERS


    private class Coord {
        private String lon;
        private String lat;
    }

    private class Sys {
        private double message;
        private String country;
        private int sunrise;
        private int sunset;
    }

    private class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

    }

    private class Main {
        private double temp;
        private int pressure;
        private double temp_min;
        private double temp_max;
        private int humidity;
    }

    private class Wind {
        private double speed;
        private double gust;
        private int deg;
    }

    private class Clouds {
        private int all;
    }

}
