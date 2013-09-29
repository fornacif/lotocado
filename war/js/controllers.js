'use strict';

angular.module('lotocado.controllers', []).
	controller('CreationController', ['$scope', '$location', '$window', 'eventModel', function($scope, $location, $window, eventModel) {				
		$scope.create = function() {
			eventModel.event.name = $scope.eventName;
			eventModel.event.organizerName = $scope.organizerName;
			eventModel.event.organizerEmail = $scope.organizerEmail;
			$location.path( "/edition");
		};
	}]).
	controller('EditionController', ['$scope', '$location', 'eventModel', function($scope, $location, eventModel) {
		$scope.eventName = eventModel.event.name;
		$scope.participants = eventModel.participants;
	
		var organizerParticipant = {};
		organizerParticipant.name = eventModel.event.organizerName;
		organizerParticipant.email = eventModel.event.organizerEmail;
		$scope.participants.push(organizerParticipant);
		
		$scope.addParticipant = function() {
			$scope.participants.push({});
		};
		
		$scope.removeParticipant = function(participant) {
			var index = $scope.participants.indexOf(participant);
			$scope.participants.splice(index, 1);
		};
	  
		$scope.edit = function() {
			$scope.participants.forEach(function(participant) {
				participant.hashKey = participant.$$hashKey;
				delete participant.$$hashKey;
			});
			
			console.log(JSON.stringify(eventModel));

			gapi.client.lotocado.randomMatcher.createDrawingLots({
				"event" : eventModel.event,
				"participants" : eventModel.participants
			}).execute(function(response) {
				if (response.error != null) {
					var message = JSON.parse(response.error.message);
					console.log(message.code);
				} else {
					console.log(response.result.items);
					$scope.$apply($location.path( "/confirmation"));
				}
				
			});
		};
	}]).
	controller('ConfirmationController', ['$scope', '$location', 'eventModel', function($scope, $location, eventModel) {
		$scope.eventName = eventModel.event.name;
	}]);