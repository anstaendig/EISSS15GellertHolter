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

// TODO URI template /:childId
router.get('/exampleChild', function(req, res, next) {
  console.log(chalk.yellow('Searching for chil with id: ') + chalk.blue('PUT ID HERE') + chalk.yellow('...'));
  Child.findById("557ad78338559bbaf3580f9a", function(err, child) {
    if (err) next(err);
    res.json(child);
    console.log(chalk.green('The following child has been found and sent: '));
    console.log(chalk.blue(JSON.stringify(child, null, 2)));
  })
});

// TODO URI template /:childId
router.put('/exampleChild', function(req, res, next) {
  Parent.findById("556da21a09ed2732f501d55b", function(err, parent) {
    Child.findByIdAndUpdate(parent.child, function(err, child) {
      // TODO: Update profile of child
    })
  })
});

// TODO URI template /:childId
router.get('/exampleChild/log', function(req, res, next) {
  console.log(chalk.yellow('Searching for logbook from child with id: ') + chalk.blue('PUT ID HERE') + chalk.yellow('...'));
  Child.findById("557ad78338559bbaf3580f9a", function(err, child) {
    if (err) next(err);
    res.json(child.log);
    console.log(chalk.green('The following logbook has been found and sent: '));
    console.log(chalk.blue(JSON.stringify(child.log, null, 2)));
  });
});

// TODO URI template /:childId/log
// TODO change to put
router.post('/exampleChild/log', function(req, res, next) {
  console.log(chalk.yellow('Following entry recieved: '));
  console.log(chalk.blue(JSON.stringify(req.body, null, 2)));
  console.log(chalk.yellow('Trying to save entry to database...'));
  Child.findByIdAndUpdate("557ad78338559bbaf3580f9a", {$push: {log: req.body}}, {upsert: true, save: true}, function(err, child) {
    if (err) next(err);
    console.log(chalk.green('Entry has succesfully been added to log of child with id: '));
    console.log(chalk.blue(child._id));
    res.json(child);
  });
});

module.exports = router;
