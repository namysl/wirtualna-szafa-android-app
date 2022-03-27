package com.example.wirtualnaszafa.ui.add_element;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddElementViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddElementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jesteś w AddElement");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
