package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int previous = 0; //poprzedni wylosowany numer

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.textView1);
    }

    public void sendMessage(View view) {
        java.util.Random gen = new java.util.Random();

        String[] tab = getResources().getStringArray(R.array.tab_aforyzmy);

        int i = gen.nextInt(tab.length);

        if(i == previous){ //jesli wylosowano to samo, co poprzednio
            i = i % 2;
        }
        previous = i;

        String message = tab[i];
        tv1.setText(message); //wyswietl cytat

    }
}




