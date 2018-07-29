package com.example.stan.movietime.view.ui;

import android.os.Bundle;
import android.view.View;

import com.example.stan.movietime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DiscoverActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputEditText = findViewById(R.id.search_editText);
        FloatingActionButton searchFab = findViewById(R.id.search_fab);
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textInputLayout.setErrorEnabled(false);
                query = textInputLayout.getEditText().getText().toString();
                showSearchDialog(query);
            }
        });
    }

    private void showSearchDialog(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFragment searchFragment = SearchFragment.newInstance(query);
//        searchFragment.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Light);
        searchFragment.show(fragmentManager, "search_dialog_Fragment");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
