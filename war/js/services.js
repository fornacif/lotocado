'use strict';

angular.module('lotocado.services', []).
	service('eventModel', function() {
		this.name = null;
		this.organizerName = null;
		this.organizerEmail = null;
		this.reset = function() {
			this.name = null;
			this.organizerName = null;
			this.organizerEmail = null;
	    };
	});