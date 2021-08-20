package com.mongodb.ispfieldtechapp.data.model

import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.realmsync.LiveRealmResults
import io.realm.Realm

class TicketViewModel : ViewModel {

    var technician : String? = null
    var ticketNumber: Int? = null

    var ticketObject : Ticket? = null

    constructor () {
        ticketObject = null
    }

    constructor (ticket : Ticket?, techName : String?, tNum: Int?) {
        ticketObject = ticket
        technician = techName
        ticketNumber = tNum
    }

}