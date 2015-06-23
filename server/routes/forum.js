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
    date: 'date1' },
  {
    author: 'author3',
   	body: 'body2',
   	date: 'date3' }]
},
{
  '_id': '_id',
  author: 'author',
  date: 'date',
  body: 'body',
  topics: ['/topic1', '/topic'],
  comments: [{
    body: 'body',
    date: 'date',
    author: 'author' },
  {
    body: 'body2',
   	date: 'date2',
   	author: 'author2' }]
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

// Create new forum thread
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
  })

  
});

// TODO Comment on a thread
router.put('/:id', function(req, res, next) {

});

module.exports = router;
