<h1 align="center">Anime King</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a> 
</p>

<p align="center">  
Anime King is an anime trivia game based on modern Android application tech-stacks and MVVM architecture.<br>This project includes the new library Hilt for implementing dependency injection.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern.
</p>
</br>

<p align="center">
<img src="collage.png"/>
</p>

## Download
Go to the [Releases](https://github.com/skydoves/Pokedex/releases) to download the latest APK.


<img src="main_screen_preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [RxJava](https://github.com/ReactiveX/RxJava) based for asynchronous events.
- Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct lightweight http API response and handling error responses.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [TransformationLayout](https://github.com/skydoves/transformationlayout) - implementing transformation motion animations.
- [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently.
- [Bundler](https://github.com/skydoves/bundler) - Android Intent & Bundle extensions that insert and retrieve values elegantly.
- [Timber](https://github.com/JakeWharton/timber) - logging.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Custom Views
  - [Rainbow](https://github.com/skydoves/rainbow) - An easy way to apply gradations and tinting for Android.
  - [AndroidRibbon](https://github.com/skydoves/androidribbon) - A simple way to implement a  beautiful ribbon with the shimmering on Android.
  - [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView, fully customizable with animations.

## MAD Score
![summary](https://user-images.githubusercontent.com/24237865/102366914-84f6b000-3ffc-11eb-8d49-b20694239782.png)
![kotlin](https://user-images.githubusercontent.com/24237865/102366932-8a53fa80-3ffc-11eb-8131-fd6745a6f079.png)

## Architecture
Pokedex is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

Pokedex using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/Pokedex/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

# License
```xml
Designed and developed by 2020 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
