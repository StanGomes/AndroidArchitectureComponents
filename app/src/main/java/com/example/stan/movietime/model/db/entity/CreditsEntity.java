package com.example.stan.movietime.model.db.entity;

import com.example.stan.movietime.model.db.entity.typeConverters.CastTypeConverter;
import com.example.stan.movietime.model.network.model.Cast;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/*************************
 *Author : Stanley Gomes *
 *************************/

@Entity(tableName = "credits")
public class CreditsEntity {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @TypeConverters(CastTypeConverter.class)
    @SerializedName("cast")
    private List<Cast> casts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }
}
