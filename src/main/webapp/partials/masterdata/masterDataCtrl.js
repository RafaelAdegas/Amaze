var masterDataModule = angular.module('masterDataCtrl', ['datatables','fxpicklist']);
/* MASTER DATA CONTROLLERS */
masterDataModule.controller('MasterDataCtrl', function (DTOptionsBuilder, 
											DTColumnDefBuilder,
											UsersService,
											$scope,
											$http,
											$location) {
	
	/* initialize entity's */
	$scope.users = UsersService.query();
	/* end */
	
    /** TABS */
    $scope.tab = 1;
    $scope.setTab = function(tabId){
    	this.tab = tabId;
    };
    $scope.isSet = function(tabId) {
    	return this.tab === tabId;
    }
    /** end TABS */
    
    
    
    /** Pick List Options */
    $scope.toptions=new Array();

	$scope.toptions.push({
		name: "Página: Home (Inicial)",
		value: "HOME_MENU_HEADER",
		index: 1
	},{
		name: "Página: Beacons",
		value: "BEACONS_MENU_HEADER",
		index: 2
	}, {
		name: "Página: Clientes",
		value: "CUSTOMERS_MENU_HEADER",
		index: 3
	}, {
		name: "Página: Estabelecimento",
		value: "ESTABLISHMENT_MENU_HEADER",
		index: 4
	}, {
		name: "Página: Configurações",
		value: "MASTERDATA_MENU_HEADER",
		index: 5
	}
	);
	$scope.tselected=[$scope.toptions[1]];
	/** end pick list options */
	
	
	/** modal users */
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    //var activity = ActivityService.get({idActivityData: $scope.activity.idActivityData});
    
    //vm.beacons = activity.beaconCollection;
    
    $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers');
    $scope.dtColumnDefs = [
        DTColumnDefBuilder.newColumnDef(0),
        DTColumnDefBuilder.newColumnDef(1),
        DTColumnDefBuilder.newColumnDef(2)
    ];
    /** end modal users */
});