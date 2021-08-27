package com.mongodb.ispfieldtechapp.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId
import java.util.*

@RealmClass(embedded = true)
open class Ticket : RealmObject {
    var ticketNum : Int? = null
    var createDate : Date? = null
    var completeDate : Date? = null
    var status : String = "open"
    var description : String = ""
    var technician : Technician? = null

    constructor (number : Int, tech: Technician, tDesc : String = "") {
        ticketNum = number
        createDate = Date()
        technician = tech
        description = tDesc
    }

    constructor () {}
}

open class Technician : RealmObject {
    @PrimaryKey
    var _id : ObjectId? = null
    var technicianId : String // technician email address
    var firstName : String? = null
    var lastName : String? = null
    var phone : String? = null
    var email : String? = null
    var tickets : RealmList<Ticket> = RealmList<Ticket>()

    fun getNumTickets() : Int {
        return tickets.size
    }

    fun createTicket(desc : String) : Ticket {
        val newTicket = Ticket(this.getNumTickets(), this, desc)
        tickets.add(newTicket)

        return newTicket
    }


    constructor(techId : String, first: String?, last: String?) {
        _id = ObjectId()
        technicianId = techId
        firstName = first
        lastName = last

        createTicket("first ticket")
        createTicket("second ticket")
        createTicket("third ticket")
    }

    constructor () {
        _id = ObjectId()
        technicianId = "technicianUnassigned"
    }
}