var express = require('express');
var router = express.Router();
var Child = require('../models/child');
var Parent = require('../models/parent');
var chalk = require('chalk');

//var isAuthorized = require('../util/isAuthorized');

// TODO URI Templates

router.get('/', function(req, res, next) {
  // TODO take parent ID from req via token
  Parent.findById("556da21a09ed2732f501d55b", function(err, parent) {
    console.log(parent.children);
    Child.find({'_id': { $in: parent.children}}, function(err, children) {
      console.log(children);
      res.json(children);
    });
  });
});

// TODO URI template or /me (verificatin via token)
router.get('/exampleChild', function(req, res, next) {
  Child.findById("557ad78338559bbaf3580f9a", function(err, child) {
    res.json(child);
  })
});

// TODO URI template or /me (verificatin via token)
router.put('/exampleChild', function(req, res, next) {
  Parent.findById("556da21a09ed2732f501d55b", function(err, parent) {
    Child.findByIdAndUpdate(parent.child, function(err, child) {
      // TODO: Update profile of child
    })
  })
});

// TODO URI template or /me (verificatin via token)
router.get('/exampleChild/log', function(req, res, next) {
  Child.findById("557ad78338559bbaf3580f9a", function(err, child) {
    res.json(child.log);
  });
});

// TODO URI template or /me (verificatin via token)
router.post('/exampleChild/log', function(req, res, next) {
  console.log(req.body);
  Child.findByIdAndUpdate("557ad78338559bbaf3580f9a", {$push: {log: req.body}}, {upsert: true, save: true}, function(err, child) {
    if (err) next(err);
    console.log(chalk.yellow('Entry has succesfully been added to log!'));
    res.json(child);
  });
  /*
  var newEntry = new LogEntry({
    bloodsugar: req.body.bloodsugar,
    be: req.body.be,
    beFactor: req.body.beFactor,
    correctionValue: req.body.correctionValue,
    insulin: req.body.insulin,
    notes: req.body.notes
  }).save(function(err, entry) {
    Child.findByIdAndUpdate("5578556338559bbaf3580f99", {$push: {log: entry._id}}, {upsert: true, save: true}, function(err, child) {
      if (err) next(err);
      console.log(chalk.yellow('Entry has succesfully been added to log!'));
      res.json(child);
    });
  });
  */
});

module.exports = router;
