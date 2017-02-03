angular.module('baseapp').controller('MaiceController', MaiceControllerFunction);
		
function MaiceControllerFunction($scope, $rootScope, MaiceService) {
	
	$scope.credentials = {username:"timm.schwemann@googlemail.com", password:"test"};
	$scope.registration = false;
	
	$scope.login = function() {
		if (!$scope.registration){
			MaiceService.authenticate($scope.credentials);			
		}else{
			MaiceService.register($scope.credentials);	
		}
	};

	$scope.logout = function() {
		MaiceService.logout();
	}

	$scope.isAuthenticated = function() {
		return MaiceService.isAuthenticated();
	}
	
	$scope.changeLanguage = function(lang){
		MaiceService.changeLanguage(lang);
	}
	
};
