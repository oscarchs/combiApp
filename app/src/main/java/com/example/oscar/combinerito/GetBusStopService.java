package com.example.oscar.combinerito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetBusStopService {
    @GET("api/bus_stop/")
    Call<List<BusStop>> getBusStops();
}
