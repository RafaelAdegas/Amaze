var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

services.factory('NewsService', function($resource) {
	
	return $resource('rest/news/:id', {id: '@id'});
});

services.factory('BeaconService', function($resource) {
	
	return $resource('rest/beacons/:idBeacon', {idBeacon: '@idBeacon'});
});

services.factory('CustomerService', function($resource) {
	
	return $resource('rest/customers/:id', {id: '@id'});
});

services.factory('EstablishmentService', function($resource) {
	
	return $resource('rest/establishments/:idEstablishment', {idEstablishment: '@idEstablishment'});
});

services.factory('ActivityService', function($resource) {
	
	return $resource('rest/activitydata/:idActivityData', {idActivityData: '@idActivityData'});
});

services.factory('ShareDataService', function() {
    var myList = [];

    var addList = function(newObj) {
        myList.push(newObj);
    }

    var getList = function(){
        return myList;
    }

    return {
        addList: addList,
        getList: getList
    };
});