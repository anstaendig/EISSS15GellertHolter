//Load the request module
var request = require('request');

//Lets configure and request
request({
  url: '', //URL to hit
  method: 'POST'
  }
}, function(err, response, body) {
  if(!err && response.statusCode == 200) {
    var data = JSON.parse(body);
  } else {
    console.log(err);
  };
});
