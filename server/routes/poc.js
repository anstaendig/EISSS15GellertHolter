var express = require('express');
var router = express.Router();

var jsonObject = {
  'key1': 'value1',
  'key2': 'value2',
  'key3': 'value3'
};

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log('/GET')
  res.json(jsonObject);
});

router.post('/', function(req, res, next) {
  //console.log(JSON.parse(req.body));

  console.log(req.body.Name);
  res.json(JSON.stringify(req.body, null, 2));
});

module.exports = router;
