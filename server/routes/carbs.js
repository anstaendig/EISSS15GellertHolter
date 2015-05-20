var express = require('express');
var router = express.Router();
var Product = require('../models/product');
var chalk = require('chalk');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.send('/carbs');
});

router.get('/:product', function(req, res, next) {
  console.log(chalk.yellow('Searching for: ') + req.params.product);
  Product.find({bezeichnung: {$regex : ".*" + req.params.product + ".*", $options: 'i'}}, function(err, products) {
    console.log(chalk.green('Search for ' + req.params.product + ' was successfull!'));
    res.json(products);
  });
});

router.post('/', function(req, res, next) {
  console.log(chalk.yellow('Incoming post: \n') + JSON.stringify(req.body, null, 2));
  var newProduct = new Product({
    hersteller: req.body.hersteller,
    art: req.body.art,
    bezeichnung: req.body.bezeichnung,
    '1be': req.body.be,
    '1ke': req.body.ke
  });
  newProduct.save(function(err) {
    if (err) next(err)
    console.log(chalk.green('Successfully saved ' + req.body.bezeichnung + ' to database'));
  });
})

module.exports = router;
