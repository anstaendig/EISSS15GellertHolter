var secret = require('../config/secret').secret;
var jwt = require('jsonwebtoken');
var chalk = require('chalk');

var isAuthorized = function(req, res, next) {
  console.log(chalk.yellow('Checking if user is authorized...'));
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
  if (token) {
    jwt.verify(token, secret, function(err, decoded) {
      if (err) {
        return res.json({
          success: false,
          message: 'Failed to authenticate token.'
        });
      } else {
        req.decoded = decoded;
        console.log(decoded);
        console.log(chalk.green('Authorization has been verified!'));
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
