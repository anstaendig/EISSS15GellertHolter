var express = require('express');
var router = express.Router();

var Thread = require('../models/thread');

/* GET home page. */
router.get('/', function(req, res, next) {
  // Respond with all thread
  Thread.find(function(err, threads, next) {
    if (err) next(err);
    console.log(threads);
    res.json(threads);
  });
});

router.post('/', function(req, res, next) {
  var newThread = new Thread({
    author: req.body.author,
    body: req.body.body,
    topics: req.body.topics
  });
  newThread.save(function(err) {
    if (err) next(err);
    console.log('Saved successfully');
    res.json(newThread);
  })
});

module.exports = router;
