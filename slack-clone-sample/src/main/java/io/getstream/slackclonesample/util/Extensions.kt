package io.getstream.slackclonesample.util

import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun Fragment.showToast(@StringRes resId: Int) {
    showToast(getString(resId))
}

fun Fragment.showToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.useAdjustResize() = setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

private fun Fragment.setSoftInputMode(flags: Int) {
    (activity ?: return).window.setSoftInputMode(flags)
}

inline fun <VM : ViewModel> viewModelProviderFactoryOf(
    crossinline f: () -> VM
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = f() as T
}

