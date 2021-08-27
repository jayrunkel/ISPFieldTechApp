package com.mongodb.ispfieldtechapp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.model.MetaData
import com.mongodb.ispfieldtechapp.data.realmsync.LiveRealmResults
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.App
import io.realm.mongodb.sync.SyncConfiguration
import java.util.*

class MetaDataViewModel : ViewModel() {

    private var realm : Realm? = null
    var _metaDataObject : LiveRealmResults<MetaData>? = null

    fun openRealm(techApp: App, callback : (result: String? ) -> Unit) {
        Log.v("QUICKSTART", "Opening MetaData Realm.")

        val config = SyncConfiguration.Builder(techApp.currentUser(), "All")
            //.allowWritesOnUiThread(true) //read only
            .allowQueriesOnUiThread(true)
            .build()

        //this.realm = Realm.getInstance(config)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                Log.v("QUICKSTART", "Realm opened for meta data")
                this@MetaDataViewModel.realm = realm

                this@MetaDataViewModel._metaDataObject = LiveRealmResults(realm.where<MetaData>().equalTo("technicianId", "All").findAll())
                Log.v("QUICKSTART", "Ticket statuses: ${arrayOf(this@MetaDataViewModel.getStatuses())}")

                callback.invoke("metadata loaded")
            }

            fun onError (e: java.lang.Exception) {
                Log.e("QUICKSTART", "Failed to get metadata realm instance: $e")
                this@MetaDataViewModel.realm = null
            }

        })
    }

    fun getStatuses () : Array<String> {
        var returnVal = arrayOf<String>()
        _metaDataObject?.value?.let {
            if (it.isNotEmpty()) returnVal = it.first().getTicketStatuses()
        }
        return returnVal

    }
}