package com.murrayde.retrofittesting.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.murrayde.retrofittesting.di.DaggerApiComponent;
import com.murrayde.retrofittesting.model.AnimeComplete;
import com.murrayde.retrofittesting.model.AnimeData;
import com.murrayde.retrofittesting.network.AnimeApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    @Inject
    AnimeApiService animeApiService;

    public MainActivityViewModel() {
        DaggerApiComponent.create().inject(this);
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<List<AnimeData>> animeData = new MutableLiveData<List<AnimeData>>();

    /**We can't add 'AnimeData' objects to animeData, only assigning data with setValue/postValue is possible.
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
                animeApiService.getAnimeComplete()
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