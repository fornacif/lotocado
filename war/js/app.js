"use strict";

angular.module("lotocado", ["ngRoute", "ngAnimate", "angular-google-analytics", "lotocado.translate", "lotocado.services", "lotocado.controllers", "ui.directives", "ui.bootstrap"]).
	config(function(AnalyticsProvider, $routeProvider) {
		$routeProvider.
			when("/", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/about", {controller:"AboutController", templateUrl:"partials/about.html"}).
			when("/home", {controller:"HomeController", templateUrl:"partials/home.html"}).
			when("/creation", {controller:"CreationController", templateUrl:"partials/creation.html"}).
			when("/participants", {controller:"ParticipantsController", templateUrl:"partials/participants.html"}).
			when("/exclusions", {controller:"ExclusionsController", templateUrl:"partials/exclusions.html"}).
			when("/verification", {controller:"VerificationController", templateUrl:"partials/verification.html"}).
			when("/success", {controller:"SuccessController", templateUrl:"partials/success.html"}).
			when("/participant/:encryptedValue", {controller:"ParticipantController", templateUrl:"partials/participant.html"}).
			when("/event/:encryptedValue", {controller:"EventController", templateUrl:"partials/event.html"}).
			otherwise({redirectTo:"/"});

			AnalyticsProvider.setAccount('UA-45603516-1');
			AnalyticsProvider.trackPages(false);
	});

function init() {
	var apiRoot = null;
	
	if (window.location.host.indexOf("localhost") == 0) {
		apiRoot = "http://" + window.location.host + "/_ah/api";
	} else {
		apiRoot = "https://lotocado.appspot.com/_ah/api";
	}
	
	var callback = function() {
//		console.log("Bootstraping AngularJS...")
//		angular.bootstrap(document, ["lotocado"]);
		console.log("Google API loaded");
		var scope = angular.element(document).scope();
		scope.$broadcast("GAPI_LOADED_EVENT");
	}
	
	gapi.client.load("lotocado", "v2", callback, apiRoot);
}