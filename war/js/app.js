"use strict";

angular.module("lotocado", ["lotocado.translate", "lotocado.services", "lotocado.controllers", "ui.select2", "ui.date"]).
	config(function($routeProvider) {
		$routeProvider.
			when("/", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/home", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/create-event", {controller:"EventCreationController", templateUrl:"partials/create-event.html"}).
			when("/edit-participants", {controller:"ParticipantsEditionController", templateUrl:"partials/edit-participants.html"}).
			when("/edit-exclusions", {controller:"ExclusionsEditionController", templateUrl:"partials/edit-exclusions.html"}).
			when("/confirm-creation", {controller:"CreationConfirmationController", templateUrl:"partials/confirm-creation.html"}).
			otherwise({redirectTo:"/"});
	});

function init() {
	var apiRoot = null;
	
	if (window.location.host.indexOf("localhost") == 0) {
		apiRoot = "http://" + window.location.host + "/_ah/api";
	} else {
		apiRoot = "https://" + window.location.host + "/_ah/api";
	}
	
	var callback = function() {
//		console.log("Bootstraping AngularJS...")
//		angular.bootstrap(document, ["lotocado"]);
		console.log("Google API loaded")
	}
	
	gapi.client.load("lotocado", "v1", callback, apiRoot);
}