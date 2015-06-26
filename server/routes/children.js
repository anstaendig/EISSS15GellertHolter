var express = require('express');
var router = express.Router();
var Child = require('../models/child');
var Parent = require('../models/parent');
var chalk = require('chalk');
//var isAuthorized = require('../util/isAuthorized');

router.get('/', function(req, res, next) {
  // TODO take parent ID from req via token
  Parent.findById("556da21a09ed2732f501d55b", function(err, parent) {
    console.log(parent.children);
    Child.find({
      '_id': {
        $in: parent.children
      }
    }, function(err, children) {
      console.log(children);
      res.json(children);
    });
  });
});

router.post('/', function(req, res, next) {
  //TODO Create new child profile
})

router.get('/:child', function(req, res, next) {
  console.log(chalk.yellow('Searching for chil with id: ') + chalk.blue(req
    .params.child) + chalk.yellow('...'));
  Child.findById(req.params.child, function(err, child) {
    if (err) next(err);
    res.json(child);
    console.log(chalk.green(
      'The following child has been found and sent: '));
    console.log(chalk.blue(JSON.stringify(child, null, 2)));
  })
});

router.put('/:child', function(req, res, next) {
  Parent.findById(req.params.child, function(err, parent) {
    Child.findByIdAndUpdate(parent.child, function(err, child) {
      // TODO: Update profile of child
    })
  })
});

router.get('/:child/log', function(req, res, next) {
  console.log(chalk.yellow('Searching for logbook from child with id: ') +
    chalk.blue('PUT ID HERE') + chalk.yellow('...'));
  Child.findById(req.params.child, function(err, child) {
    if (err) next(err);
    res.json(child.log);
    console.log(chalk.green(
      'The following logbook has been found and sent: '));
    console.log(chalk.blue(JSON.stringify(child.log, null, 2)));
  });
});

router.put('/:child/log', function(req, res, next) {
  // TODO: Send notification to parent via gcm
  console.log(chalk.yellow('Following entry recieved: '));
  console.log(chalk.blue(JSON.stringify(req.body, null, 2)));
  console.log(chalk.yellow('Trying to save entry to database...'));
  Child.findByIdAndUpdate(req.params.child, {
    $push: {
      log: req.body
    }
  }, {
    upsert: true,
    save: true
  }, function(err, child) {
    if (err) next(err);
    console.log(chalk.green(
      'Entry has succesfully been added to log of child with id: '));
    console.log(chalk.blue(child._id));
    res.json(child);
  });
});

module.exports = router;
