var mymodal = angular.module('beaconCtrl', ['datatables']); /**''*/
/* BEACON CONTROLLERS */
mymodal.controller('BeaconCtrl', function (DTOptionsBuilder, 
											DTColumnDefBuilder,
											BeaconService, 
											ActivityService,
											$scope,
											$location) {
	
/*
    $scope.imageCropResult = null;
    $scope.showImageCropper = true;
    $scope.imageUri = null;*/

	/* initialize entity's */
		$scope.newBeacon = new BeaconService();
		$scope.newBeacon.activityData = new ActivityService();
		$scope.beacons = BeaconService.query();
	/* end */
		
	/* initialize default value's for variables */
		$scope.selectedBeacon = null;
		$scope.showSuccess = false;
		$scope.showDeleteSuccess = false;
	/* end */
		
	/* delete beacon */
		$scope.deleteBeacon = function(beacon) {
			beacon.$remove(function() {
				$scope.beacons = BeaconService.query();
				$scope.setTab(1);
				$scope.showDeleteSuccess = true;
			});
		};
	/* end */

    /* function selectBeacon with parameter */
	    $scope.selectBeacon = function(beacon) {
	    	$scope.showSuccess = false;
	    	$scope.selectedBeacon = beacon;
	    	$scope.viewMode = true;
	    	$scope.editMode = false;
	    };
	/* end */
    
    /** Buttons functions in Details tab **/
	$scope.edit = function() {
		$scope.viewMode = false;
		$scope.editMode = true;
	};
	
    /** Buttons functions in First tab **/
	$scope.editFromSearch = function() {
		$scope.setTab(2);
		$scope.viewMode = false;
		$scope.editMode = true;
	};
	$scope.selectFromSearch = function() {
		$scope.setTab(2);
		$scope.editMode = false;
	}
	
	$scope.searchBeacons = function() {
		$scope.beacons = BeaconService.query();
	}
	
	$scope.save = function(newBeacon) {
		newBeacon.$save(function() {
			$scope.showSuccess = true;
			$scope.viewMode = true;
			$scope.editMode = false;
			$scope.selectedBeacon = newBeacon;
			$scope.setTab(2);
			$scope.newBeacon = new BeaconService();
		});
	};
	
	$scope.createBeacon = function() {
		$scope.setTab(3);
	}
	
	$scope.cancel = function() {
		$scope.newBeacon = new BeaconService();
		$scope.setTab(1);
	}
	
    $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers');
    $scope.dtColumnDefs = [
        DTColumnDefBuilder.newColumnDef(0),
        DTColumnDefBuilder.newColumnDef(1),
        DTColumnDefBuilder.newColumnDef(2)
    ];
    
    
    
    /** TABS **/
    $scope.tab = 1;
    $scope.setTab = function(tabId){
    	this.tab = tabId;
    };
    $scope.isSet = function(tabId) {
    	return this.tab === tabId;
    }
    
    /** MODAL BEACONS **/
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    

    $scope.ok = function(imageUri){
    	$scope.newBeacon.activityData.imageUrl = imageUri;
    	$scope.selectedBeacon.activityData.imageUrl = imageUri;
    	$scope.imageUri = null;
    }

    
    
    
  });
/* FIM BEACON CONTROLLERS */

/* click directive */
mymodal.directive('sglclick', ['$parse', function($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
          var fn = $parse(attr['sglclick']);
          var delay = 300, clicks = 0, timer = null;
          element.on('click', function (event) {
            clicks++;  //count clicks
            if(clicks === 1) {
              timer = setTimeout(function() {
                scope.$apply(function () {
                    fn(scope, { $event: event });
                }); 
                clicks = 0;             //after action performed, reset counter
              }, delay);
              } else {
                clearTimeout(timer);    //prevent single-click action
                clicks = 0;             //after action performed, reset counter
              }
          });
        }
    };
}]);

/* modal directive*/
mymodal.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog modal-lg">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });