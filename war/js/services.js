"use strict";

angular.module("lotocado.services", []).
	service("eventModel", function() {
		this.event = {};
		this.participants = [];
		this.reset = function() {
			this.event = {};
			this.participants = [];
		}
	}).
	service("eventService", ['eventModel', function() {
		this.createDrawingLots = function (eventModel, callback) {
			eventModel.participants.forEach(function(participant) {
				participant.hashKey = participant.$$hashKey;
			});
			
			gapi.client.lotocado.randomMatcher.createDrawingLots({
				"event" : eventModel.event,
				"participants" : eventModel.participants
			}).execute(callback);
        };
	}]);