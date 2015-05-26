var passport = require('passport'),
    LocalStrategy = require('passport-local').Strategy;

var Parent = require('../models/parent');

passport.serializeUser(function(user, done) {
  done(null, user.id);
});

passport.deserializeUser(function(id, done) {
  Parent.findById(id, function(err, user) {
    done(err, user);
  });
});

passport.use('login', new LocalStrategy({
  usernameField: 'email',
  passwordField: 'password'
},
function(email, password, done) {
  Parent.findOne({ email: email }, function (err, user) {
    if (err) { return done(err); }
    if (!user) { return done(null, false); }
    if (user.password != password) { return done(null, false); }
    return done(null, user);
    });
  }
));

module.exports = passport;
