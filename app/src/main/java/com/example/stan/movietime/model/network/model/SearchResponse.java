package com.example.stan.movietime.model.network.model;

import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018     *
 *************************/

public class SearchResponse {
    @SerializedName("results")
    private List<SearchEntity> mResults;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<SearchEntity> getResults() {
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
