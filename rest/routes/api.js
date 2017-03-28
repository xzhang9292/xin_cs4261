//dependencies
var express = require('express');
var router = express.Router();

//models
var Project = require('../models/project');
var User = require('../models/user')

//Routes
Project.methods(['get','put','post','delete'])
Project.register(router,'/project')

User.methods(['get','put','post','delete'])
User.register(router,'/user')

module.exports = router;

// router.get('/products', function(req,res){
// 	res.send('api is working');
// })

