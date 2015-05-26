// load the things we need
var mongoose = require('mongoose');

// define the schema for our product model
var productSchema = mongoose.Schema({
	brand: String,
	type: String,
	description: String,
	be: String,
  ke: String
}, { versionKey: false });

// create the model for products and expose it to our app
module.exports = mongoose.model('Product', productSchema);
