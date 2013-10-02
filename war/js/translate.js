"use strict";

angular.module("lotocado.translate", ["pascalprecht.translate"]).
	config(["$translateProvider", function ($translateProvider) {
		$translateProvider.translations("en", {
			CONTACT_US: "Contact us",
			WELCOME_TO: "Welcome to",
			CREATE_EVENT: "Create event"
		});
		$translateProvider.translations("fr", {
			CONTACT_US: "Nous contacter",
			WELCOME_TO: "Bienvenue dans",
			CREATE_EVENT: "Cr&eacute;er un &eacute;v&egrave;nement"
		});
		$translateProvider.preferredLanguage("fr");
	}]).
	controller("TranslateController", ["$translate", "$scope", function ($translate, $scope) {
		$scope.lang = "fr";
		$scope.changeLanguage = function () {
			$translate.uses($scope.lang);
		}
	}]);