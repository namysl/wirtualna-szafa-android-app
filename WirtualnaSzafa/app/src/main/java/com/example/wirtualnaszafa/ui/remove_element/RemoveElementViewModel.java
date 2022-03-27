package com.example.wirtualnaszafa.ui.remove_element;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RemoveElementViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RemoveElementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jesteś w RemoveElement");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
