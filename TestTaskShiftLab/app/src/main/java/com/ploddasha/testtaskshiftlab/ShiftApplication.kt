package com.ploddasha.testtaskshiftlab

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ploddasha.testtaskshiftlab.data.UserNameRepository

private const val USER_NAME = ""
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_NAME
)

class ShiftApplication: Application() {
    lateinit var userNameRepository: UserNameRepository

    override fun onCreate() {
        super.onCreate()
        userNameRepository = UserNameRepository(dataStore)
    }
}