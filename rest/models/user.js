var restful = require('node-restful');
var mongoose = restful.mongoose;

//schema
var productSchema = new mongoose.Schema({
	username: String,
    password: String,
    firstName: String,
	lastName:  String,
	occupation: String,
	ifinuniversity: String,
	university: String,
	address: String,
	city: String,
	state: String,
	zip: String,
	email: String,
	phone: String,
	postProject: String,
	joinedProject:String,
});

//Return model

module.exports = restful.model('user', productSchema);