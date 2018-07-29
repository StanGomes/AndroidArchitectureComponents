package com.example.stan.movietime.utils;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class GenreStringToId {

    private int convertStringToInt(String genreString) {
        int genreId;
        switch (genreString) {
            case "Action":
                genreId = 28;
                break;
            case "Adventure":
                genreId = 12;
                break;
            case "Animation":
                genreId = 16;
                break;
            case "Comedy":
                genreId = 35;
                break;
            case "Crime":
                genreId = 80;
                break;
            case "Documentary":
                genreId = 99;
                break;
            case "Drama":
                genreId = 18;
                break;
            case "Family":
                genreId = 10751;
                break;
            case "Fantasy":
                genreId = 14;
                break;
            case "History":
                genreId = 36;
                break;
            case "Horror":
                genreId = 27;
                break;
            case "Music":
                genreId = 10402;
                break;
            case "Mystery":
                genreId = 9648;
                break;
            case "Romance":
                genreId = 10749;
                break;
            case "Science Fiction":
                genreId = 878;
                break;
            case "Thriller":
                genreId = 53;
                break;
            case "War":
                genreId = 10752;
                break;
            default:
                genreId = 0;
                break;
        }
        return genreId;
    }

}
