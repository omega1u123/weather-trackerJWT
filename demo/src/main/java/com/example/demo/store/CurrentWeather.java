package com.example.demo.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class CurrentWeather {

    //private static final long serialVersionUID = 7406210628182440902L;

    private String weatherDescription;
    private String name;

    private int temp;
    private int minTemp;
    private int maxTemp;


    private int clouds;

    private double wind;

    private String sunrise;
    private String sunset;

    private int pressure;



    @Bean
    public CurrentWeather weather() {
        return new CurrentWeather();
    }

    public CurrentWeather() {
    }


    @JsonProperty("clouds")
    public void setClouds(Map<String, Object> clouds){
        this.clouds = (int) clouds.get("all");
    }



    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        setWeatherDescription((String) weather.get("description"));
    }



    @JsonProperty("main")
    public void setTemp(Map<String, Object> main) {
        this.temp = (int) (Math.round((Double)main.get("temp")) - 273.15);
        this.minTemp = (int) (Math.round((Double)main.get("temp_min")) - 273.15);
        this.maxTemp = (int) (Math.round((Double)main.get("temp_max")) - 273.15);
        setPressure(main);
    }

    /*@JsonProperty("main")
    public void temp(Map<String, Object> main) {
       setTemp((int) (Math.round((Double)main.get("temp")) - 273.15));
    }*/

    @JsonProperty("wind")
    public void setWind(Map<String, Object> wind) {
        this.wind = Math.round(((Double) wind.get("speed")) * 3.6);
    }

    @JsonProperty("sys")
    public void getSunDescription(Map<String, Object> sys){
        setSunrise((int) sys.get("sunrise"));
        setSunset((int) sys.get("sunset"));
    }

    public void setSunrise(long sunrise) {
        Date date = new Date(sunrise * 1000L);
        String dateStr = date.toString();
        this.sunrise = dateStr.substring(10,16);
    }

    public void setSunset(long sunset) {
        Date date = new Date(sunset * 1000L);
        String dateStr = date.toString();
        this.sunset = dateStr.substring(10,16);
    }

    public void setPressure(Map<String, Object> main) {
        this.pressure = (int) main.get("pressure");
    }


}