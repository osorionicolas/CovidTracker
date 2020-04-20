package com.nosorio.covidtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.nosorio.covidtrack.services.CovidService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Initializing", "Starting app...");

        CovidService service = new CovidService();
        service.getCovidSummary(MainActivity.this);
        setContentView(R.layout.activity_main);
    }
}
