package com.example.stan.movietime.model.db.entity.typeConverters;

import com.example.stan.movietime.model.network.model.Cast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class CastTypeConverter {

    @TypeConverter
    public String castListToString(List<Cast> castList) {
        if (castList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cast>>() {
        }.getType();
        return gson.toJson(castList, type);
    }

    @TypeConverter
    public List<Cast> stringToCastList(String data) {
        if (data == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cast>>() {
        }.getType();

        return gson.fromJson(data, type);
    }
}
