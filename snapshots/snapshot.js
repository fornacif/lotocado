var args = require('system').args;
var page = require('webpage').create();  
page.open(args[1], function (status) {
    if (status !== 'success') {
        console.log('Unable to access network');
    } else {
        var p = page.evaluate(function () {
            return "<!doctype html><html ng-app=\"lotocado\">" + document.getElementsByTagName('html')[0].innerHTML + "</html>";
        });
        console.log(p);
    }
    phantom.exit();
});