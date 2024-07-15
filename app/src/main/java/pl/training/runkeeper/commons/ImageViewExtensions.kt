package pl.training.runkeeper.commons

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources

fun ImageView.setDrawable(name: String) {
    val imageId = resources.getIdentifier(name, "drawable", context.opPackageName)
    val drawable = AppCompatResources.getDrawable(context, imageId)
    setImageDrawable(drawable)
}