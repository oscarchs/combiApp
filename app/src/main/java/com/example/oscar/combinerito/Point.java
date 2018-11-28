package com.example.oscar.combinerito;

import com.google.gson.annotations.SerializedName;

public class Point {
    @SerializedName("routes_point")
    private String routes_point;

    public Point(String routes_point){
        this.routes_point = routes_point;
    }

    public String getRoutes_point() {
        return routes_point;
    }

    public void setRoutes_point(String routes_point) {
        this.routes_point = routes_point;
    }

}
