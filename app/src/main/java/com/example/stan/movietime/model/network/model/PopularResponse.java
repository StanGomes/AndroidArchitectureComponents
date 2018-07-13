package com.example.stan.movietime.model.network.model;

import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularResponse {

    @SerializedName("results")
    private List<PopularEntity> mResults;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<PopularEntity> getResults() {
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
