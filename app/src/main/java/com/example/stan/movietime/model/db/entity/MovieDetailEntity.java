package com.example.stan.movietime.model.db.entity;

import com.example.stan.movietime.model.network.model.GenreResults;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/************************
 *Author : Stanley Gomes *
 *Since : 02/06/2018        *
 ************************/
@Entity(tableName = "movie_detail")
public class MovieDetailEntity {
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @TypeConverters(ListTypeConverter.class)
    @SerializedName("genres")
    public List<GenreResults> mGenreResults;
    @SerializedName("homepage")
    public String homepage;
    @SerializedName("tagline")
    public String tagline;
    @SerializedName("runtime")
    public int runtime;
    @ColumnInfo(name = "avg_vote")
    @SerializedName("vote_average")
    public double voteAverage;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("overview")
    public String overview;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    public String backdropPath;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("title")
    public String title;
    @SerializedName("budget")
    public int budget;
    @SerializedName("revenue")
    public int revenue;
    @TypeConverters(ListTypeConverter.class)
    @ColumnInfo(name = "production_companies")
    @SerializedName("production_companies")
    private List<ProductionCompanies> productionCompanies;

    public List<GenreResults> getmGenreResults() {
        return mGenreResults;
    }

    public void setmGenreResults(List<GenreResults> mGenreResults) {
        this.mGenreResults = mGenreResults;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public class ProductionCompanies {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
