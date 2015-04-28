var express =  require('express');
var app = express();
var carbs = require('./carbs.js');

app.get('/', function(req, res) {
  res.send('Hello World!');
  carbs.search('Nutella', function(err, search) {
    if (err) return err;
    console.log(search);
  });
});

var server = app.listen(3000, function() {
  var host = '127.0.0.1';
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
