package com.prasan.a500pxcodingchallenge

import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import retrofit2.Response

/**
 * Readable naming convention for Network call lambda
 * @since 1.0
 */
typealias NetworkCall<T> = suspend () -> Response<T>

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
 * Utility function that works to perform a Retrofit API call and returns either a success model
 * instance or a error message wrapped in a [Exception] class
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
): APICallResult<T> {
    val response = apiCall()
    if (response.isSuccessful) return APICallResult.OnSuccessResponse(response.body()!!)
    return APICallResult.OnErrorResponse(Exception("Error Occurred during getting safe Api result, Custom ERROR - $messageInCaseOfError"))
}

/**
 * [ImageView] extension function adds the capability to loading image by directly specifying
 * the url
 * @param url Image URL
 */
fun ImageView.loadUrl(@NonNull url: String) {
    Picasso.get()
        .load(url)
        .error(R.drawable.ic_launcher_foreground) // Sample icon to save time
        .into(this)
}

/**
 * Lets the UI act on a controlled bound of states that can be defined here
 * @author Prasan
 * @since 1.0
 */
sealed class UIState<out T : Any> {
    data class LoadingState(val isLoading: Boolean) : UIState<Nothing>()
    data class OnOperationSuccess<out T : Any>(val output: T) : UIState<T>()
    data class OnOperationFailed(val exception: Exception) : UIState<Nothing>()
}

fun Fragment.showToast(@NonNull message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

const val baseURL = "https://api.500px.com/"