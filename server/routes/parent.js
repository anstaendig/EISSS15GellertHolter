var express = require('express');
var router = express.Router();
var isAuthorized = require('../util/isAuthorized');

router.get('/:parent/children', isAuthorized, function(req, res, next) {
  console.log('User who requested is authorized');
  res.json(req.user);
});

module.exports = router;
