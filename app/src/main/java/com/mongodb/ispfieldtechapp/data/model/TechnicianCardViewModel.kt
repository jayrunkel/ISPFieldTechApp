package com.mongodb.ispfieldtechapp.data.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.realmsync.LiveRealmResults
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import io.realm.mongodb.App
import io.realm.mongodb.sync.SyncConfiguration
import java.lang.Exception

class TechnicianCardViewModel (private val techApp : App) : ViewModel() {

    private var realm : Realm? = null
    var technician : String? = null

    var _technicianObject : LiveRealmResults<Technician>? = null

    fun openRealm(callback : (result: String? ) -> Unit) {
           Log.v("QUICKSTART", "Opening Realm. The technician is: " + this.technician)

        val config = SyncConfiguration.Builder(techApp.currentUser(), technician!!)
            // TODO: because this application only reads/writes small amounts of data, it's OK to read/write from the UI thread - NOT SURE THIS IS TRUE
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        //this.realm = Realm.getInstance(config)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                Log.v("QUICKSTART", "Realm opened for $technician")
                this@TechnicianCardViewModel.realm = realm

                this@TechnicianCardViewModel._technicianObject = LiveRealmResults(realm.where<Technician>().findAll())

                //For debugging purposes, create a technician object if none exists
                this@TechnicianCardViewModel._technicianObject?.let {
                    if (this@TechnicianCardViewModel.technicianEmpty(it.value) && this@TechnicianCardViewModel.technician != null) {
                        Log.v(
                            "QUICKSTART",
                            "Creating technician object for technician $technician, since one doesn't exist"
                        )
                        realm.executeTransaction { transactionRealm ->
                            transactionRealm.insert(Technician(technician!!, "first", "last"))
                        }
                    } else {
                        Log.v(
                            "QUICKSTART",
                            "Technician exists. Not creating new technician object"
                        )
                    }
                }
                callback.invoke("technician loaded")
            }

            fun onError (e: java.lang.Exception) {
                Log.e("QUICKSTART", "Failed to get realm instance: $e")
                this@TechnicianCardViewModel.realm = null
            }
            // _chatMessages = LiveRealmResults(realm?.where<ChatMessage>()!!.findAll().sort("timestamp"))


            //_chatMessages?.let{
            //    setMessageHistoryText(it.value)
        })
    }

    fun technicianEmpty(techs : List<Technician>? ) : Boolean {
        return techs?.isEmpty() ?: true
    }

    fun getTickets() : RealmList<Ticket> {
        val t : Technician? = getTechnicianObj()
        return t?.tickets ?: RealmList<Ticket>()
    }

    fun getTechnicianObj() : Technician? {
        return this._technicianObject?.value?.first()
    }

    fun closeRealm() {
        this.realm?.close()
    }
 }