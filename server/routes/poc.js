var express = require('express');
var router = express.Router();

var testJsonObject = {
  'key1': 'value1',
  'key2': 'value2',
  'key3': 'value3',
  'key4': 'value4'
};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.json(testJsonObject);
  console.log('Sent:\n' + JSON.stringify(testJsonObject, null, 2));
});

router.post('/', function(req, res, next) {
  console.log(req.body);
  res.send('Post on /poc was successfull');
});

module.exports = router;
