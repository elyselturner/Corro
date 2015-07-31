package com.example.elyseturner.corro;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by elyseturner on 7/31/15.
 */
public interface Api {
    @GET("/weather")
    void getWeather(@Query("q") String cityName,
                    Callback<WeatherResponse> callback);
}
