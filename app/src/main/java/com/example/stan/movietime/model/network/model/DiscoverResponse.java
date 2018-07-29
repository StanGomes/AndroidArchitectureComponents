package com.example.stan.movietime.model.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class DiscoverResponse {

    @SerializedName("results")
    private List<DiscoverResult> discoverResults;

    public List<DiscoverResult> getDiscoverResults() {
        return discoverResults;
    }

    public void setDiscoverResults(List<DiscoverResult> discoverResults) {
        this.discoverResults = discoverResults;
    }
}
