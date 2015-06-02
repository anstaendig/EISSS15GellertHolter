var express = require('express');
var router = express.Router();
var isAuthorized = require('../util/isAuthorized');

/* GET home page. */
router.get('/', isAuthorized, function(req, res, next) {
  console.log('User who requested is authorized');
  res.json(req.user);
});

router.post('/login', function(req, res, next) {
  passport.authenticate('login', function(err, user, info) {
    if (err) { return next(err); }
    if (!user) { return res.json('Login failed'); }
    req.logIn(user, function(err) {
      if (err) { return next(err); }
      console.log('User successfully logged in!');
      res.json(user);
    });
  })(req, res, next);
})

module.exports = router;
