package com.example.wirtualnaszafa.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jestem w java/costam/ui/slideshow/SlideshowViewModel");
    }

    public LiveData<String> getText() {
        return mText;
    }
}