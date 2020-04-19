package com.murrayde.animekingtrivia.di.random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.murrayde.animekingtrivia.di.OkHttpClientModule;
import com.murrayde.animekingtrivia.network.random.TriviaDbApiEndpoint;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class QuestionRandomModule {

    @SuppressWarnings("FieldCanBeLocal")
    private static String BASE_URL = "https://opentdb.com/";

    @Provides
    TriviaDbApiEndpoint provideTriviaApiEndpoint(Retrofit retrofit) {
        return retrofit.create(TriviaDbApiEndpoint.class);
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
