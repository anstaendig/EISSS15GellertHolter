var express = require('express');
var router = express.Router();
var chalk = require('chalk');

var Parent = require('../models/parent');

router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

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

router.post('/login', function(req, res, next) {
  Parent.findOne({ email: req.body.email }, function(err, user) {
    if (err) next(err);
    if (!user) {
      res.json('E-Mail not existing');
    } else if (req.body.password != user.password) {
      res.json('Wrong password');
    } else {
      res.json(user);
    };
  });
})
module.exports = router;
