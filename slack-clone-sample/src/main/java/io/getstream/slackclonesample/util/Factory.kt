package io.getstream.slackclonesample.util

interface Factory<T> {

    fun create(): T
}