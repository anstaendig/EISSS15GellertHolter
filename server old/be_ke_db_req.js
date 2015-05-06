//Load the request module
var request = require('request');
var search = process.argv[2];
var chalk = require('chalk');
var fs = require('fs');

//Lets configure and request
request({
  url: 'http://www.diabetes-kids.de/index.php?Itemid=414&option=com_fabrik&view=list&listid=2&calculations=0&resetfilters=0&clearordering=0&clearfilters=0', //URL to hit
  method: 'POST',
  //Lets post the following key/values as form
  form: {
    'fabrik___filter[list_2_com_fabrik_2][value][2]': search,
    'fabrik___filter[list_2_com_fabrik_2][condition][2]': 'contains',
    'fabrik___filter[list_2_com_fabrik_2][join][2]': 'AND',
    'fabrik___filter[list_2_com_fabrik_2][key][2]': '`betabelle`.`bezeichnung`',
    'fabrik___filter[list_2_com_fabrik_2][search_type][2]': 'normal',
    'fabrik___filter[list_2_com_fabrik_2][match][2]': 0,
    'fabrik___filter[list_2_com_fabrik_2][full_words_only][2]': 0,
    'fabrik___filter[list_2_com_fabrik_2][eval][2]': 0,
    'fabrik___filter[list_2_com_fabrik_2][grouped_to_previous][2]': 0,
    'fabrik___filter[list_2_com_fabrik_2][hidden][2]': 0,
    'fabrik___filter[list_2_com_fabrik_2][elementid][2]': 11,
    limit2: 100,
    format: 'JSON'
  }
}, function(err, response, body) {
  if(!err && response.statusCode == 200) {
    var data = JSON.parse(body);
    data[0].forEach(function(product) {
      var dataObject = {
        hersteller: product.betabelle___hersteller,
        art: product.betabelle___art,
        bezeichnung: product.betabelle___bezeichnung,
        '1be': product.betabelle___1be,
        '1ke': product.betabelle___1ke
      };
      fs.appendFile('example_database.json', JSON.stringify(dataObject, null, 2), function(err){
        if (err) throw err;
        console.log('Product ' + dataObject.bezeichnung + ' was appended to file!');
      });
      // console.log(chalk.underline(product.betabelle___bezeichnung) + ': ' + chalk.yellow(product.betabelle___beproeinheit));
    });
  } else {
    console.log(err);
  };
});
