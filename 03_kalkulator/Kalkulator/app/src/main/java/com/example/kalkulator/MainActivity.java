package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    double x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nie_wiem(){
        TextView obj = (TextView)findViewById(R.id.textView);
        CharSequence cs = obj.getText();
        String s = cs.toString();
        double x = Double.parseDouble(s);
        x = x*x;
        String s2 = Double.toString(x);
        obj.setText(s2);
    }

    public void cyfra1(View view){
        TextView obj = (TextView)findViewById(R.id.textView);
        CharSequence old_text = obj.getText();
        String text = old_text + "1";
        obj.setText(text);
    }

    public void rowna_sie(View view){

    }

    public void usun(View view){
        TextView obj = (TextView)findViewById(R.id.textView);

        String text = "";
        obj.setText(text);
    }

    public void dodaj(View view){
        TextView obj = (TextView)findViewById(R.id.textView);

        CharSequence old_text = obj.getText();
        String text = old_text + "+";

        CharSequence cs = obj.getText();
        String s = cs.toString();
        x = Double.parseDouble(s);

        obj.setText(text);
    }

}