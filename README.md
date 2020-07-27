# Kotlin MVVM app using clean architecture, Jetpack, Hilt, Retrofit and Coroutines Flow API
 
## Introduction      
This application is a simple implementation of the popular images feature of 500px which using the [500px API](https://github.com/500px/legacy-api-documentation) built using modern Android development strategies focusing on the following key aspects:     
- Code structuring as per clean Architecture      
- Using MVVM Pattern as per Google's recommendation      
- Android Architecture Components (LiveData, ViewModel, Navigation)        
- Kotlin features (Lambdas, Extension functions, typealias, sealed class and Coroutines)        
      
## API Key 
In order to use the app, you would need to create a file called "config" your project root folder and in it add your 500px API key using the string "apiKey={your API key}". Then go back to the project and force a gradle sync. The file is part of the .gitignore of the project in order to prevent any accidental commit.      
      
## App Overview      
 The app features a 2 screen navigation      
      
- List screen displaying popular images in a paginated fashion      
         
   <img alt="Popular Images List" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/List_Screen.png" />      
         
- Details screen showing the details of the image on click on the image in the list screen      
      
    <img alt="Image Details" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/Details_Screen.png" />      
    
Navigation between the screens has been done using the Jetpack Navigation library and the following is its nav graph:    
    
<img alt="Nav Graph" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/Navigation_Graph.png" />      
      
## Libraries The App uses libraries and tools used to build Modern Android application, mainly part of Android Jetpack 
- [Kotlin](https://kotlinlang.org/) first
- [Coroutines Flow API](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Android desugaring for Java 8+ APIs](https://developer.android.com/studio/write/java8-support#library-desugaring)
- [Retrofit](https://square.github.io/retrofit/)
- [Moshi](https://github.com/square/moshi)
- [Picasso](https://square.github.io/picasso/)
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Android KTX](https://developer.android.com/kotlin/ktx) features
- [MockK](https://mockk.io/) for unit testing
        
### Scope for Improvements        
 The app can be further improved with the addition of the following features
- Dynamic image sizes using multiple ViewHolders for different image sizes instead of current GridLayoutManager implementation  
- Espresso Tests