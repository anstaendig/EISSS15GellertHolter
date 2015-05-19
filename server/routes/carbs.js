var express = require('express');
var router = express.Router();
var Product = require('../models/product');
var chalk = require('chalk');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.send('Here we can search for the carbs!');
});

router.get('/:product', function(req, res, next) {
  console.log('Searching for: ' + req.params.product);
  Product.find({bezeichnung: {$regex : ".*" + req.params.product + ".*", $options: 'i'}}, function(err, products) {
    console.log(chalk.green('Search for ' + req.params.product + ' was successfull!'));
    res.json(products);
  });
});

module.exports = router;
