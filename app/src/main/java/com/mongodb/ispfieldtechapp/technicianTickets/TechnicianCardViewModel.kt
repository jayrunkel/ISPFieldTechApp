package com.mongodb.ispfieldtechapp.technicianTickets

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.model.Customer
import com.mongodb.ispfieldtechapp.data.model.Technician
import com.mongodb.ispfieldtechapp.data.model.Ticket
import com.mongodb.ispfieldtechapp.data.realmsync.LiveRealmResults
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import io.realm.mongodb.App
import io.realm.mongodb.sync.SyncConfiguration

class TechnicianCardViewModel : ViewModel() {

    var realm : Realm? = null
    var technician : String? = null

    var _technicianObject : LiveRealmResults<Technician>? = null
    var _customersObject : LiveRealmResults<Customer>? = null


    fun openRealm(techApp: App, callback : (result: String? ) -> Unit) {
           Log.v("QUICKSTART", "Opening Realm. The technician is: " + this.technician)

        val config = SyncConfiguration.Builder(techApp.currentUser(), technician!!)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        //this.realm = Realm.getInstance(config)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                Log.v("QUICKSTART", "Realm opened for $technician")
                this@TechnicianCardViewModel.realm = realm

                this@TechnicianCardViewModel._customersObject = LiveRealmResults(realm.where<Customer>().equalTo("technicianId", technician).findAll())
                this@TechnicianCardViewModel._technicianObject = LiveRealmResults(realm.where<Technician>().equalTo("technicianId", technician).findAll())


                Log.v("QUICKSTART", "Logging customers...")
                logCustomers()

                //For debugging purposes, create a technician object if none exists
                this@TechnicianCardViewModel._technicianObject?.let {
                    if (this@TechnicianCardViewModel.technicianEmpty(it.value) && this@TechnicianCardViewModel.technician != null) {
                        Log.v(
                            "QUICKSTART",
                            "Creating technician object for technician $technician, since one doesn't exist"
                        )
                        val t = Technician(technician!!, "first", "last")
                        val c1 = Customer(technician!!)
                        val c2 = Customer(technician!!)
                        val c3 = Customer(technician!!)
                        c1.fullname = "Customer 1"
                        c2.fullname = "Customer 2"
                        c3.fullname = "Customer 3"
                        c1.addTechnician(t)
                        c2.addTechnician(t)
                        c3.addTechnician(t)
                        t.createTicket(c1, "Customer 1 ticket")
                        t.createTicket(c2, "Customer 2 ticket")
                        t.createTicket(c3, "Customer 3 ticket")

                        realm.executeTransaction { transactionRealm ->
                            //transactionRealm.insert(c1)
                            //transactionRealm.insert(c2)
                            //transactionRealm.insert(c3)
                            transactionRealm.insert(t)
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

    fun logCustomers() {
        this@TechnicianCardViewModel._customersObject?.value?.forEach { c ->
            Log.v("QUICKSTART", "Loaded ${c.fullname}")
        }
    }


    fun technicianEmpty(techs : List<Technician>? ) : Boolean {
        return techs?.isEmpty() ?: true
    }

    fun getTickets() : RealmList<Ticket> {
        val t : Technician? = getTechnicianObj()
        return t?.tickets ?: RealmList<Ticket>()
    }

    fun getTicket(tNum : Int) : Ticket? {
        val tickets : RealmList<Ticket> = getTickets()

        for(i in tickets.indices) {
            if (tickets[i]?.ticketNum == tNum)
                return tickets[i]
        }
        return null
    }

    fun getTechnicianObj() : Technician? {
        return this._technicianObject?.value?.first()
    }

    fun closeRealm() {
        this.realm?.close()
    }
 }