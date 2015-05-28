var express = require('express');
var router = express.Router();

var Thread = require('../models/thread');

var isLoggedIn = require('../util/isLoggedIn');

var threads = [{
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [
    {body: 'body',
     date: 'date',
     author: 'author' },
     {
   	 body: 'body',
   	 date: 'date',
   	 author: 'author' }]
}];

var thread = {
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [
    {body: 'body',
     date: 'date',
     author: 'author' },
     {
   	 body: 'body',
   	 date: 'date',
   	 author: 'author' }]
};

//router.use(isLoggedIn);

/* GET home page. */
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

router.get('/test', function(req, res, next) {
  // Respond with all thread
  /*
  Thread.findById(req.params.id, function(err, thread, next) {
    if (err) next(err);
    console.log(thread);
    res.json();
  });
  */
  res.json(thread);
});

router.post('/', function(req, res, next) {
  var newThread = new Thread({
    author: req.user._id,
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
