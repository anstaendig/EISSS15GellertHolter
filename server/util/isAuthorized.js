var secret = require('../config/secret').secret;
var jwt = require('jsonwebtoken');
var chalk = require('chalk');

// Funktion zur überprüfungen des übergebenen Token!
var isAuthorized = function(req, res, next) {
  console.log(chalk.yellow('Checking if user is authorized...'));
  var token = req.headers['x-access-token'];
  if (token) {
    jwt.verify(token, secret, function(err, decoded) {
      if (err) {
        return res.status(403).json({
          success: false,
          message: 'Failed to authenticate token.'
        });
      } else {
        req.decoded = decoded;
        console.log(chalk.green(decoded.name + '(' + decoded._id +
          ') has been verified!'));
        next();
      }
    });
  } else {
    return res.status(403).json({
      success: false,
      message: 'No token provided'
    });
  }
};

module.exports = isAuthorized;
