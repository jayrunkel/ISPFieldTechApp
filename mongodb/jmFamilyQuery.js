var ids = db.AccessoryPackage_080521.find({“audit._audit_updated_by” : { $ne : “vpc_14testing_1080"},
  “_id” : { $ne : “seed record”}})
  .sort({_id:-1})
  //.limit(50)
  .limit(1)
  .map(function(doc) { return doc._id; });


db.AccessoryPackage_080521.updateMany({_id: {$in: ids}}, {$set: {“audit._audit_updated_by”: “vpc_14testing_1800", “audit._audit_updated_datetime”: new ISODate()}}})
