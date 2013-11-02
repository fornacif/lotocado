describe('Testing Lotocado', function() {
	var $scope;
	var eventModelService;
	var location;
	
	beforeEach(module('lotocado.services'));
	beforeEach(module('lotocado.controllers'));

	beforeEach(inject(function($rootScope, $controller, $location, eventModel) {
		$scope = $rootScope.$new();
		eventModelService = eventModel;
		location = $location;

		$controller('HomeController', {
			$scope : $scope,
			eventModel : eventModel,
			$location : $location
		});
		
		$controller('CreationController', {
			$scope : $scope,
			eventModel : eventModel,
			$location : $location
		});
		
		$controller('ParticipantsController', {
			$scope : $scope,
			eventModel : eventModel,
			$location : $location
		});

	}));

	it('HomeController', function() {
		expect(eventModelService.event).toBeDefined();
		expect(eventModelService.participants).toBeDefined();
		
		eventModelService.event.name = "test";
		$scope.create();
		
		expect(location.path()).toBe("/creation");
		expect(eventModelService.event.name).toBeUndefined();
	});
	
	it('CreationController', function() {
		eventModelService.event.name = "test";
		eventModelService.event.date = new Date();
		eventModelService.event.organizerName = "test";
		eventModelService.event.organizerEmail = "test@test.fr";
		$scope.addOrganizer();
		
		expect(eventModelService.participants.length).toBe(1);
		expect(eventModelService.participants[0].name).toBe("test");
		expect(eventModelService.participants[0].email).toBe("test@test.fr");
		expect(location.path()).toBe("/participants");
		
		$scope.addOrganizer();
		expect(eventModelService.participants.length).toBe(1);
	});
	
	it('ParticipantsController', function() {
		expect(eventModelService.participants.length).toBe(0);
		$scope.addParticipant();
		expect(eventModelService.participants.length).toBe(1);
		$scope.removeParticipant();
		expect(eventModelService.participants.length).toBe(0);
	});

});