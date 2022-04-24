package com.example.wirtualnaszafa.ui.home;

import static com.example.wirtualnaszafa.Constants.ACCOUNT_PREFERENCES_KEY;
import static com.example.wirtualnaszafa.Constants.TOKEN_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wirtualnaszafa.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        homeViewModel.getTest().observe(getViewLifecycleOwner(), result -> {
            if(result == null) return;
            System.out.println(result);
        });

        String token = requireContext().getSharedPreferences(ACCOUNT_PREFERENCES_KEY, Context.MODE_PRIVATE).getString(TOKEN_KEY, null);
        homeViewModel.test(token);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}