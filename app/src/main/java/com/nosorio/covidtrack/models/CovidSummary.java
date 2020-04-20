package com.nosorio.covidtrack.models;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class CovidSummary {

    @SerializedName("TotalConfirmed")
    private Long confirmed;
    @SerializedName("TotalDeaths")
    private Long deaths;
    @SerializedName("TotalRecovered")
    private Long recovered;

    DecimalFormat formatter = new DecimalFormat("#,###");

    public String getConfirmed() {
        return formatter.format(confirmed);
    }

    public void setConfirmed(Long confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return formatter.format(deaths);
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return formatter.format(recovered);
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }
}
