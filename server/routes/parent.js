var express = require('express');
var router = express.Router();
//var isAuthorized = require('../util/isAuthorized');

// Respond mit allen Kindern, die Eltern :parent angelegt haben.
router.get('/:parent/children', function(req, res, next) {
  // TODO: Find all children for parent and send them back
  console.log('User who requested is authorized');
  res.json(req.user);
});

module.exports = router;
