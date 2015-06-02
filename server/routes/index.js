var express = require('express');
var router = express.Router();
var chalk = require('chalk');
var jwt    = require('jsonwebtoken');
var app = require('../app');
var secret = require('../config/secret').secret;
var isAuthorized = require('../util/isAuthorized');

var Parent = require('../models/parent');

// Route to authenticate a parent (POST http://localhost:3000/api/authenticate)
router.post('/authenticate', function(req, res, next) {
  Parent.findOne({
    email: req.body.email
  }, function(err, user) {
    if (err) next(err);
    if (!user) {
      res.json({
        succes: false,
        message: 'Authentication failed. User not found!'
      });
    } else if (user) {
      if (user.password != req.body.password) {
        res.json({
          success: false,
          message: 'Authentication failed. Wrong password!'
        });
      } else {
        var token = jwt.sign(user, secret);
        console.log(token);
        res.json({
          success: true,
          message: 'Enjoy your token!',
          token: token
        });
      };
    };
  });
});


router.use(isAuthorized);

router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});
/*
router.post('/signup', function(req, res, next) {
  Parent.findOne({ email: req.body.email }, function(err, user) {
    if (err) next(err);
    if (user) {
      res.json('E-Mail already exists');
    } else {
      var newParent = new Parent({
        email: req.body.email,
        password: req.body.password,
        name: req.body.name
      });
      newParent.save(function(err) {
        if (err) next(err);
        console.log(chalk.green('Successfully saved ' + req.body.email + ' to database'));
      });
    };
  });
});
*/

module.exports = router;
