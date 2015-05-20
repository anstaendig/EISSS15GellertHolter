// load the things we need
var mongoose = require('mongoose');

// required to refernce to ObjectId
var ObjectId = mongoose.Schema.Types.ObjectId;

// define the schema for our parent model
var parentSchema = mongoose.Schema({
	email: String,
	password: String,
	name: String,
	child: ObjectId
}, { versionKey: false });

// create the model for products and expose it to our app
module.exports = mongoose.model('Parent', parentSchema);
