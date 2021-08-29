package com.mongodb.ispfieldtechapp.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class Customer : RealmObject {

    @PrimaryKey var _id : ObjectId
    var accountNumber : Int? = null
    var fullname : String? = null
    var occupation : String? = null
    var billingAddress : String? = null
    var primaryPhone : String? = null
    var state : String? = null
    var zipcode : String? = null
    var notes : String? = null
    var birthday : Date? = null
    var ssn: String? = null
    var email : String? = null
    var serviceType : String? = null
    var technicianId : String // TODO: Should be list of technician ids working on this customers tickets
    var activeTechnicians : RealmList<Technician>? = null
 //   var activeTickets : RealmList<Ticket>? = null

    constructor(techId : String) {
        _id = ObjectId()
        technicianId = techId
    }

    constructor() {
        _id = ObjectId()
        technicianId = ""
    }

    fun addTechnician(t : Technician) {
        if (activeTechnicians == null) activeTechnicians = RealmList<Technician>()
        activeTechnicians?.add(t)
    }
}