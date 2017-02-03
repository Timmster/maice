angular.module('baseapp', [ 'ngRoute', 'ngResource']).factory('MaiceService', function MaiceServiceFunction($rootScope, $http, $location) {
	return {
		
		/**
		 * Initializes the MaiceService.
		 */
		initialize : function() {
			$rootScope.error = false;
			this.http('get', 'messages', null, function(data) {
				$rootScope.messages = data; 
				$rootScope.msgKeys = [];
				for (var key in data) {
					$rootScope.msgKeys.push(key);
				}
				$rootScope.msg = $rootScope.messages["de"]; 
			});
			this.authenticate();
		},
		
		/**
		 * Perform a redirect to the given path.
		 */
		redirect : function(path){
			$location.path(path);
		},
		
		promt : function(object){
			alert(JSON.stringify(object));
		}, 
		
		changeLanguage : function(lang) {
			$rootScope.msg = $rootScope.messages[lang]; 
		},
		
		isAuthenticated : function() {
			return $rootScope.authenticated;
		},
		
		checkSecurity : function() {
			if (!$rootScope.authenticated) {
				$location.path("/");
			}
		},

		http : function(method, path, data, callback){
			$rootScope.infoMessages = [];
			$http({
				  method: method,
				  url: path,
				  headers: data
				}).then(function successCallback(response) {
					$rootScope.infoMessages = response.data.infos;
					callback(response.data.responseObject);
				  }, function errorCallback(response) {
					$rootScope.error = true;
					$rootScope.infoMessages = response.data.infos;
				  });
		},

		authenticate : function(credentials) {
			var headers = credentials ? {
				authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
			} : {};
			$rootScope.authenticated = false;
			$http.get('user', {headers: headers}).success(function(response) {
				$rootScope.authenticated = true;
				$rootScope.error = false;
				$location.path("/home");
			}).error(function(response) {
				$rootScope.authenticated = false;
				$rootScope.error = credentials;
				$location.path("/");
			});
		},

		register : function(credentials, path) {
			this.http('post', 'register', credentials, function(data) {
			});
		},

		logout : function() {
			$rootScope.authenticated = false;
			this.http('post', 'logout', {}, function(data) {
				$location.path("/");
			});
		}
	};
})

.config(function($routeProvider, $httpProvider) {
	$routeProvider.when('/home', {
		templateUrl : 'html/home.html',
		controller : 'HomeController'
	}).when('/', {
		templateUrl : 'html/public.html',
		controller : 'PublicController'
	}).otherwise('/');
	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}).

run(function(MaiceService) {
	MaiceService.initialize();
});

function register(name, controller){
	angular.module('baseapp').controller(name, controller);
}