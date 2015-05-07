var product = process.argv[2];
var request = require('request');

var counter = 0;

var poc = function(){
     if(counter < 5) {
          request('http://localhost:3030/' + product, function(err, res, body) {
            if(!err && res.statusCode === 200) {
              var data = JSON.parse(body);
              console.log(data[0].bezeichnung);
            };
          });
          counter++;
          console.log(counter);
     } else {
          clearInterval(poc);
          process.exit(1);
     }
};

setInterval(poc, 1200);
