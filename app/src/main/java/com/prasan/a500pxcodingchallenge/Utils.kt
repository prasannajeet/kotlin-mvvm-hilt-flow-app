package com.prasan.a500pxcodingchallenge

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.prasan.a500pxcodingchallenge.model.datamodel.Photo
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * Readable naming convention for Network call lambda
 * @since 1.0
 */
typealias NetworkCall<T> = suspend () -> Response<T>

/**
 * [typealias] for lambda passed when a photo is tapped on in [PopularPhotosFragment]
 */
typealias PhotoItemClickListener = (Photo) -> Unit

/**
 * Sealed class type-restricts the result of API calls to success and failure. The type
 * <T> represents the model class expected from the API call in case of a success
 * In case of success, the result will be wrapped around the OnSuccessResponse class
 * In case of error, the exception causing the error will be wrapped around OnErrorResponse class
 * @author Prasan
 * @since 1.0
 */
sealed class APICallResult<out T : Any> {
    data class OnSuccessResponse<out T : Any>(val data: T) : APICallResult<T>()
    data class OnErrorResponse(val exception: Exception) : APICallResult<Nothing>()
}

/**
 * Utility function that works to perform a Retrofit API call and return either a success model
 * instance or an error message wrapped in an [Exception] class
 * @param messageInCaseOfError Custom error message to wrap around [APICallResult.OnErrorResponse]
 * with a default value provided for flexibility
 * @param apiCall lambda representing a suspend function for the Retrofit API call
 * @return [APICallResult.OnSuccessResponse] object of type [T], where [T] is the success object wrapped around
 * [APICallResult.OnSuccessResponse] if network call is executed successfully, or [APICallResult.OnErrorResponse]
 * object wrapping an [Exception] class stating the error
 * @since 1.0
 */
suspend fun <T : Any> safeApiCall(
    messageInCaseOfError: String = "Network IO error",
    apiCall: NetworkCall<T>
) =
    flow {
        val response = apiCall()
        if (response.isSuccessful) {
            emit(APICallResult.OnSuccessResponse(response.body()!!))
            return@flow
        }
        emit(APICallResult.OnErrorResponse(Exception("Error Occurred during getting safe Api result, Custom ERROR - $messageInCaseOfError")))
    }


/**
 * [ImageView] extension function adds the capability to loading image by directly specifying
 * the url
 * @param url Image URL
 */
fun ImageView.loadUrl(
    @NonNull url: String,
    placeholder: Drawable = this.context.getDrawable(R.drawable.ic_launcher_foreground)!!,
    error: Drawable = this.context.getDrawable(R.drawable.ic_launcher_background)!!
) {
    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

/**
 * Alternate implementation to the above loadUrl method using data binding instead of extn functions
 * @param view [ImageView] to load the image via url
 * @param url URL of the image
 * @param error Error [Drawable] to show when image doesn't load successfully
 * @since 1.0
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.loadUrl(url)
}

/**
 * Lets the UI act on a controlled bound of states that can be defined here
 * @author Prasan
 * @since 1.0
 */
sealed class UIState<out T : Any> {

    /**
     * Represents UI state where the UI should be showing a loading UX to the user
     * @param isLoading will be true when the loading UX needs to display, false when not
     */
    data class LoadingState(val isLoading: Boolean) : UIState<Nothing>()

    /**
     * Represents the UI state where the operation requested by the UI has been completed successfully
     * and the output of type [T] as asked by the UI has been provided to it
     * @param output result object of [T] type representing the fruit of the successful operation
     */
    data class OnOperationSuccess<out T : Any>(val output: T) : UIState<T>()

    /**
     * Represents the UI state where the operation requested by the UI has failed to complete
     * either due to a IO issue or a service exception and the same is conveyed back to the UI
     * to be shown the user
     * @param exception [Exception] instance containing the root cause of the failure in a [String]
     */
    data class OnOperationFailed(val throwable: Throwable) : UIState<Nothing>()
}

/**
 * Extension function on a fragment to show a toast message
 */
fun Fragment.showToast(@NonNull message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

/**
 * Extension function on a [Photo] class that will convert the camera data into a single
 * string to be shown on the details screen
 */
fun Photo.getFormattedExifData() = StringBuilder().apply {

    append(if (camera != null && camera.isBlank()) "Unknown Camera" else camera)
    append(" + ")
    append(if (lens != null && lens.isBlank()) "Unknown Lens" else lens)
    append(" | ")
    append(if (focalLength != null && focalLength.isBlank()) "0mm" else focalLength + "mm")
    appendln()
    append(if (aperture != null && aperture.isBlank()) "f0" else "f/$aperture")
    append(" | ")
    append(if (shutterSpeed != null && shutterSpeed.isBlank()) "0s" else shutterSpeed + "s")
    append(" | ")
    append(if (iso != null && iso.isBlank()) "ISO0" else "ISO$iso")
}.run {
    toString()
}

const val baseURL = "https://api.500px.com/"