package com.raids.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.fragment));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || Navigation.findNavController(this, R.id.fragment).navigateUp();
    }
}
