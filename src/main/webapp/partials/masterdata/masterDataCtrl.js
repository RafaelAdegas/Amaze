var masterDataModule = angular.module('masterDataCtrl', ['datatables','ngImgCrop']);
/* MASTER DATA CONTROLLERS */
masterDataModule.controller('MasterDataCtrl', function (DTOptionsBuilder, 
											DTColumnDefBuilder,
											UsersService, 
											ActivityService,
											fileUpload,
											$scope,
											$http,
											$location) {
	
	/* initialize entity's */
	$scope.users = UsersService.query();
	/* end */
	
	
	
    /** TABS **/
    $scope.tab = 1;
    $scope.setTab = function(tabId){
    	this.tab = tabId;
    };
    $scope.isSet = function(tabId) {
    	return this.tab === tabId;
    }
});