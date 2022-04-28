# MarvelSuperHeroes
Sample Android app made with the Marvel API.

## How to get a Marvel API key

Go [https://developer.marvel.com/](https://developer.marvel.com/) > Sign in / Join > [Get a key](https://developer.marvel.com/account). 

## Installation

### Android Studio:

Clone the repo and open the project in Android Studio. Go to ApiConstants.kt and add your public and private keys.

### Android device:

Run the application on your emulator or physical device.

## Technologies

To build this app I used:

* Kotlin.
* Android Jetpack navigation component.
* MVVM (Model-View-ViewModel) design pattern.
* Kotlin Flow.
* Jetpack Compose.

Works with ```Android SDK 24+```

__Third-party libraries:__

* [Retrofit2](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Landscapist](https://github.com/skydoves/landscapist)
* [Swipe Refresh](https://github.com/google/accompanist)
* [Material components](https://github.com/material-components/material-components-android)
* [Hilt](https://dagger.dev/hilt/)
* [Mockito](https://github.com/mockito/mockito)

## About the project

![Character list screen](https://github.com/stacyjacks/MarvelSuperHeroes/blob/develop/screenshot1.png) ![Character list screen](https://github.com/stacyjacks/MarvelSuperHeroes/blob/develop/screenshot2.png)

This project displays a list of __Marvel©__ characters in a 2-column grid and in alphabetical order, with an infinite scroll implemented through the __Paging 3__ library for a more comfortable browsing experience.

The user can then navigate to a detail fragment, which displays some additional information about each individual character of the __Marvel Universe__.

To retrieve the data I used Retrofit and the Marvel API (https://developer.marvel.com/docs), where the endpoint ```/v1/public/characters``` fetches all the characters from the Marvel Universe, and  ```/v1/public/characters/{characterId}``` retrieves an individual character based on their assigned ID.
To load the images retrieved from the API I used the __Landscapist__ library, which is an image loading library specially developed for __Jetpack Compose__.

Due to the limited information provided by the API, a button with a link is included, allowing the user to navigate to a URL, where they can read more about each character on the [Marvel.com](https://www.marvel.com/) website.
