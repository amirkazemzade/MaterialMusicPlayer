package me.amirkazemzade.materialmusicplayer.domain.model


sealed interface StatusI<T, S, V> {
    val data: T?
    val message: S?
    val partialMessage: V?
}

sealed interface SuccessI<T, S, V> : StatusI<T, S, V>
sealed interface ErrorI<T, S, V> : StatusI<T, S, V>
sealed interface LoadingI<T, S, V> : StatusI<T, S, V>

/***
 * Status with generic type of [T] for [data] value, generic type of [S] for [message] and generic type of [V] [partialMessage] values
 */
sealed class StatusCore<T, S, V>(
    override val data: T? = null,
    override val message: S? = null,
    override val partialMessage: V? = null,
) :
    StatusI<T, S, V> {

    data class Success<T, S, V>(override val data: T, override val partialMessage: V? = null) :
        StatusCore<T, S, V>(data = data, partialMessage = partialMessage), SuccessI<T, S, V>

    data class Error<T, S, V>(override val message: S, override val data: T? = null) :
        StatusCore<T, S, V>(data = data, message = message), ErrorI<T, S, V>

    data class Loading<T, S, V>(override val data: T? = null) :
        StatusCore<T, S, V>(data = data), LoadingI<T, S, V>
}

/***
 * Status with generic type of [T] for [data] value and generic type of [S] for [message] and [partialMessage] values
 */
sealed class StatusGeneric<T, S>(
    override val data: T? = null,
    override val message: S? = null,
    override val partialMessage: S? = null,
) :
    StatusI<T, S, S> {

    data class Success<T, S>(override val data: T, override val partialMessage: S? = null) :
        StatusGeneric<T, S>(data = data, partialMessage = partialMessage), SuccessI<T, S, S>

    data class Error<T, S>(override val message: S, override val data: T? = null) :
        StatusGeneric<T, S>(data = data, message = message), ErrorI<T, S, S>

    data class Loading<T, S>(override val data: T? = null) :
        StatusGeneric<T, S>(data = data), LoadingI<T, S, S>
}

/***
 * Status with generic type of [T] for [data] value and [String] type for [message] and [partialMessage] values
 */
sealed class Status<T>(
    override val data: T? = null,
    override val message: String? = null,
    override val partialMessage: String? = null,
) :
    StatusI<T, String, String> {

    data class Success<T>(override val data: T, override val message: String? = null) :
        Status<T>(data, message), SuccessI<T, String, String>

    data class Error<T>(override val message: String, override val data: T? = null) :
        Status<T>(data, message), ErrorI<T, String, String>

    data class Loading<T>(override val data: T? = null) :
        Status<T>(data), LoadingI<T, String, String>
}