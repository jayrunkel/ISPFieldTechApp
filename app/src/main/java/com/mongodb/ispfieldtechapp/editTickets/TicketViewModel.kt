package com.mongodb.ispfieldtechapp.editTickets

import androidx.lifecycle.ViewModel
import com.mongodb.ispfieldtechapp.data.model.Ticket
import io.realm.Realm

class TicketViewModel : ViewModel {

    var technician : String? = null
    var ticketNumber: Int? = null

    private var realm : Realm? = null
    var ticketObject : Ticket? = null

    constructor () {
        ticketObject = null
    }

    constructor (ticket : Ticket?, techName : String?, tNum: Int?, r: Realm?) {
        ticketObject = ticket
        technician = techName
        ticketNumber = tNum
        realm = r
    }

    fun updateTicket(newStatus : String, newComment: String) {
        realm?.executeTransaction {
            ticketObject?.status = newStatus
            ticketObject?.description = newComment
        }
    }

}