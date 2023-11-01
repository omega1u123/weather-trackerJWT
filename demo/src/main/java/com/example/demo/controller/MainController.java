package com.example.demo.controller;

import com.example.demo.service.WeatherService;
import com.example.demo.store.RoleEntity;
import com.example.demo.store.entity.LocationEntity;
import com.example.demo.store.model.api.ForecastApiResponse;
import com.example.demo.store.model.api.HourlyResponse;
import com.example.demo.store.model.api.WeatherApiResponse;
import com.example.demo.store.repository.RoleRepo;
import com.example.demo.store.entity.UserEntity;
import com.example.demo.store.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MainController {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final WeatherService weatherService;

    @GetMapping("/addUser")
    public String addUser(@RequestParam String name, @RequestParam String password, @RequestParam String role){
        RoleEntity roleEntity = roleRepo.findRoleEntityByName(role);
        UserEntity userEntity = new UserEntity(name, password, roleEntity);
        userRepo.save(userEntity);
        return "ok";
    }

    @GetMapping("/user")
    public String getUser(){
        return "user";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping("/info")
    public String getInfo(){
        return "info";
    }

    @GetMapping("/getWeather")
    public ForecastApiResponse getWeather() throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");
        return weatherService.getForecastForLocation(loc);
    }

    @GetMapping("/getHourlyWeather")
    public Map<Integer, HourlyResponse.HourlyForecast> getH() throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");
        return weatherService.getHourlyWeather2(weatherService.getHourly(loc).getHForecasts(), loc);
    }

    @GetMapping("/getCurrentWeather")
    public WeatherApiResponse getWeatherForLoc() throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");
        return weatherService.getWeatherForLocation(loc);
    }

    /*@GetMapping("/getHourlyWeather")
    public Map<Integer, ForecastApiResponse.HourlyForecast> getHourly() throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");
        return weatherService.getHourlyWeather(weatherService.getForecastForLocation(loc).getForecasts(), loc);
    }*/


    @GetMapping("/getDailyWeather")
    public Map<Integer, ForecastApiResponse.HourlyForecast> getTempForWeek() throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");
        System.out.println(weatherService.getDailyWeather(weatherService.getForecastForLocation(loc).getForecasts()));
        return weatherService.getDailyWeather(weatherService.getForecastForLocation(loc).getForecasts());
    }

}
