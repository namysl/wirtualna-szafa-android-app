package com.example.wirtualnaszafa.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wirtualnaszafa.model.User;
import com.example.wirtualnaszafa.remote.RemoteService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomeViewModel extends ViewModel {

    private final RemoteService remoteService = new RemoteService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<String> mText;

    private final MutableLiveData<User> test = new MutableLiveData<>(null);
    public LiveData<User> getTest() { return test; }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wirtualna Szafa\nto aplikacja modowa\n" +
                       "do katalogowania i losowania zestawów ubrań.");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void test(String token) {
        if(token == null) return;
        Disposable request = remoteService.getUser(token)
                .subscribe( user -> {
                    test.postValue(user);
                }, throwable -> {
                    //bad
                });

        compositeDisposable.add(request);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }
}