var express = require('express');
var app = express();
var mongo = require('mongoskin');

var db = mongo.db("mongodb://localhost:27017/carbs", {native_parser:true});
db.bind('carbs');

app.get('/:bezeichnung', function (req, res) {
  var search = req.params.bezeichnung;
  db.collection('carbs').find({bezeichnung: {$regex : ".*" + search + ".*", $options: 'i'}}).toArray(function(err, result) {
    console.log(result);
  });
});

var server = app.listen(3030, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
