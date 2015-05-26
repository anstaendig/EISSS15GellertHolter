// load the things we need
var mongoose = require('mongoose');

// required to refernce to ObjectId
var ObjectId = mongoose.Schema.Types.ObjectId;

// define the schema for our product model
var threadSchema = mongoose.Schema({
	author: ObjectId,
	date: { type: Date, default: Date.now },
	body: String,
	topics: [String],
	comments: [{
		body: String,
		date: { type: Date, default: Date.now },
		author: ObjectId }]
}, { versionKey: false });

// create the model for threads and expose it to our app
module.exports = mongoose.model('Thread', threadSchema);
