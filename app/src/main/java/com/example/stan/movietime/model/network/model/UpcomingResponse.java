package com.example.stan.movietime.model.network.model;

import com.example.stan.movietime.model.db.entity.UpcomingEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingResponse {

    @SerializedName("results")
    private List<UpcomingEntity> mResults;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<UpcomingEntity> getResults() {
        return mResults;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
