var express = require('express');
var router = express.Router();

var jsonObject = {
  'key1': 'value1',
  'key2': 'value2',
  'key3': 'value3'
};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.json(jsonObject);
});

module.exports = router;
