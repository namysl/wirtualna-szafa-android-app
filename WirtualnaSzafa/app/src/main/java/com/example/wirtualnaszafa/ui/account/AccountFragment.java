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

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountFragment extends Fragment implements View.OnClickListener{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Button button_register, button_login;
    EditText email_editT, password_editT, username_editT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        button_register = rootView.findViewById(R.id.button_register);
        button_login = rootView.findViewById(R.id.button_login);

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
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "");

                if (v.getId() == R.id.button_register){
                    //API rejestracja
                    //"http://adrinna-pilawa-314-a-2022-04.int.heag.live/api/user/register?email=emailB@email.com&password=securepassword123&device_name=AndroidSmartphone&name=Annie"
                    String url_data = "https://adrinna-pilawa-314-a-2022-04.int.heag.live/api/user/register?email=";
                    url_data += email_editT.getText().toString() + "&password=" + password_editT.getText().toString();
                    url_data += "&device_name=AndroidSmartphone&name=" + username_editT.getText().toString();
                    System.out.println("url_data: " + url_data);

                    Request request = new Request.Builder()
                            .url(url_data)
                            .method("POST", body)
                            .addHeader("Accept", "application/json")
                            .build();

                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                Response response = client.newCall(request).execute();
                                System.out.println("RESPONSE: " + response.body().string());
                                //tutaj się coś pierdoli z protokołem SSL






                                //if (API response == 200){  udało się zarejestrować, mamy klucz autoryzacyjny
                                //tylko co potem robimy z tym kluczem?
                                Toast.makeText(v.getContext(), "Zarejestrowano !!NIEPRZETESTOWANE!!", Toast.LENGTH_SHORT).show();
                                //}
                                //else{ email zajęty np., wypisz wiadomość ze zwróconego jsona w toast, try again, bla bla

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
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