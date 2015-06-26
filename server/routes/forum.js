var express = require('express');
var router = express.Router();
var Thread = require('../models/thread');
var isAuthorized = require('../util/isAuthorized');

// TODO: Delete dummy data and get from database
var threads = [{
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [{
    author: 'author1',
    body: 'body1',
    date: 'date1'
  }, {
    author: 'author3',
    body: 'body2',
    date: 'date3'
  }]
}, {
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [{
    body: 'body',
    date: 'date',
    author: 'author'
  }, {
    body: 'body2',
    date: 'date2',
    author: 'author2'
  }]
}];

var thread = {
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [{
    body: 'body',
    date: 'date',
    author: 'author'
  }, {
    body: 'body',
    date: 'date',
    author: 'author'
  }]
};

// Repsond mit allen Threads im Forum
router.get('/', function(req, res, next) {
  // Respond with all thread
  /*
  Thread.find(function(err, threads, next) {
    if (err) next(err);
    console.log(threads);
    res.json(threads);
  });
  */
  console.log(threads);
  res.json(threads);
});

// Erstelle einen neuen Thread mit den übergebeben Parametern
// TODO: Matchmaking and GCM to other parents
router.post('/', function(req, res, next) {
  var newThread = new Thread({
    author: req.user._id,
    body: req.body.body,
    topics: req.body.topics,
    comments: req.body.comments
  });
  newThread.save(function(err) {
    if (err) next(err);
    console.log('Saved successfully');
    res.json(newThread);
    newThread.save();
  });

  Parent.findById("556da21a09ed2732f501d55b", function(err, parent) {
    Child.findById(parent.children[0], function(err, child) {

    });
  });

});

// Respond mit Thread mit der eindeutigen ID :id
// TODO: search database for thread and send it back
router.get('/:id', function(req, res, next) {

});

// Dem thread mit der eindeutigen ID :id wirh ein Kommentar aus den übergebenen Parametern hinzugefügt
// TODO Comment on a thread
router.put('/:id', function(req, res, next) {

});

module.exports = router;
