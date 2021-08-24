package com.mongodb.ispfieldtechapp.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class MetaData : RealmObject {
    @PrimaryKey
    var _id : ObjectId? = null
    @Required
    var technicianId = "All"
    @Required
    var ticketStatuses : RealmList<String> = RealmList<String>()

    fun getTicketStatuses () : Array<String> {
        return ticketStatuses.toTypedArray()
    }

    constructor() {
        _id = ObjectId()
    }
}
