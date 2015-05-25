var app = angular.module('exampleApp', ['ngRoute', 
                                        'ngCookies',
                                        'exampleApp.services',
                                        'exampleApp.directives',
                                        'beaconCtrl',
                                        'masterDataCtrl',
                                        'ngImgCrop'
                                        ]);
	
app.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', '$compileProvider', '$provide',
		  function($routeProvider, $locationProvider, $httpProvider, $compileProvider, $provide) {
	
				$routeProvider.when('/login', {
					templateUrl: 'partials/login.html',
					controller: LoginController
				});
				
				/** BEACON ROUTE **/
				$routeProvider.when('/beacons', {
					templateUrl: 'partials/beacons/index.html',
					controller: 'BeaconCtrl'
				});
				/** fim BEACON ROUTE **/
				
				/** MASTER DATA ROUTE **/
				$routeProvider.when('/masterdata', {
					templateUrl: 'partials/masterdata/index.html',
					controller: 'MasterDataCtrl'
				});
				/** fim MASTER DATA ROUTE **/
				
			/** CUSTOMER ROUTE **/
				$routeProvider.when('/customers', {
					templateUrl: 'partials/customers.html',
					controller: CustomerController
				});
			/** fim CUSTOMER ROUTE **/
			
			/** ESTABLISHMENT ROUTE **/
				$routeProvider.when('/establishment', {
					templateUrl: 'partials/establishment/index.html',
					controller: EstablishmentController
				});
				
				$routeProvider.when('/establishment/create', {
					templateUrl: 'partials/establishment/create.html',
					controller: EstablishmentCreateController
				});
				
				$routeProvider.when('/establishment/edit/:idEstablishment', {
					templateUrl: 'partials/establishment/edit.html',
					controller: EstablishmentEditController
				});
			/** fim ESTABLISHMENT ROUTE **/
			
				$routeProvider.otherwise({
				});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		}]).run(function($rootScope, $location, $cookieStore, UserService, AccessService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.hasPageAccess = function(page) {
			
			function searchInArray (str, strArray) {
				for(var j=0; j<strArray.length; j++) {
					if(strArray[j].match(str)) return true;
				}
				return false;
			}
			
			if ($rootScope.userAccess === undefined) {
				return false;
			}
			return searchInArray(page, $rootScope.userAccess.pages);
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.userAccess;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
			AccessService.get(function(userAccess) {
				$rootScope.userAccess = userAccess;
			});
		}
		
		$rootScope.initialized = true;
	});

/* CUSTOMER CONTROLLERS */
	function CustomerController($scope, CustomerService) {
		
		$scope.customers = CustomerService.query();
		
		$scope.deleteCustomer = function(customer) {
			customer.$remove(function() {
				$scope.customers = CustomerService.query();
			});
		};
	};
/* FIM CUSTOMER CONTROLLERS */

/* ESTABLISHMENT CONTROLLERS */
	function EstablishmentController($scope, EstablishmentService) {
		
		$scope.establishments = EstablishmentService.query();
	};
	
	function EstablishmentEditController($scope, $routeParams, $location, EstablishmentService) {
	
		$scope.establishment = EstablishmentService.get({idEstablishment: $routeParams.idEstablishment});
		
		$scope.save = function() {
			$scope.establishment.$save(function() {
				$location.path('/establishment');
			});
		};
	};
	
	
	function EstablishmentCreateController($scope, $location, EstablishmentService) {
		
		$scope.establishment = new EstablishmentService();
		
		$scope.save = function() {
			$scope.establishment.$save(function() {
				$location.path('/establishment');
			});
		};
	};
/* FIM ESTABLISHMENT CONTROLLERS */

	function LoginController($scope, $rootScope, $location, $cookieStore, UserService, AccessService) {
		
		$scope.rememberMe = false;
		
		$scope.login = function() {
			UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				UserService.get(function(user) {
					$rootScope.user = user;
					$location.path("/");
				});
				AccessService.get(function(userAccess) {
					$rootScope.userAccess = userAccess;
				});
			});
		};
	};