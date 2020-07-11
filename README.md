
  
# 500px Clone App   
## Introduction  
  This is a simple implementation of the popular images feature of 500px which uses [500px API](https://github.com/500px/legacy-api-documentation)     
    
The application is built using the most modern Android development strategies.    
    
The codebase focuses on following key things:    
- Code structuring as per clean Architecture  
- Using MVVM Pattern as per Google's recommendation  
- Android Architecture Components (LiveData, ViewModel, Navigation)    
- Kotlin features (Lambdas, Extension functions, typealias, sealed class and Coroutines)    
  
## API Key  
In order to use the app, you would need to create a file called "config" your project root folder and in it add your 500px API key using the string "apiKey={your API key}". Then go back to the project and force a gradle sync. The file is part of the .gitignore of the project in order to prevent any accidental commit.  
  
## App Overview  
  
The app features a 2 screen design  
  
- List screen displaying popular images in a paginated fashion  
     
   <img alt="Popular Images List" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/List_Screen.png?token=AAZKVI5JSKTPBPOXGD44ZP27BHZDY" />  
     
- Details screen showing the details of the image on click on the image in the list screen  
  
    <img alt="Image Details" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/Details_Screen.png?token=AAZKVI3QDVE7BNAZ2U25UI27BHZDW" />  

Navigation between the screens has been done using the Jetpack Navigation library and the following is its nav graph:

<img alt="Nav Graph" height="250px" src="https://raw.githubusercontent.com/prasannajeet/500px-clone-app/master/Navigation_Graph.png?token=AAZKVI67JYGQJ2R5RGZMYOK7BHZD2" />  
  
## Libraries The App uses libraries and tools used to build Modern Android application, mainly part of Android Jetpack   
    
- [Kotlin](https://kotlinlang.org/) first    
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)    
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)    
- [Retrofit](https://square.github.io/retrofit/)    
- [Moshi](https://github.com/square/moshi)    
- [Picasso](https://square.github.io/picasso/)    
- [Android KTX](https://developer.android.com/kotlin/ktx) features  
    
### Scope for Improvements    
 The app can be further improved with the addition of the following features    
    
- Dagger2/Koin for resolving dependencies  
- Flow  instead of Coroutines for API call results   
- Dynamic image sizes using multiple ViewHolders for different image sizes instead of current GridLayoutManager implementation
