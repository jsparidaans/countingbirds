package com.example.froukje.countingbirds;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class KalenderNext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_next);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        String naam = b.getString("Input");

        EditText bedrijf = (EditText) findViewById(R.id.editText_kalenderNext_bedrijf);
        EditText van = (EditText) findViewById(R.id.editText_kalenderNext_tijdVan);
        EditText tot = (EditText) findViewById(R.id.editText_kalenderNext_tijdTot);
    }

}
