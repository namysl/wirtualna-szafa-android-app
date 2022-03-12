package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        java.util.Random gen=new java.util.Random();

        String[] tab = getResources().getStringArray(R.array.tab_aforyzmy);
        int i = gen.nextInt(tab.length);

        final TextView tv1 = findViewById(R.id.textView1);

        String message = tab[i];
        tv1.setText(message);

    }
}



