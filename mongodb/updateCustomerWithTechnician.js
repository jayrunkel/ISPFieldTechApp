var db = db.getSiblingDB("ISPFieldTech")

var customerCol = db.getCollection("Customer")
var technicianCol = db.getCollection("Technician")

var pipeline = [{$unwind: {
  path: "$tickets"
}}, {$lookup: {
  from: 'Customer',
  localField: 'tickets.customer',
  foreignField: '_id',
  as: 'customer'
}}, {$match: {
  customer : {$not : {$size : 0}}
}}, {$addFields: {
  customer: {$first : '$customer'}
}}]


var customerArray = technicianCol.aggregate(pipeline).toArray()



customerArray.forEach(techDoc => {
	customerCol.updateOne({_id : techDoc.customer._id}, {
		$unset : {technicianId : 1, activeTechnicians : 1}
	})

	customerCol.updateOne({_id : techDoc.customer._id}, {

		$addToSet : {
			technicianId : techDoc.technicianId,
			activeTechnicians : techDoc._id,
			activeTickets : techDoc.tickets._id
		}
	})
}
)									 
