package com.example.demo.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class HourWeather {

    private String time;

    private String description ;

    private int temp;

    private String windDirection;

    private Double windSpeed;


    @Bean
    public HourWeather hourWeather() {
        return new HourWeather();
    }

    public HourWeather() {
    }




}

