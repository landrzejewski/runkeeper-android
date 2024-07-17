package pl.training.runkeeper.commons.store

interface Store {

    fun set(key: String, value: String)

    fun get(key: String, defaultValue: String = ""): String

}