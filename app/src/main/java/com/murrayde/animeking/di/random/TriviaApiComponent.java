package com.murrayde.animeking.di.random;

import com.murrayde.animeking.network.random.TriviaDbApiEndpoint;

import dagger.Component;

@Component(modules = {QuestionRandomModule.class})
public interface TriviaApiComponent {
    TriviaDbApiEndpoint getTriviaApiEndpoint();
}
