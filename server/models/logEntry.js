// load the things we need
var mongoose = require('mongoose');

// required to refernce to ObjectId
var ObjectId = mongoose.Schema.Types.ObjectId;

// define the schema for our parent model
var logEntrySchema = mongoose.Schema({
	date: { type: Date, default: Date.now },
	bloodsugar: Number,
	be: Number,
	beFactor: Number,
  correctionValue: Number,
  insulin: Number,
  mood: String,
  notes: String
}, { versionKey: false });

// create the model for parents and expose it to our app
module.exports = mongoose.model('LogEntry', logEntrySchema);
