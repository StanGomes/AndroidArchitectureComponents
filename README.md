# Android Architecture Components demonstration

A simple demonstration of using Android Architecture Components + MVVM pattern using TMDB api.
It follows the practice of single source of truth. The views have no idea where the data is coming from.

This project is evergrowing and I will keep updating it with more components as I learn.

The following diagram explains the architecture

![image1](https://imgur.com/q5fSEuw.jpg)


### Prerequisites

This app targets API 28

![image1](https://imgur.com/VO6QRWpl.jpg)
![image2](https://imgur.com/DYkrdaGl.jpg)
![image3](https://imgur.com/6nnSfZHl.jpg)
![image4](https://imgur.com/BI5bfDkl.jpg)
![image5](https://imgur.com/2PekfBjl.jpg)
![image6](https://imgur.com/N7nsqmTl.jpg)
![image7](https://imgur.com/vmLDYHfl.jpg)


## Built With

* [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
* [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) - A collection of libraries that help you design robust, testable, and maintainable apps.
  
  -[LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.

  -[Room](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
  
  -[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
  
* [Dagger2](https://github.com/google/dagger) - A fast dependency injector for Android and Java
