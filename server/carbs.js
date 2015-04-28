//Load the request module
var request = require('request');

function search(product, callback) {

  var options = {
    url: 'http://www.diabetes-kids.de/index.php?Itemid=414&option=com_fabrik&view=list&listid=2&calculations=0&resetfilters=0&clearordering=0&clearfilters=0', //URL to hit
    method: 'POST',
    //Lets post the following key/values as form
    form: {
      'fabrik___filter[list_2_com_fabrik_2][value][0]': '',
      'fabrik___filter[list_2_com_fabrik_2][condition][0]': '=',
      'fabrik___filter[list_2_com_fabrik_2][join][0]': 'AND',
      'fabrik___filter[list_2_com_fabrik_2][key][0]': '`betabelle`.`hersteller`',
      'fabrik___filter[list_2_com_fabrik_2][search_type][0]': 'normal',
      'fabrik___filter[list_2_com_fabrik_2][match][0]': 1,
      'fabrik___filter[list_2_com_fabrik_2][full_words_only][0]': 0,
      'fabrik___filter[list_2_com_fabrik_2][eval][0]': 0,
      'fabrik___filter[list_2_com_fabrik_2][grouped_to_previous][0]': 0,
      'fabrik___filter[list_2_com_fabrik_2][hidden][0]': 0,
      'fabrik___filter[list_2_com_fabrik_2][elementid][0]': 9,
      'fabrik___filter[list_2_com_fabrik_2][value][1]': '',
      'fabrik___filter[list_2_com_fabrik_2][condition][1]': '=',
      'fabrik___filter[list_2_com_fabrik_2][join][1]': 'AND',
      'fabrik___filter[list_2_com_fabrik_2][key][1]': '`betabelle`.`art`',
      'fabrik___filter[list_2_com_fabrik_2][search_type][1]': 'normal',
      'fabrik___filter[list_2_com_fabrik_2][match][1]': 1,
      'fabrik___filter[list_2_com_fabrik_2][full_words_only][1]': 0,
      'fabrik___filter[list_2_com_fabrik_2][eval][1]': 0,
      'fabrik___filter[list_2_com_fabrik_2][grouped_to_previous][1]': 0,
      'fabrik___filter[list_2_com_fabrik_2][hidden][1]': 0,
      'fabrik___filter[list_2_com_fabrik_2][elementid][1]': 10,
      'fabrik___filter[list_2_com_fabrik_2][value][2]': product,
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
      limitstart2: 0,
      option: 'com_fabrik',
      orderdir: '',
      orderby: '',
      view: 'list',
      listid: 2,
      listref: '2_com_fabrik_2',
      Itemid: 414,
      fabrik_referrer: '/index.php?Itemid=414&option=com_fabrik&view=list&listid=2&calculations=0&resetfilters=0&clearordering=0&clearfilters=0',
      '55da66b09d51f9211916555e3b87fac9': 1,
      format: 'JSON',
      packageId: 0,
      task: 'list.filter',
      fabrik_listplugin_name: '',
      fabrik_listplugin_renderOrder: '',
      fabrik_listplugin_options: '',
      incfilters: 1
    }
  };

  function handleResponse(err, response, body) {
    if (!err && response.statusCode == 200) {
      var result = JSON.parse(body);
      return callback(null, result[0]);
    }
    return callback(err);
  };

  request(options, handleResponse);
};

exports.search = search;
