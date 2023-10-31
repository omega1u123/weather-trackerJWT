package com.example.demo.service;

import com.example.demo.store.CurrentWeather;
import com.example.demo.store.HourWeather;
import com.example.demo.store.entity.LocationEntity;
import com.example.demo.store.model.api.ForecastApiResponse;
import com.example.demo.store.model.api.WeatherApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private static final String APP_ID = "0a1ca86b61569b765fbcba868a5c685d";
    private static final String BASE_API_URL = "https://api.openweathermap.org";
    private static final String WEATHER_API_URL_SUFFIX = "/data/2.5/weather";
    private static final String FORECAST_API_URL_SUFFIX = "/data/2.5/forecast";


    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    final RestTemplate restTemp;

    public WeatherService(RestTemplate restTemp) {
        this.restTemp = restTemp;
    }

    public CurrentWeather getCurrentWeather() throws IOException {

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("api.openweathermap.org/data/2.5/weather")
                .path("")
                .query("q={keyword}&APPID={appid}")
                .buildAndExpand("Minsk","0a1ca86b61569b765fbcba868a5c685d");

        String uri = uriComponents.toUriString();
        System.out.println(uri);
        ResponseEntity<String> resp= restTemp.exchange(uri, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        CurrentWeather currentWeather = mapper.readValue(resp.getBody(), CurrentWeather.class);

        System.out.println(resp.getBody());

        System.out.println(currentWeather.toString());

        return currentWeather;

    }

    public HourWeather getHourWeather() throws IOException {

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("api.openweathermap.org/data/2.5/forecast")
                .path("")
                .query("q={keyword}&APPID={appid}&cnt=40")
                .buildAndExpand("Minsk","0a1ca86b61569b765fbcba868a5c685d");

        String uri = uriComponents.toUriString();
        System.out.println(uri);
        ResponseEntity<String> resp= restTemp.exchange(uri, HttpMethod.GET, null, String.class);

        ObjectMapper mapper = new ObjectMapper();
        HourWeather hourWeather = mapper.readValue(resp.getBody(), HourWeather.class);

        System.out.println("HOUR WEATHER");


        String response = resp.getBody();


        return hourWeather;

    }


    public ForecastApiResponse getForecastForLocation(LocationEntity location) throws IOException, InterruptedException {
            URI uri = buildUriForForecastRequest(location);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), ForecastApiResponse.class);

    }


    public WeatherApiResponse getWeatherForLocation(LocationEntity location) throws IOException, InterruptedException {
        URI uri = buildUriForWeatherRequest(location);
        HttpRequest request = buildRequest(uri);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);


        return objectMapper.readValue(response.body(), WeatherApiResponse.class);
    }

    public static Map<Integer, ForecastApiResponse.HourlyForecast> getHourlyWeather(List<ForecastApiResponse.HourlyForecast> forecasts, LocationEntity location) throws IOException, InterruptedException {
        LocalDate day = LocalDate.from(forecasts.get(0).getDate());
        Map<Integer, ForecastApiResponse.HourlyForecast> result = new HashMap<>();
        for(int i = 0; i <5 ; i++){
            result.put(i+1, forecasts.get(i));
        }

        return result;

        /*return forecasts
                .stream()
                .filter(f -> f.getDate().toLocalDate().isEqual(day))
                .collect(Collectors.toList());*/
    }


    public static List<ForecastApiResponse.HourlyForecast> getForecastForDay(List<ForecastApiResponse.HourlyForecast> forecasts, LocalDate day) throws IOException, InterruptedException {
        return forecasts
                .stream()
                .filter(f -> f.getDate().toLocalDate().isEqual(day))
                .collect(Collectors.toList());
    }


    public static Map<Integer, ForecastApiResponse.HourlyForecast> getDailyWeather(List<ForecastApiResponse.HourlyForecast> forecasts) throws IOException, InterruptedException {
        LocationEntity loc = new LocationEntity("minsk");

        Map<Integer, ForecastApiResponse.HourlyForecast> dailyForecasts = new HashMap<>();

        LocalDate currentDay = LocalDate.from(forecasts.get(0).getDate());
        LocalDate lastDay = LocalDate.from(forecasts.get(forecasts.size()-1).getDate());
         int i = 1;

        while (currentDay.isBefore(lastDay)) {
            dailyForecasts.put(i, getMaxTemp(getForecastForDay(forecasts, currentDay)));
            System.out.println(currentDay);
            currentDay = currentDay.plusDays(1);
            i++;
        }
        /*return dailyForecasts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(LocalDate::compareTo))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new)
                );*/
        return dailyForecasts;
    }


    public static ForecastApiResponse.HourlyForecast getMaxTemp(List<ForecastApiResponse.HourlyForecast> forecast){

        ForecastApiResponse.HourlyForecast res = forecast.get(0);


        for(int i = 0; i < forecast.size() - 1 ; i++){
            System.out.println(forecast.get(i).getMain().getTemperatureMaximum());
            if (res.getMain().getTemperature() < forecast.get(i).getMain().getTemperatureMaximum()){
                res = forecast.get(i);
            }
        }

        return res;

    }



    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }

    private static URI buildUriForWeatherRequest(LocationEntity location) {
        return URI.create(BASE_API_URL + WEATHER_API_URL_SUFFIX
                + "?q=" + location.getName()
                + "&appid=" + APP_ID
                + "&units=" + "metric");
    }

    private static URI buildUriForForecastRequest(LocationEntity location) {
        return URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX
                + "?q=" + location.getName()
                + "&appid=" + APP_ID
                + "&units=" + "metric"
                + "&cnt=" + "40"
        );
    }





}

