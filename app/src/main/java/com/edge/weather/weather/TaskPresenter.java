package com.edge.weather.weather;

import com.edge.weather.Data.WeatherCallback;
import com.edge.weather.Data.WeatherRepository;

/**
 * Created by user1 on 2017-10-27.
 */

public class TaskPresenter implements TaskContract.Presenter,WeatherCallback {

    WeatherRepository weatherRepository;
    TaskContract.View view;

    public TaskPresenter(TaskContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWeather(double lat, double lon) {
        weatherRepository = new WeatherRepository(this);
        weatherRepository.getWeather(lat,lon);
    }

    @Override
    public void callback(String weather) {
        view.showWeather(weather);
    }
}
