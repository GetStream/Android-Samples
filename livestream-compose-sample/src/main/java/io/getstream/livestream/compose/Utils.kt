package io.getstream.livestream.compose

import android.content.Context
import android.content.res.TypedArray
import java.util.Random

fun Context.randomArtWork(): Int {
    val rand = Random()
    val artworksArray: TypedArray = resources.obtainTypedArray(R.array.artwork)
    val rndInt: Int = rand.nextInt(artworksArray.length())
    return artworksArray.getResourceId(rndInt, 0).also {
        artworksArray.recycle()
    }
}

fun Context.randomDescription(): Int {
    val rand = Random()
    val descArray: TypedArray = resources.obtainTypedArray(R.array.description)
    val rndInt: Int = rand.nextInt(descArray.length())
    return descArray.getResourceId(rndInt, 0).also {
        descArray.recycle()
    }
}
