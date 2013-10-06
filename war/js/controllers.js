"use strict";

angular.module("lotocado.controllers", []).
	controller("HomeController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.create = function() {
			console.log("create");
			eventModel.reset();
			$location.path( "/creation");
		}		
	}]).
	controller("CreationController", ["$scope", "$location", "$window", "eventModel", function($scope, $location, $window, eventModel) {
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
	}]).
	controller("ExclusionsController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
	}]).
	controller("ConfirmationController", ["$scope", "$location", "eventModel", "eventService", function($scope, $location, eventModel, eventService) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		
		$scope.confirm = function() {
			console.log(JSON.stringify(eventModel));
			eventService.createDrawingLots(eventModel, function(response) {
				if (response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
				} else {
					console.log(response.result.items);
					$scope.$apply($location.path("/success"));
					eventModel.reset();
				}
			});	
		};
	}]).
	controller("SuccessController", ["$scope", "$location", "eventModel", function($scope, $location, eventModel) {
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
	}]);