package com.example.demo.store.model.api;

import com.example.demo.store.model.Main;
import com.example.demo.store.model.Weather;
import com.example.demo.store.model.Wind;
import com.example.demo.store.model.util.UnixTimestampDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyResponse {

    @JsonProperty("list")
    private List<HourlyResponse.HourlyForecast> hForecasts;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyForecast {

        @JsonProperty("dt")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime date;


        @JsonProperty("main")
        private Main main;

        @JsonProperty("weather")
        private List<Weather> weatherList;

        @JsonProperty("wind")
        private Wind wind;

    }

}
