package com.nosorio.covidtrack.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCovidSummary {

    //@GET("/summary")
    @GET("/data/9043/global-covid19-who-gis.json")
    Call<JsonObject> getSummary();
}
