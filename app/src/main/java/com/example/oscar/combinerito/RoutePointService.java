package com.example.oscar.combinerito;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RoutePointService {
    @GET("api/route/")
    Call<Point> getRoutePoint(@QueryMap Map<String, String> options);
}
