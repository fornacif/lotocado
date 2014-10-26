"use strict";

angular.module("lotocado.controllers", []).
	controller("InitController", ["$rootScope", "$scope", "$location", "$translate", function($rootScope, $scope, $location, $translate) {
		$scope.$on("GAPI_LOADED_EVENT", function() {
			$rootScope.gapi = true;
		});
		
		$scope.isActive = function(path) {
			return $location.path() == path;
		}
		
		$scope.lang = "fr";
		$scope.changeLanguage = function () {
			$translate.uses($scope.lang);
		}
	}]).
	controller("HomeController", ["$rootScope", "$scope", "$location", "eventModel", "Analytics", function($rootScope, $scope, $location, eventModel, Analytics) {
		Analytics.trackPage("/home");
		
		$scope.create = function() {
			eventModel.reset();
			$location.path( "/creation");
		};
	}]).
	controller("CreationController", ["$scope", "$location", "eventModel", "Analytics", function($scope, $location, eventModel, Analytics) {
		Analytics.trackPage($location.path());
		
		$scope.event = eventModel.event;
		$scope.dateOptions = {
			regional: "fr"
		};
		
		$scope.addOrganizer = function() {			
			var participants = eventModel.participants;
			if (participants[0] == null || participants[0].name == null) {
				var organizerParticipant = {};
				organizerParticipant.name = eventModel.event.organizerName;
				organizerParticipant.email = eventModel.event.organizerEmail;
				participants[0] = organizerParticipant;
			}
			$location.path( "/participants");
		};
	}]).
	controller("ParticipantsController", ["$scope", "$location", "eventModel", "Analytics", function($scope, $location, eventModel, Analytics) {
		Analytics.trackPage($location.path());
		
		if (eventModel.event.date) {
			eventModel.event.date.setHours(eventModel.event.date.getHours() + 1);
		}
		
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
	controller("ExclusionsController", ["$scope", "$location", "eventModel", "Analytics", function($scope, $location, eventModel, Analytics) {
		Analytics.trackPage($location.path());
		
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		$scope.isEventInvalid = function () {
			return !eventModel.isValid($scope.event, $scope.participants);
		}
	}]).
	controller("VerificationController", ["$scope", "$location", "eventModel", "creationService", "Analytics", function($scope, $location, eventModel, creationService, Analytics) {
		Analytics.trackPage($location.path());
		
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		$scope.isEventInvalid = function () {
			return !eventModel.isValid($scope.event, $scope.participants);
		}
		
		$scope.save = function() {
			$scope.submitted = true;
			$scope.error = null;
			creationService.createDrawingLots(eventModel, function(response) {
				$scope.submitted = false;
				if (response && response.error != null) {
					var message = JSON.parse(response.error.message);
					$scope.$apply($scope.error = message);
				} else {
					eventModel.event.organizerLink = response.organizerLink;
					$scope.$apply($location.path("/success"));
				}
			});	
		};
	}]).
	controller("SuccessController", ["$scope", "$location", "eventModel", "Analytics", function($scope, $location, eventModel, Analytics) {
		Analytics.trackPage($location.path());
		
		if (!eventModel.isValid(eventModel.event, eventModel.participants)) {
			eventModel.reset();
			$location.path("/home");
		}
		
		$scope.event = eventModel.event;
		$scope.participants = eventModel.participants;
		eventModel.reset();
	}]).	
	controller("ParticipantController", ["$rootScope", "$scope", "$location", "$routeParams", "participantService", "$translate", "Analytics", function($rootScope, $scope, $location, $routeParams, participantService, $translate, Analytics) {
		Analytics.trackPage("/participant");
		
		$scope.submitted = false;
		
		$scope.getParticipant = function() {
			$scope.submitted = true;
			$scope.loading = true;
			participantService.getParticipant($routeParams.encryptedValue, function(response) {
				$scope.$apply($scope.loading = false);
				if (response && response.error != null) {
					$scope.$apply($scope.error = true);
				} else {
					$scope.$apply($scope.participant = response);
				}
			});	
		}
		
		$scope.showResult = function() {
			if ($rootScope.gapi) {
				$scope.getParticipant();
			} else {
				$scope.$on("GAPI_LOADED_EVENT", function() {
					$scope.getParticipant();
				});
			}
		}
		
		$scope.otherParticipantTooltip = function(otherParticipant) {
			return '<span style="font-size: 20px;">' + $translate("PARTICIPANT_OTHER_PARTICIPANTS_EXCUSIONS") + ' : ' + otherParticipant.excludedNames.toString() + '</span>';
		};
	}]).
	controller("EventController", ["$rootScope", "$scope", "$location", "$routeParams", "eventService", "Analytics", function($rootScope, $scope, $location, $routeParams, eventService, Analytics) {	
		Analytics.trackPage("/event");

		$scope.loading = true;
		$scope.getEvent = function() {
			eventService.getEvent($routeParams.encryptedValue, function(response) {
				$scope.$apply($scope.loading = false);
				if (response && response.error != null) {
					$scope.$apply($scope.error = true);
				} else {
					response.date = response.date.substring(0,10);
					$scope.$apply($scope.event = response);
				}
			});	
		}
		
		$scope.resendEmail = function(participant) {
			$scope.loading = true;
			$scope.resendEmailError = false;
			
			eventService.resendEmail(participant, function(response) {
				$scope.$apply($scope.loading = false);
				if (response && response.error != null) {
					$scope.$apply($scope.resendEmailError = true);
				}
			});	
		}
		
		if ($rootScope.gapi) {
			$scope.getEvent();
		} else {
			$scope.$on("GAPI_LOADED_EVENT", function() {
				$scope.getEvent();
			});
		}
		
	}]).
	controller("AboutController", ["$scope", "$location", "Analytics", function($scope, $location, Analytics) {
		Analytics.trackPage($location.path());
	}]);