package com.retr0.projectretr0.data.carModels

/** A Car Models failure with its stable [CarModelErrorCode]. [message] keeps the specific text (file, count…). */
open class CarModelException(
    val error: CarModelErrorCode,
    message: String = error.userMessage,
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Any throwable as a coded failure. Already-coded failures keep their code. A binder failure anywhere in the
 * cause chain (helper process died, Shizuku restarted) is always [CarModelErrorCode.SERVICE_UNAVAILABLE], so the
 * user sees "reconnect" rather than a misleading copy/hash error. Other uncoded ones become [fallback].
 */
fun Throwable.asCarModelException(fallback: CarModelErrorCode = CarModelErrorCode.UNEXPECTED): CarModelException {
    if (this is CarModelException) return this
    if (isBinderFailure()) return CarModelException(
        CarModelErrorCode.SERVICE_UNAVAILABLE,
        "${CarModelErrorCode.SERVICE_UNAVAILABLE.userMessage} The operation stopped (${javaClass.simpleName}); reconnect Shizuku and try again.",
        this,
    )
    return CarModelException(fallback, message?.takeIf { it.isNotBlank() } ?: fallback.userMessage, this)
}

/** True when this (or a cause) is a binder transport failure: DeadObjectException, TransactionTooLarge, etc. */
fun Throwable.isBinderFailure(): Boolean = generateSequence(this) { it.cause }.take(8).any { it is android.os.RemoteException }
