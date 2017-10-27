package com.edge.weather.Data;

import android.support.annotation.NonNull;

import com.edge.weather.ApiService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user1 on 2017-10-27.
 */

public class WeatherRepository {
    WeatherCallback weatherCallback;

    public WeatherRepository(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }

    public void getWeather(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<JsonObject> call = apiService.getHourly(ApiService.APIKEY, 1, latitude, longitude);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    if (object != null) {
                        //데이터가 null 이 아니라면 날씨 데이터를 텍스트뷰로 보여주기
                        weatherCallback.callback(object.toString());
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
    }

}
