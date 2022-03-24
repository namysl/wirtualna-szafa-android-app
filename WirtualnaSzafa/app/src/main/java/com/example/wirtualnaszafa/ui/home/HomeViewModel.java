package com.example.wirtualnaszafa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jestem w java/costam/ui/slideshow/HomeViewModel");
    }

    public LiveData<String> getText() {
        return mText;
    }
}