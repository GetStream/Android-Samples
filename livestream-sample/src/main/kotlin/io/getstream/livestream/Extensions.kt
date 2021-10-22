package io.getstream.livestream

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String, @DrawableRes placeholder: Int) {
    if (url.isNotEmpty()) {
        Picasso.get().load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fit()
            .into(this)
    } else {
        Picasso.get().cancelRequest(this)
        setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                placeholder
            )
        )
    }
}

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun EditText.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}
