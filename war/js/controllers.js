'use strict';

angular.module('lotocado.controllers', []).
	controller('CreationController', ['$scope', '$location', '$window', 'eventModel', function($scope, $location, $window, eventModel) {				
		$scope.create = function() {
			eventModel.name = $scope.eventName;
			eventModel.organizerName = $scope.organizerName;
			eventModel.organizerEmail = $scope.organizerEmail;
			console.log($scope.organizerEmail);
			$location.path( "/edition");
		};
	}]).
	controller('EditionController', ['$scope', '$location', 'eventModel', function($scope, $location, eventModel) {
		$scope.eventName = eventModel.name;
		$scope.participants = [];
		
		var organizerParticipant = {};
		organizerParticipant.name = eventModel.organizerName;
		organizerParticipant.email = eventModel.organizerEmail;
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
			});
			
			console.log(JSON.stringify(eventModel));
			console.log(JSON.stringify($scope.participants));

			gapi.client.lotocado.randomMatcher.createDrawingLots({
				"event" : eventModel,
				"participants" : $scope.participants
			}).execute(function(response) {
				if (response.error != null) {
					console.log(response.error.message);
				} else {
					console.log(response.result.items);
					$scope.$apply($location.path( "/confirmation"));
				}
				
			});
		};
	}]).
	controller('ConfirmationController', ['$scope', '$location', 'eventModel', function($scope, $location, eventModel) {
		$scope.eventName = eventModel.name;
	}]);