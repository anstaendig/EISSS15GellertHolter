var express = require('express');
var router = express.Router();
var Product = require('../models/product');
var chalk = require('chalk');

// Suche nach Nahrungsmitteln in der Datenbank
// Falls kein search query, suche nach allen Nahrungsmitteln
// Falls ein search query vorhanden, suche nach diesem via Regular Expression und
// case insensitivity
// Gibt ergebnis als json array
router.get('/', function(req, res, next) {
  if (typeof req.query.search !== "undefined") {
    console.log(chalk.yellow('Searching for: ') + chalk.blue(req.query.search));
    Product.find({
      description: {
        $regex: ".*" + req.query.search + ".*",
        $options: 'i'
      }
    }, function(err, products) {
      console.log(chalk.green('Found: ') + chalk.blue(JSON.stringify(
        products, null, 2)));
      res.json(products);
    });
  } else {
    console.log(chalk.yellow('Requesting all products from database... '));
    Product.find({}, function(err, products) {
      console.log(chalk.green('Found: ') + chalk.blue(JSON.stringify(
        products, null, 2)));
      res.json(products);
    });
  }
});

// API-Endpoint zur Erstellung eines neuen Nahrungsmittels
// Zum Testen implementiert worden
router.post('/', function(req, res, next) {
  console.log(chalk.yellow('Incoming post: \n') + chalk.blue(JSON.stringify(
    req.body, null, 2)));
  var newProduct = new Product({
    brand: req.body.brand,
    type: req.body.type,
    description: req.body.description,
    be: req.body.be,
    ke: req.body.ke
  });
  newProduct.save(function(err) {
    if (err) next(err);
    console.log(chalk.green('Successfully saved ' + chalk.blue(JSON.stringify(
      req.body, null, 2)) + ' to database'));
  });
});

module.exports = router;
