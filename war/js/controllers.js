'use strict';

angular.module('lotocado.controllers', []).
	controller('HomeController', ['$scope', '$location', '$window', function($scope, $location, $window) {				

	}]).
	controller('EventCreationController', ['$scope', '$location', '$window', 'eventModel', function($scope, $location, $window, eventModel) {
		$scope.event = {};
		
		$scope.create = function() {
			eventModel.event = $scope.event;
			
			var participants = eventModel.participants;
			var organizerParticipant = {};
			organizerParticipant.name = eventModel.event.organizerName;
			organizerParticipant.email = eventModel.event.organizerEmail;
			participants.push(organizerParticipant);
			
			$location.path( "/edit-participants");
		};
	}]).
	controller('ParticipantsEditionController', ['$scope', '$location', 'eventModel', 'eventService', function($scope, $location, eventModel, eventService) {
		$scope.eventName = eventModel.event.name;
		$scope.participants = eventModel.participants;
		
		$scope.addParticipant = function() {
			$scope.participants.push({});
		};
		
		$scope.removeParticipant = function(participant) {
			var index = $scope.participants.indexOf(participant);
			$scope.participants.splice(index, 1);
		};
	  
		$scope.saveEvent = function() {
			console.log(JSON.stringify(eventModel));
			eventService.createDrawingLots(eventModel, function(response) {
				if (response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
				} else {
					console.log(response.result.items);
					$scope.$apply($location.path("/confirm-creation"));
				}
			});	
		};
	}]).
	controller('ExclusionsEditionController', ['$scope', '$location', 'eventModel', 'eventService', function($scope, $location, eventModel, eventService) {
		$scope.eventName = eventModel.event.name;
		$scope.participants = eventModel.participants;
	
		$scope.saveEvent = function() {
			console.log(JSON.stringify(eventModel));
			eventService.createDrawingLots(eventModel, function(response) {
				if (response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
				} else {
					console.log(response.result.items);
					$scope.$apply($location.path("/confirm-creation"));
				}
			});	
		};
	}]).
	controller('CreationConfirmationController', ['$scope', '$location', 'eventModel', function($scope, $location, eventModel) {
		$scope.eventName = eventModel.event.name;
	}]);