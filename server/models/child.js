// load the things we need
var mongoose = require('mongoose');

// required to refernce to ObjectId
var ObjectId = mongoose.Schema.Types.ObjectId;

// define the schema for our parent model
var childSchema = mongoose.Schema({
	name: String,
	age: Integer,
	gender: String,
	sports: [String],
  diseases: [String],
  log: [ObjectId],
  parent: ObjectId,
  doc: ObjectId,
	therapy: {
		factor: {
			morning: Double,
			day: Double,
			evening: Double,
			type: String
		},
		type: String,
		target: Integer,
		correction: Integer
	}
}, { versionKey: false });

// create the model for children and expose it to our app
module.exports = mongoose.model('Child', childSchema);
