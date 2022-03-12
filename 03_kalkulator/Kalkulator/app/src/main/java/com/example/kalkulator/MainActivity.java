package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String liczba1_cs;
    String operacja;
    boolean jest_kropka = false;

    private TextView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obj = findViewById(R.id.textView);
    }

    public void aktualizuj_pole(String new_text){
        CharSequence old_text;
        String text;

        if (new_text.equals("")){
            //czyszczenie ekranu (C)
            obj.setText(new_text);
        }
        else if(new_text.equals(".")){
            if(!jest_kropka){
                jest_kropka = true;
                old_text = obj.getText();
                text = old_text + ".";
                obj.setText(text);
            }
        }
        else{
            //operacje i cyfry
            if (new_text.equals("+") || new_text.equals("-") || new_text.equals("*") || new_text.equals("/") ||
                new_text.equals("^") || new_text.equals("√") || new_text.equals("log") || new_text.equals("!") || new_text.equals("%")){
                //operacje
                liczba1_cs = obj.getText().toString(); //zapisz liczbe
                operacja = new_text; //zapisz operacje
                obj.setText(new_text); //wyswietl operacje
                jest_kropka = false;
            }
            else{
                //cyfry
                old_text = obj.getText();
                String old_text_s = old_text.toString();

                if(old_text_s.equals("+") || old_text_s.equals("-") || old_text_s.equals("*") || old_text_s.equals("/") ||
                old_text_s.equals("^") || old_text_s.equals("√") || old_text_s.equals("log") || old_text_s.equals("!") || old_text_s.equals("%")){
                    //jesli poprzednia byla operacja
                    obj.setText(new_text);
                }
                else {
                    //poprzednia byla cyfra
                    text = old_text + new_text;
                    obj.setText(text);
                }
            }
        }
    }

    public void cyfra0(View view){
        aktualizuj_pole("0");
    }

    public void cyfra1(View view){
        aktualizuj_pole("1");
    }

    public void cyfra2(View view){
        aktualizuj_pole("2");
    }

    public void cyfra3(View view){
        aktualizuj_pole("3");
    }

    public void cyfra4(View view){
        aktualizuj_pole("4");
    }

    public void cyfra5(View view){
        aktualizuj_pole("5");
    }

    public void cyfra6(View view){
        aktualizuj_pole("6");
    }

    public void cyfra7(View view){
        aktualizuj_pole("7");
    }

    public void cyfra8(View view){
        aktualizuj_pole("8");
    }

    public void cyfra9(View view){
        aktualizuj_pole("9");
    }

    public void znak_plus(View view){
        aktualizuj_pole("+");
    }

    public void znak_minus(View view){
        aktualizuj_pole("-");
    }

    public void znak_mnozenie(View view){
        aktualizuj_pole("*");
    }

    public void znak_dzielenie(View view){
        aktualizuj_pole("/");
    }

    public void kropka(View view){
        aktualizuj_pole(".");
    }

    public void usun(View view){
        aktualizuj_pole("");
        jest_kropka = false;
    }

    public void potegowanie(View view){
        aktualizuj_pole("^");
    }

    public void pierwiastkowanie(View view){
        aktualizuj_pole("√");
    }

    public void silnia(View view){
        aktualizuj_pole("!");
    }

    public void logarytm(View view){
        aktualizuj_pole("log");
    }

    public void procent(View view){
        aktualizuj_pole("%");
    }

    public void rowna_sie(View view){
        double wynik = 0;
        try {
            double liczba1 = Double.parseDouble(liczba1_cs);

            if (operacja.equals("+") || operacja.equals("-") || operacja.equals("*") ||
                operacja.equals("/") || operacja.equals("^") || operacja.equals("%")) {
                double liczba2 = Double.parseDouble(obj.getText().toString());

                switch (operacja) {
                    case "+":
                        wynik = liczba1 + liczba2;
                        break;
                    case "-":
                        wynik = liczba1 - liczba2;
                        break;
                    case "*":
                        wynik = liczba1 * liczba2;
                        break;
                    case "/":
                        wynik = liczba1 / liczba2;
                        break;
                    case "^":
                        wynik = Math.pow(liczba1, liczba2);
                        break;
                }
            }
            else {
                switch (operacja) {
                    case "√":
                        wynik = Math.sqrt(liczba1);
                        break;
                    case "!":
                        int silnia = 1;
                        for (int i = 1; i <= liczba1; i++) {
                            silnia = silnia * i;
                        }
                        wynik = silnia;
                        break;
                    case "log":
                        wynik = Math.log10(liczba1);
                        break;
                }
            }

            obj.setText(String.valueOf(wynik));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}