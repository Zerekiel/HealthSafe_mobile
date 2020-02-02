package com.eipteam.healthsafe;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class ChooseDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    public void choosePortable(View view) {
        Intent intent = new Intent(this, NFCConnect.class);
        startActivity(intent);
    }

    public void chooseComputer(View view) {
        Intent intent = new Intent(this, NFCConnectPC.class);
        startActivity(intent);
    }

    public void deconnection(View view) {
        finish();
    }
}

