package com.example.wirtualnaszafa.ui.randomize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RandomizeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RandomizeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jesteś w Randomize");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
