package com.murrayde.animekingtrivia.di.random;

import com.murrayde.animekingtrivia.network.random.TriviaDbApiEndpoint;

import dagger.Component;

@Component(modules = {QuestionRandomModule.class})
public interface TriviaApiComponent {
    TriviaDbApiEndpoint getTriviaApiEndpoint();
}
