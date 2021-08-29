package com.mongodb.ispfieldtechapp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.model.Customer
import com.mongodb.ispfieldtechapp.data.model.MetaData
import com.mongodb.ispfieldtechapp.data.realmsync.LiveRealmResults
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.App
import io.realm.mongodb.sync.SyncConfiguration

class CustomerViewModel : ViewModel() {

    private var realm : Realm? = null
    var technician : String? = null
    var _customerObject : LiveRealmResults<Customer>? = null

    fun openRealm(techApp: App, techName: String, callback : (result: String? ) -> Unit) {
        Log.v("QUICKSTART", "Opening Customer Realm.")
        technician = techName

        val config = SyncConfiguration.Builder(techApp.currentUser(), techName)
            //.allowWritesOnUiThread(true) //read only
            .allowQueriesOnUiThread(true)
            .build()

        //this.realm = Realm.getInstance(config)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                Log.v("QUICKSTART", "Realm opened for customer")
                this@CustomerViewModel.realm = realm

                this@CustomerViewModel._customerObject = LiveRealmResults(realm.where<Customer>().equalTo("technicianId", techName).findAll())
                //Log.v("QUICKSTART", "Ticket statuses: ${arrayOf(this@MetaDataViewModel.getStatuses())}")

                callback.invoke("Customers loaded")
            }

            fun onError (e: java.lang.Exception) {
                Log.e("QUICKSTART", "Failed to get metadata realm instance: $e")
                this@CustomerViewModel.realm = null
                this@CustomerViewModel.technician = null
            }

        })
    }
}