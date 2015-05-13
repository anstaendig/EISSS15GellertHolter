var mongo = require('mongoskin');
var ObjectID = require('mongodb').ObjectID;

var db = mongo.db('mongodb://localhost:27017/test', {native_parser:true});
db.bind('sick');

// define parts for aggregation pipeline
var a = {'$match': {'$or': [{'krankheiten': 'Stinken'},{'krankheiten': 'Grippe'}]}};
var b = {'$unwind': '$krankheiten'};
var c = {'$project': {'c': {'$concat':
                            [{'$cond':[{'$eq' : ['$krankheiten', 'Stinken' ]},'Stinken','']},
                            {'$cond':[{'$eq' : ['$krankheiten', 'Grippe']},'Grippe','']}]}}};
var d = {'$group': {'c': {'$addToSet': '$c'}, '_id': '$_id'}};
var e = {'$match': {'c': {'$size': 3}}};
var f = {'$project': {'_id': '$_id'}};

db.collection('sick').aggregate([a,b,c,d,e,f], function(err, result) {
  console.log(result);
  var ids = result.map(function(obj) { return ObjectID(obj._id); });
  console.log(ids);
  db.collection('sick').find({'_id': {'$in': ids}}).toArray(function(err, docs) {
    console.log(docs);
  });
});
