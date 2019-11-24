package ge.tbc.tbcdemo.presentation.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

@SuppressLint("Registered")
open
class BaseActivity<T : BasePresenter<out BaseView>> : AppCompatActivity() {

    @field:Inject
    lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    open fun showError() {
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}