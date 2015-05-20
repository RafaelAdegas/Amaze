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

services.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, filename, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('filename', filename);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);

services.factory('BeaconService', function($resource) {
	
	return $resource('rest/beacons/:idBeacon', {idBeacon: '@idBeacon'});
});

services.factory('UsersService', function($resource) {
	
	return $resource('rest/users/:id', {id: '@id'});
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