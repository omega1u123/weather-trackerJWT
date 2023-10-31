package com.example.demo.store.model.api;


import com.example.demo.store.model.Clouds;
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
public class WeatherApiResponse {
    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime date;

    @JsonProperty("sys")
    private Sys sys;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("sunrise")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunriseTime;

        @JsonProperty("sunset")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunsetTime;
    }
}
