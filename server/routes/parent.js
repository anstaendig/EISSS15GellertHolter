var express = require('express');
var router = express.Router();
var passport = require('../config/passport');
var isLoggedIn = require('../util/isLoggedIn');

/* GET home page. */
router.get('/', isLoggedIn, function(req, res, next) {
  console.log('ERFOOOOLG!');
  console.log(req.user);
  res.status(200).json('DONE');
});

router.post('/login', function(req, res, next) {
  passport.authenticate('login', function(err, user, info) {
    if (err) { return next(err); }
    if (!user) { return res.json('Login failed'); }
    req.logIn(user, function(err) {
      if (err) { return next(err); }
      return res.status(200).json(user);
    });
  })(req, res, next);
});

module.exports = router;
