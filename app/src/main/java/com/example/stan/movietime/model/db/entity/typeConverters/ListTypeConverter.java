package com.example.stan.movietime.model.db.entity.typeConverters;

import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.model.GenreResults;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class ListTypeConverter {

    @TypeConverter
    public String productionListToString(List<MovieDetailEntity.ProductionCompanies> productionCompanies) {
        if (productionCompanies == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieDetailEntity.ProductionCompanies>>() {
        }.getType();
        return gson.toJson(productionCompanies, type);
    }

    @TypeConverter
    public List<MovieDetailEntity.ProductionCompanies> stringToProductionList(String data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieDetailEntity.ProductionCompanies>>() {
        }.getType();

        return gson.fromJson(data, type);
    }

    @TypeConverter
    public String genreListToString(List<GenreResults> genreResults) {
        if (genreResults == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<GenreResults>>() {
        }.getType();
        return gson.toJson(genreResults, type);
    }

    @TypeConverter
    public List<GenreResults> stringToGenreList(String data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<GenreResults>>() {
        }.getType();

        return gson.fromJson(data, type);
    }


}
