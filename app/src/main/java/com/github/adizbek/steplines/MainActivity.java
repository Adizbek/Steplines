package com.github.adizbek.steplines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SteplinesView steplinesView = findViewById(R.id.steplines);

        steplinesView.addItem(new SteplinesView.Item("Xiva"));
        steplinesView.addItem(new SteplinesView.Item("Buxoro"));
        steplinesView.addItem(new SteplinesView.Item("Samarqand"));
        steplinesView.addItem(new SteplinesView.Item("Jizzax"));
        steplinesView.addItem(new SteplinesView.Item("Toshkent"));
        steplinesView.addItem(new SteplinesView.Item("Andijon"));
        steplinesView.addItem(new SteplinesView.Item("Fa'rgona"));

        steplinesView.addLayer(new SteplinesView.SidebarLayer("#0099FF", 2, 5));
        steplinesView.addLayer(new SteplinesView.SidebarLayer("#CA9AF0", 3, 4));
    }


}
