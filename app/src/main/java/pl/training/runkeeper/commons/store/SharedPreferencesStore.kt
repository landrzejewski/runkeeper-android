package pl.training.runkeeper.commons.store

import android.content.Context

class SharedPreferencesStore(private val context: Context) : Store {

    override fun set(key: String, value: String) = getPreferences()
        .edit()
        .putString(key, value)
        .apply()

    override fun get(key: String, defaultValue: String) = getPreferences()
        .getString(key, defaultValue) ?: defaultValue

    private fun getPreferences() = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

}