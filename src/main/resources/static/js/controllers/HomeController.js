register('HomeController', function ($scope, MaiceService) {
	
	MaiceService.http('get', '/testrest/', null, function(response) {
		$scope.greeting = response;
	});
	
});
