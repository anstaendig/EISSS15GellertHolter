// load the things we need
var mongoose = require('mongoose');

// required to refernce to ObjectId
var ObjectId = mongoose.Schema.Types.ObjectId;

// define the schema for our parent model
var childSchema = mongoose.Schema({
	name: String,
	age: Number,
	gender: String,
	sports: [String],
  diseases: [String],
  log: [ObjectId],
  parent: ObjectId,
  doc: ObjectId,
	therapy: {
		factor: {
			morning: Number,
			day: Number,
			evening: Number,
			type: { type: String }
		},
		type: { type: String },
		target: Number,
		correction: Number
	}
}, { collection: 'children', versionKey: false });

// create the model for children and expose it to our app
module.exports = mongoose.model('Child', childSchema);
