"use strict";

angular.module("lotocado.services", []).
	service("eventModel", function() {
		this.event = {};
		this.participants = [{}, {}, {}];
		this.reset = function() {
			this.event = {};
			this.participants = [{}, {}, {}];
		}
		this.isValid = function (event, participants) {
			return event.name != null && event.organizerName != null && event.organizerEmail != null && event.date != null && participants.length >= 3;
		};
	}).
	service("creationService", ["eventModel", function() {
		this.createDrawingLots = function (eventModel, callback) {
			eventModel.participants.forEach(function(participant) {
				participant.hashKey = participant.$$hashKey;
			});
			
			gapi.client.lotocado.randomMatcher.createDrawingLots({
				"event" : eventModel.event,
				"participants" : eventModel.participants
			}).execute(callback);
        };
	}]).
	service("participantService", function() {
		this.getParticipant = function (encryptedValue, callback) {			
			gapi.client.lotocado.participantManager.getParticipant(
				{"encryptedValue" : encryptedValue}
			).execute(callback);
        };
	}).
	service("eventService", function() {
		this.getEvent = function (encryptedValue, callback) {			
			gapi.client.lotocado.eventManager.getEvent(
				{"encryptedValue" : encryptedValue}
			).execute(callback);
        };
        this.resendEmail = function (participant, callback) {			
			gapi.client.lotocado.eventManager.resendEmail(
				{"participant" : participant}
			).execute(callback);
        };
	});