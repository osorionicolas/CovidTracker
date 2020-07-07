package com.nosorio.covidtrack.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nosorio.covidtrack.interfaces.GetCovidSummary;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidService {

    //private final String URL = "https://api.covid19api.com";
    private final String URL = "https://dashboards-dev.sprinklr.com/";
    private ProgressDialog progressDialog;
    private Context context;
    private String jsonKey = "rows";
    private String[] indexes = {"Deaths", "Cumulative Deaths", "Confirmed", "Cumulative Confirmed"};

    public CovidService(Context context){
        this.context = context;
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void getCovidSummary() {
        Log.i("Summary", "Getting summary information from: " + URL);

        this.showProgressDialog();

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
                /*JsonElement globalSummary = response.body().get(jsonKey);
                Log.i("Global", "Global summary: " + globalSummary);
                CovidSummary cSummary = new Gson().fromJson(globalSummary, CovidSummary.class);
                ActivityMainBinding binding = DataBindingUtil.setContentView((Activity) context, R.layout.activity_main);
                binding.setSummary(cSummary);*/
                Type type = new TypeToken<List<Object>>(){}.getType();
                List<Object> summary = new Gson().fromJson(response.body().get(jsonKey), type);
                Log.i("Summary", summary.toString());
                List<Object> currentSummary = (List<Object>) summary.stream();
                Log.i("Summary", currentSummary.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
