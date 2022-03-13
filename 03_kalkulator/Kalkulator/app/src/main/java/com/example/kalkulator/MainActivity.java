package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import static java.lang.Math.*;

public class MainActivity extends AppCompatActivity {
    String liczba1_s;
    String operacja;
    String pamiec;
    boolean jest_przecinek = false;

    private TextView obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obj = findViewById(R.id.textView);
    }

    public static boolean isNumeric(String str) {
        //sprawdza czy podany string jest liczba
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void aktualizuj_pole(String new_text){
        //aktualizuje pole tekstowe kalkulatora
        CharSequence old_text;
        String text;

        if (new_text.equals("")){
            //czyszczenie ekranu (C)
            obj.setText(new_text);
        }
        else if(new_text.equals(".")){
            //przecinek
            if(!jest_przecinek){
                old_text = obj.getText();

                if(old_text.equals("")){
                    old_text = "0";  //najpierw wprowadzono kropke, dodaj zero przed przecinkiem
                }
                jest_przecinek = true;

                text = old_text + "."; //zaktualizuj o kropke
                obj.setText(text);
            }
        }
        else{
            //wprowadzono operacje lub cyfre

            if(!isNumeric(new_text)){
                //wprowadzono operacje
                liczba1_s = obj.getText().toString(); //zapisz liczbe
                operacja = new_text; //zapisz operacje
                obj.setText(new_text); //wyswietl operacje
                jest_przecinek = false; //zresetuj przecinek
            }
            else{
                //wprowadzono cyfre
                old_text = obj.getText();
                String old_text_s = old_text.toString();

                if(!isNumeric(old_text_s)){
                    //poprzednia byla operacja
                    obj.setText(new_text); //wyswietl nowa cyfre
                }

                else {
                    //poprzednia byla cyfra
                    text = old_text + new_text; //dopisz wprowadzona cyfre
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

    public void znak_dodawanie(View view){
        aktualizuj_pole("+");
    }

    public void znak_odejmowanie(View view){
        aktualizuj_pole("-");
    }

    public void znak_mnozenie(View view){
        aktualizuj_pole("*");
    }

    public void znak_dzielenie(View view){
        aktualizuj_pole("/");
    }

    public void przecinek(View view){
        aktualizuj_pole(".");
    }

    public void znak_potega(View view){
        aktualizuj_pole("^");
    }

    public void znak_pierwiastek(View view){
        aktualizuj_pole("√");
    }

    public void znak_silnia(View view){
        aktualizuj_pole("!");
    }

    public void znak_logarytm(View view){
        aktualizuj_pole("log");
    }

    public void znak_procent(View view){
        aktualizuj_pole("%");
    }

    public void znak_sin(View view){
        aktualizuj_pole("sin");
    }

    public void znak_cos(View view){
        aktualizuj_pole("cos");
    }

    public void znak_tg(View view){
        aktualizuj_pole("tg");
    }

    public void znak_ctg(View view){
        aktualizuj_pole("ctg");
    }

    public void znak_sec(View view){
        aktualizuj_pole("sec");
    }

    public void znak_cosec(View view){
        aktualizuj_pole("cosec");
    }

    public void znak_mem_plus(View view){
        //dodaje wartosc z ekranu do pamieci
        pamiec = obj.getText().toString();
    }

    public void znak_mem_minus(View view){
        //usuwa zapisana liczbe z pamieci
        pamiec = "";
    }

    public void znak_mem(View view){
        //umieszcza liczbe z pamieci w polu tekstowym
        obj.setText(pamiec);
    }

    public void usun(View view){
        aktualizuj_pole("");
        jest_przecinek = false;
    }

    public void rowna_sie(View view){
        //wykonuje dzialania
        double wynik = 0;

        try {
            double liczba1 = Double.parseDouble(liczba1_s);

            if (operacja.equals("+") || operacja.equals("-") || operacja.equals("*") ||
                operacja.equals("/") || operacja.equals("^") || operacja.equals("%")) {
                //operacje dwuliczbowe -> [liczba1] [operacja] [liczba2] =

                //liczba wprowadzona po operacji:
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
                        wynik = pow(liczba1, liczba2);
                        break;
                    case "%":
                        //[liczba1] % z [liczba2]
                        wynik = (liczba1/100) * liczba2;
                        break;
                }
            }
            else {
                //operacje jednoliczbowe -> [liczba1] [operacja] =
                switch (operacja) {
                    case "√":
                        wynik = sqrt(liczba1);
                        break;
                    case "!":
                        int silnia = 1;
                        for (int i = 1; i <= liczba1; i++) {
                            silnia = silnia * i;
                        }
                        wynik = silnia;
                        break;
                    case "log":
                        wynik = log10(liczba1);
                        break;
                    //funkcje trygonometryczne: liczba1 w radianach
                    case "sin":
                        wynik = sin(liczba1);
                        break;
                    case "cos":
                        wynik = cos(liczba1);
                        break;
                    case "tg":
                        wynik = tan(liczba1);
                        break;
                    case "ctg":
                        wynik = 1 / tan(liczba1);
                        break;
                    case "sec":
                        wynik = 1 / cos(liczba1);
                        break;
                    case "cosec":
                        wynik = 1 / sin(liczba1);
                        break;
                }
            }
            obj.setText(String.valueOf(wynik)); //wyswietl wynik
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}