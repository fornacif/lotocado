"use strict";

angular.module("lotocado", ["lotocado.translate", "lotocado.services", "lotocado.controllers", "ui.select2", "ui.date"]).
	config(function($routeProvider) {
		$routeProvider.
			when("/", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/home", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/creation", {controller:"CreationController", templateUrl:"partials/creation.html"}).
			when("/participants", {controller:"ParticipantsController", templateUrl:"partials/participants.html"}).
			when("/exclusions", {controller:"ExclusionsController", templateUrl:"partials/exclusions.html"}).
			when("/confirmation", {controller:"ConfirmationController", templateUrl:"partials/confirmation.html"}).
			when("/success", {controller:"SuccessController", templateUrl:"partials/success.html"}).
			when("/participant/:encryptedValue", {controller:"ParticipantController", templateUrl:"partials/participant.html"}).
			when("/event/:encryptedValue", {controller:"EventController", templateUrl:"partials/event.html"}).
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
		console.log("Google API loaded");
		var scope = angular.element(document).scope();
		scope.$broadcast("GAPI_LOADED_EVENT");
	}
	
	gapi.client.load("lotocado", "v1", callback, apiRoot);
}

$(document).ready(function(){
	$(document).snowfall({deviceorientation : true, round : true, minSize: 3, maxSize:8,  flakeCount : 100});
});