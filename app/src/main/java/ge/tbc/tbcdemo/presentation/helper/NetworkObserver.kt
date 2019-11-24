package ge.tbc.tbcdemo.presentation.helper

import ge.tbc.tbcdemo.presentation.base.BaseView
import io.reactivex.observers.DisposableSingleObserver
import java.lang.ref.WeakReference

abstract class NetworkObserver<T> constructor(view: BaseView) : DisposableSingleObserver<T>() {

    private val weakReference: WeakReference<BaseView> = WeakReference(view)

    abstract fun onSuccessResponse(response: T)

    open fun onFinish() {
    }

    open fun onError() {
        val view = weakReference.get()
        view?.showError()
    }

    override fun onSuccess(response: T) {
        onFinish()
        onSuccessResponse(response)
    }

    override fun onError(e: Throwable) {
        onFinish()
        onError()
    }
}