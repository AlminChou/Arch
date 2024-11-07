package com.almin.arch.store

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

open abstract class DataStoreOwner(name: String) : IDataStoreOwner {
  private val Context.dataStore by preferencesDataStore(name)
  override val dataStore get() = context.dataStore
}