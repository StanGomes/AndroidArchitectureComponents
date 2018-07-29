package com.example.stan.movietime.model.network.model;

import com.example.stan.movietime.model.db.entity.RecommendedEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class RecommendedResponse {
    @SerializedName("results")
    private List<RecommendedEntity> recommendedResults;

    public List<RecommendedEntity> getRecommendedResults() {
        return recommendedResults;
    }

    public void setRecommendedResults(List<RecommendedEntity> recommendedResults) {
        this.recommendedResults = recommendedResults;
    }
}
