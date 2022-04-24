package com.example.wirtualnaszafa.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.wirtualnaszafa.remote.RemoteService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AccountViewModel extends ViewModel {

    private final RemoteService remoteService = new RemoteService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<String> authorized = new MutableLiveData<>(null);
    public LiveData<String> getAuthorized() { return authorized; }

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public LiveData<Boolean> getLoading() { return loading; }

    public void login(String username, String password) {
        Disposable request = remoteService.login(username, password)
                .doOnSubscribe(disposable -> loading.postValue(true))
                .doOnTerminate(() -> loading.postValue(false))
                .subscribe(token -> {
                    if(token != null && !token.isEmpty()) {
                        authorized.postValue(token);
                    }
                }, throwable -> {
                    authorized.postValue(null);
                });

        compositeDisposable.add(request);
    }

    public void register(String username, String password, String name) {
        Disposable request = remoteService.register(username, password, name)
                .doOnSubscribe(disposable -> loading.postValue(true))
                .doOnTerminate(() -> loading.postValue(false))
                .subscribe(token -> {
                    if(token != null && !token.isEmpty()) {
                        authorized.postValue(token);
                    }
                }, throwable -> {
                    authorized.postValue(null);
                });

        compositeDisposable.add(request);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }
}