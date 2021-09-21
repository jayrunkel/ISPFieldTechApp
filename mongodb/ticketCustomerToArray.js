var db = db.getSiblingDB("ISPFieldTech")

var customerCol = db.getCollection("Customer")
var technicianCol = db.getCollection("Technician")

db.technicianCol.updateMany({}
