package com.murrayde.animekingmobile.di.random;

import com.murrayde.animekingmobile.network.random.TriviaDbApiEndpoint;

import dagger.Component;

@Component(modules = {QuestionRandomModule.class})
public interface TriviaApiComponent {
    TriviaDbApiEndpoint getTriviaApiEndpoint();
}
