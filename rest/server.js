var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');


mongoose.connect('mongodb://xin:123321@ds135800.mlab.com:35800/testonly');

var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use('/api', require('./routes/api'));



// app.get('/',function(req, res){
// 	res.send('working');
// });

app.listen(3000);
console.log('API is running on port 3000')
