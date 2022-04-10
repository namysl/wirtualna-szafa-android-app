package com.example.wirtualnaszafa.ui.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AccountFragment extends Fragment implements View.OnClickListener{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Button button_register, button_login;
    EditText email_editT, password_editT, username_editT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        button_register = (Button) rootView.findViewById(R.id.button_register);
        button_login = (Button) rootView.findViewById(R.id.button_login);

        email_editT = rootView.findViewById(R.id.editTextEmail);
        password_editT = rootView.findViewById(R.id.editTextPassword);
        username_editT = rootView.findViewById(R.id.editTextUsername);

        button_register.setOnClickListener(this);
        button_login.setOnClickListener(this);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //check input values just one time, then decide which button was pressed
        if (isEmpty(email_editT) || isEmpty(password_editT) || isEmpty(username_editT)){
            Toast.makeText(v.getContext(), "Należy wypełnić wszystkie pola.", Toast.LENGTH_SHORT).show();
        }
        //można też wymagać, żeby input dla loginu/hasła był nie krótszy niż X
        else {
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawidłowy format");
                Toast.makeText(v.getContext(), "Wprowadź poprawny adres email.", Toast.LENGTH_SHORT).show();
            }
            else {
                //register or login?
                if (v.getId() == R.id.button_register){
                    //tutaj API rejestracja - użyć Unirest albo OkHttp
                    //if (API response == 200){  udało się zarejestrować, mamy klucz autoryzacyjny
                    //tylko co potem robimy z tym kluczem?
                        Toast.makeText(v.getContext(), "Zarejestrowano !!WIP API!!", Toast.LENGTH_SHORT).show();
                    //}
                    //else{ email zajęty np., wypisz wiadomość ze zwróconego jsona w toast, try again, bla bla
                }
                else if(v.getId() == R.id.button_login){
                    //tutaj API logowanie, tj. wyżej przy rejestracji
                    Toast.makeText(v.getContext(), "Zalogowano !!WIP API!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //regex <3
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}