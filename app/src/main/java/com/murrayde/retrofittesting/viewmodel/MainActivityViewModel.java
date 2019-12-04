package com.murrayde.retrofittesting.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.murrayde.retrofittesting.di.AnimeApiComponent;
import com.murrayde.retrofittesting.di.DaggerAnimeApiComponent;
import com.murrayde.retrofittesting.model.AnimeComplete;
import com.murrayde.retrofittesting.model.AnimeData;
import com.murrayde.retrofittesting.network.AnimeApiEndpoint;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    private AnimeApiEndpoint animeApiEndpoint;

    public MainActivityViewModel() {
        AnimeApiComponent daggerAnimeApiComponent = DaggerAnimeApiComponent.builder()
                .build();
        animeApiEndpoint = daggerAnimeApiComponent.getAnimeApiEndpoint();
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<List<AnimeData>> animeData = new MutableLiveData<List<AnimeData>>();

    /**
     * We can't add 'AnimeData' objects to animeData, only assigning data with setValue/postValue is possible.
     * Create a List<AnimeData> so we can add 'AnimeData' objects to the list and then assign animeData to the list.
     */
    private List<AnimeData> tempAnimeData = new ArrayList<>();

    public void refresh() {
        tempAnimeData.clear();
        loadAnime();
    }

    public LiveData<List<AnimeData>> getAnimeData() {
        return animeData;
    }

    private void loadAnime() {
        disposable.add(
                animeApiEndpoint.getAnimeComplete()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<AnimeComplete>() {

                            @Override
                            public void onSuccess(AnimeComplete animeComplete) {
                                tempAnimeData.addAll(animeComplete.getData());
                                animeData.setValue(tempAnimeData);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}