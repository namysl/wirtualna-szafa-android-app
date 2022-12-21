package com.example.wirtualnaszafa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wirtualna Szafa\nto aplikacja modowa\n" +
                "do katalogowania i losowania zestawów ubrań.");
    }

    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }
}