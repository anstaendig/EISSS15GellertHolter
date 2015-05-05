var express =  require('express');
var carbs = require('./carbs.js');
var app = express();

var jsonObject = {
  'name': 'jsonObject',
  'type': 'JSON',
  'value3': 5
};

app.get('/', function(req, res) {
  console.log('Incoming request on ' + req.url);
  res.send('Hello World!');
  carbs.search('Nutella', function(err, search) {
    if (err) return err;
    console.log(search);
  });
});

app.get('/poc/client-server', function(req, res) {
  console.log('Incoming request on ' + req.url);
  res.json(jsonObject);
});

var server = app.listen(3000, function() {
  var host = 'localhost';
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
});
