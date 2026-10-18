package com.retr0.projectretr0.data.carModels

/** A Car Models failure with its stable [CarModelErrorCode]. [message] keeps the specific text (file, count…). */
open class CarModelException(
    val error: CarModelErrorCode,
    message: String = error.userMessage,
    cause: Throwable? = null,
) : Exception(message, cause)

/** Any throwable as a coded failure; uncoded ones (binder, IO) become [fallback] and keep their message. */
fun Throwable.asCarModelException(fallback: CarModelErrorCode = CarModelErrorCode.UNEXPECTED): CarModelException =
    this as? CarModelException ?: CarModelException(fallback, message?.takeIf { it.isNotBlank() } ?: fallback.userMessage, this)
