package com.example.wirtualnaszafa.ui.account;

import static com.example.wirtualnaszafa.Constants.ACCOUNT_PREFERENCES_KEY;
import static com.example.wirtualnaszafa.Constants.TOKEN_KEY;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wirtualnaszafa.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFragment extends Fragment{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private AccountViewModel accountViewModel;

    Button button_register, button_login;
    EditText email_editT, password_editT, username_editT;
    ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        button_register = rootView.findViewById(R.id.button_register);
        button_login = rootView.findViewById(R.id.button_login);

        email_editT = rootView.findViewById(R.id.editTextEmail);
        password_editT = rootView.findViewById(R.id.editTextPassword);
        username_editT = rootView.findViewById(R.id.editTextUsername);

        loading = rootView.findViewById(R.id.loading);

        button_register.setOnClickListener(view -> {
            if (isEmpty(email_editT) || isEmpty(password_editT) || isEmpty(username_editT)){
                Toast.makeText(view.getContext(), "Należy wypełnić wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }
            //można też wymagać, żeby input dla loginu/hasła był nie krótszy niż X
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawidłowy format");
                Toast.makeText(view.getContext(), "Wprowadź poprawny adres email", Toast.LENGTH_SHORT).show();
                return;
            }
            accountViewModel.register(email_editT.getText().toString(), password_editT.getText().toString(), username_editT.getText().toString());
        });
        button_login.setOnClickListener(view -> {
            if (isEmpty(email_editT) || isEmpty(password_editT)){
                Toast.makeText(view.getContext(), "Należy wypełnić wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }
            //można też wymagać, żeby input dla loginu/hasła był nie krótszy niż X
            if (!validate(email_editT.getText().toString())) {
                //regex validation
                email_editT.setError("Nieprawidłowy format");
                Toast.makeText(view.getContext(), "Wprowadź poprawny adres email", Toast.LENGTH_SHORT).show();
                return;
            }
            accountViewModel.login(email_editT.getText().toString(), password_editT.getText().toString());
        });

        accountViewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        accountViewModel.getAuthorized().observe(getViewLifecycleOwner(), token -> {
            if(token != null){
                requireContext().getSharedPreferences(ACCOUNT_PREFERENCES_KEY, Context.MODE_PRIVATE).edit().putString(TOKEN_KEY, token).apply();
                //TODO redirect to main screen
                Toast.makeText(getContext(), "Autoryzacja się powiodła", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
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