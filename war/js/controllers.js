"use strict";

angular.module("lotocado.controllers", []).
	controller("InitController", ["$rootScope", "$scope", function($rootScope, $scope) {
		$scope.$on("GAPI_LOADED_EVENT", function() {
			$rootScope.gapi = true;
			
		});
	}]).
	controller("HomeController", ["$rootScope", "$scope", "$location", "eventModel", function($rootScope, $scope, $location, eventModel) {
		$scope.create = function() {
			eventModel.reset();
			$location.path( "/creation");
		};
	}]).
	controller("CreationController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.event = eventModel.event;
		$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			yearRange: "2013:-0",
			regional: "fr"
		};
		
		$scope.addOrganizer = function() {			
			var participants = eventModel.participants;
			if (participants.length == 0) {
				var organizerParticipant = {};
				organizerParticipant.name = eventModel.event.organizerName;
				organizerParticipant.email = eventModel.event.organizerEmail;
				participants.push(organizerParticipant);
			}
			$location.path( "/participants");
		};
	}]).
	controller("ParticipantsController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		
		$scope.addParticipant = function() {
			$scope.participants.push({});
		};
		
		$scope.removeParticipant = function(participant) {
			var index = $scope.participants.indexOf(participant);
			$scope.participants.splice(index, 1);
		};
		$scope.isEventInvalid = function () {
			return !eventModel.isValid($scope.event, $scope.participants);
		}
	}]).
	controller("ExclusionsController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		$scope.isEventInvalid = function () {
			return !eventModel.isValid($scope.event, $scope.participants);
		}
	}]).
	controller("ConfirmationController", ["$scope", "$location", "eventModel", "eventService", function($scope, $location, eventModel, eventService) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		$scope.isEventInvalid = function () {
			return !eventModel.isValid($scope.event, $scope.participants);
		}
		
		$scope.confirm = function() {
			console.log(JSON.stringify(eventModel));
			eventService.createDrawingLots(eventModel, function(response) {
				if (response && response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
					$scope.$apply($scope.error = message);
				} else {
					$scope.$apply($location.path("/success"));
				}
			});	
		};
	}]).
	controller("SuccessController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		if (!eventModel.isValid(eventModel.event, eventModel.participants)) {
			eventModel.reset();
			$location.path("/home");
		}
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		eventModel.reset();
	}]).	
	controller("ParticipantController", ["$rootScope", "$scope", "$location", "$routeParams", "participantService", function($rootScope, $scope, $location, $routeParams, participantService) {
		$scope.getParticipant = function() {
			participantService.getParticipant($routeParams.encryptedValue, function(response) {
				if (response && response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
				} else {
					$scope.$apply($scope.result = response);
				}
			});	
		}
		
		if ($rootScope.gapi) {
			$scope.getParticipant();
		} else {
			$scope.$on("GAPI_LOADED_EVENT", function() {
				$scope.getParticipant();
			});
		}
		
		
	}]).
	controller("EventController", ["$scope", "$location", "$routeParams", function($scope, $location, $routeParams) {
		
	}]);