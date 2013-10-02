"use strict";

angular.module("lotocado.translate", ["pascalprecht.translate"]).
	config(["$translateProvider", function ($translateProvider) {
		$translateProvider.translations('en', {
			CONTACT_US: 'Contact us',
			WELCOME_TO: 'Welcome to',
			CREATE_EVENT: 'Create event'
		});
		$translateProvider.translations('fr', {
			CONTACT_US: 'Nous contacter',
			WELCOME_TO: 'Bienvenue dans',
			CREATE_EVENT: 'Cr&eacute;er un &eacute;v&egrave;nement'
		});
		$translateProvider.preferredLanguage('en');
	}]).
	controller("TranslateController", ["$translate", "$window", "$scope", function ($translate, $window, $scope) {
		$scope.select2Options = {
	        allowClear:false
	    };
		
		var lang = $window.navigator.userLanguage || $window.navigator.language;
		$scope.lang = lang;
		$translate.uses(lang);
		
		$scope.changeLanguage = function () {
			$translate.uses($scope.lang);
		}
	}]);