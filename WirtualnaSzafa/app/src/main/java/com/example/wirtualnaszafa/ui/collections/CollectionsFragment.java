package com.example.wirtualnaszafa.ui.collections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;

public class CollectionsFragment extends Fragment {
    Button button_next, button_previous;
    ImageView iv_top, iv_bot, iv_accesories, iv_shoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }
}