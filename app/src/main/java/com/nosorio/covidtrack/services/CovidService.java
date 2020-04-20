package com.nosorio.covidtrack.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.nosorio.covidtrack.R;
import com.nosorio.covidtrack.databinding.ActivityMainBinding;
import com.nosorio.covidtrack.interfaces.GetCovidSummary;
import com.nosorio.covidtrack.models.CovidSummary;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidService {

    private final String URL = "https://api.covid19api.com";
    private ProgressDialog progressDialog;


    public void getCovidSummary(Context context) {
        Log.i("Summary", "Getting summary information from: " + URL);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCovidSummary service = retrofit.create(GetCovidSummary.class);
        Call<JsonObject> summary = service.getSummary();
        summary.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                JsonElement globalSummary = response.body().get("Global");
                Log.i("Global", "Global summary: " + globalSummary);
                CovidSummary cSummary = new Gson().fromJson(globalSummary, CovidSummary.class);
                ActivityMainBinding binding = DataBindingUtil.setContentView((Activity) context, R.layout.activity_main);
                binding.setSummary(cSummary);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
