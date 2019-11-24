package ge.tbc.tbcdemo.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : BaseView> {

    var view: T? = null

    protected var isFirstAttach: Boolean = true

    private val compositeDisposable = CompositeDisposable()

    fun attach(view: T) {
        this.view = view
        if (isFirstAttach) {
            onFirstAttach()
            isFirstAttach = false
        } else {
            onNextAttach()
        }
    }

    protected open fun onFirstAttach() {
    }

    protected open fun onNextAttach() {
    }

    fun detach() {
        onDetach()
        view = null
        compositeDisposable.clear()
    }

    protected open fun onDetach() {
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
    }
}