package com.example.wirtualnaszafa.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jestem w java/costam/ui/slideshow/GalleryViewModel");
    }

    public LiveData<String> getText() {
        return mText;
    }
}