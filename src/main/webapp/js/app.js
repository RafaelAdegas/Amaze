var app = angular.module('exampleApp', ['ngRoute', 
                                        'ngCookies',
                                        'exampleApp.services',
                                        'beaconCtrl'
                                        ]);
	
app.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', '$compileProvider', '$provide',
		  function($routeProvider, $locationProvider, $httpProvider, $compileProvider, $provide) {
				
				$routeProvider.when('/create', {
					templateUrl: 'partials/create.html',
					controller: CreateController
				});
				
				$routeProvider.when('/edit/:id', {
					templateUrl: 'partials/edit.html',
					controller: EditController
				});
	
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
			/** ACTIVITY DATA ROUTE */
				$routeProvider.when('/activities', {
					templateUrl: 'partials/activities/index.html',
					controller: ActivityController
				});
				
				$routeProvider.when('/activities/edit/:idActivityData', {
					templateUrl: 'partials/activities/edit.html',
					controller: ActivityEditController
				});
				
				$routeProvider.when('/activities/create', {
					templateUrl: 'partials/activities/create.html',
					controller: ActivityCreateController
				});
				
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
					templateUrl: 'partials/index.html',
					controller: IndexController
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
		   
		}]).run(function($rootScope, $location, $cookieStore, UserService) {
		
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
		
		$rootScope.logout = function() {
			delete $rootScope.user;
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
		}
		
		$rootScope.initialized = true;
	});


function IndexController($scope, NewsService) {
	
	$scope.newsEntries = NewsService.query();
	
	$scope.deleteEntry = function(newsEntry) {
		newsEntry.$remove(function() {
			$scope.newsEntries = NewsService.query();
		});
	};
};

function EditController($scope, $routeParams, $location, NewsService) {

	$scope.newsEntry = NewsService.get({id: $routeParams.id});
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};

function CreateController($scope, $location, NewsService) {
	
	$scope.newsEntry = new NewsService();
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};

/* ACTIVITY DATA */
	function ActivityController($scope, ActivityService, BeaconService) {
		
		$scope.activities = ActivityService.query();
		
		$scope.deleteAct = function(activity) {
			activity.$remove(function() {
				$scope.activities = ActivityService.query();
			});
		};
	};
	
	function ActivityEditController($scope, $routeParams, $location, ActivityService) {
		
		$scope.activity = ActivityService.get({idActivityData: $routeParams.idActivityData});
		
		$scope.save = function() {
			$scope.activity.$save(function() {
				$location.path('/activities');
			});
		};
	};

	function ActivityCreateController($scope, $location, ActivityService) {
		
		$scope.activity = new ActivityService();
		
		$scope.save = function() {
			$scope.activity.$save(function() {
				$location.path('/activities');
			});
		};
	};
/* FIM ACTIVITY DATA */

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

	function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
		
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
			});
		};
	};