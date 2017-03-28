var restful = require('node-restful');
var mongoose = restful.mongoose;

//schema
var productSchema = new mongoose.Schema({
	owner: String,
	title: String,
	description: String,
	size: Number,
	beginDate: String,
	endDate: String,
	catagories: [String],
	award: Number,
	contactMethod: [String],
	members:[String],
});

//Return model

module.exports = restful.model('project', productSchema);

