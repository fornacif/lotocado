'use strict';

angular.module('lotocado', ['lotocado.services', 'lotocado.controllers', 'ui.select2']).
	config(function($routeProvider) {
		$routeProvider.
			when('/', {controller:'CreationController', templateUrl:'partials/creation.html'}).
			when('/edition', {controller:'EditionController', templateUrl:'partials/edition.html'}).
			when('/confirmation', {controller:'ConfirmationController', templateUrl:'partials/confirmation.html'}).
			otherwise({redirectTo:'/'});
	});

function init() {
	var apiRoot = null;
	
	if (window.location.host.indexOf("localhost") == 0) {
		apiRoot = 'http://' + window.location.host + '/_ah/api';
	} else {
		apiRoot = 'https://' + window.location.host + '/_ah/api';
	}
	
	var callback = function() {
		console.log("Bootstraping AngularJS...")
		angular.bootstrap(document, ["lotocado"]);
	}
	
	gapi.client.load('lotocado', 'v1', callback, apiRoot);
}