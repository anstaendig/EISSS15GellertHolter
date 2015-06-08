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
      res.status(404).json({
        succes: false,
        message: 'Authentication failed. User not found!'
      });
    } else if (user) {
      if (user.password != req.body.password) {
        res.status(401).json({
          success: false,
          message: 'Authentication failed. Wrong password!'
        });
      } else {
        res.json({
          success: true,
          message: 'Enjoy your token!',
          token: user.token
        });
      };
    };
  });
});

router.post('/signup', function(req, res, next) {
  console.log(chalk.yellow('Trying to register user with email address ') + chalk.blue(req.body.email) + chalk.yellow('...'));
  Parent.findOne({
    email: req.body.email
  }, function(err, user) {
    if (err) next(err);
    if (!user) {
      var parent = new Parent();
      parent.email = req.body.email;
      parent.password = req.body.password;
      parent.save(function(err, user) {
        user.token = jwt.sign(user, secret);
        user.save(function(err, user1) {
          res.json({
            success: true,
            message: 'Signup successfull',
            token: user1.token
          });
          console.log(chalk.green('User with email address ') + chalk.blue(user1.email) +  chalk.green(' successfully registered with unique token:\n') + chalk.blue(user1.token));
        });
      });
    }
  });
});

router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
