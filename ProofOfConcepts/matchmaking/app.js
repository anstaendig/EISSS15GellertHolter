/* var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/test');

var db = mongoose.connection;

db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {
  console.log('Succesfully connected to database!')
}); */
var mongo = require('mongoskin');

var db = mongo.db('mongodb://localhost:27017/test', {native_parser:true});
db.bind('sick');

// define parts for aggregation pipeline
var a = {'$match': {'$or' : [ {'krankheiten' : 'Stinken'},{'krankheiten' : 'Grippe'}]}};

var b = {'$project': 'krankheiten'};

var c = {'$unwind': '$krankheiten'};

var d = {'$project': {'c': {'$concat':
                            [{'$cond':[ {'$eq' : ['$krankheiten', 'Stinken' ]},'Stinken','']},
                            {'$cond':[ {'$eq' : ['$krankheiten', 'Grippe']},'Grippe','']}]}}};

var e = {'$group': {'c': {'$addToSet': '$c'}, '_id': '$_id'}};
var f = {'$unwind': '$c'};
var g = {'$group': {'c': {'$sum': 1}, '_id': '$_id'}};
var h = {'$match': {'c': {'$gte': 3}}};
var i = {'$project': {'_id': '$_id'}};

db.collection('sick').aggregate([a,c,d,e,f,g,h,i], function(err, result) {
  console.log(result.length);
  for (var i = 0; i < result.length; i++) {
    db.collection('sick').findOne({_id:result[i]._id}, function(err, matched) {
      console.log(matched);
    })
  };
});
