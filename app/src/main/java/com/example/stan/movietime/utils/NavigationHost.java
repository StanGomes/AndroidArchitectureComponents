package com.example.stan.movietime.utils;

import androidx.fragment.app.Fragment;

/************************
 *Author : Stanley Gomes *
 *Since : 02/06/2018        *
 ************************/

public interface NavigationHost {
    void navigateTo(Fragment fragment, boolean addToBackstack);
}
