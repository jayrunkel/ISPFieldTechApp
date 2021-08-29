var db = db.getSiblingDB("ISPFieldTech")

var customerCol = db.getCollection("Customer")
var technicianCol = db.getCollection("Technician")

db.customerCol.updateMany({technicianId : {$exists : false}},
													{$set : {technicianId : "no active tickets"}})
