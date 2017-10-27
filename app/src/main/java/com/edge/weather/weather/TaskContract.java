package com.edge.weather.weather;

import com.edge.weather.BasePresenter;
import com.edge.weather.BaseView;

/**
 * Created by user1 on 2017-10-27.
 */

public interface TaskContract  {
    interface Presenter extends BasePresenter {
        void getWeather(double lat, double lon);
    }
    interface View extends BaseView<Presenter> {
        void showWeather(String weather);
    }
}
