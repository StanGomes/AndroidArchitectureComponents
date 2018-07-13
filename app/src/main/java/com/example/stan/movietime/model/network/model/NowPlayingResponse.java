package com.example.stan.movietime.model.network.model;

import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/
public class NowPlayingResponse {

    @SerializedName("results")
    private List<NowPlayingEntity> mResults;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<NowPlayingEntity> getResults() {
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
