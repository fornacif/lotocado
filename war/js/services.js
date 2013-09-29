"use strict";

angular.module("lotocado.services", []).
	service("eventModel", function() {
		this.event = {};
		this.participants = [];
		this.event.name = null;
		this.event.organizerName = null;
		this.event.organizerEmail = null;
		
		this.reset = function() {
			this.event.name = null;
			this.event.organizerName = null;
			this.event.organizerEmail = null;
			this.participants = [];
	    };
	});