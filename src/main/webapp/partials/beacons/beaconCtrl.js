var beaconCtrl = angular.module('beaconCtrl', ['datatables','ngImgCrop']);
/* BEACON CONTROLLERS */
beaconCtrl.controller('BeaconCtrl', function (DTOptionsBuilder, 
											DTColumnDefBuilder,
											BeaconService, 
											ActivityService,
											fileUpload,
											$scope,
											$http,
											$location) {
	$scope.fileSelected = false;
	$scope.croppingImage = true;

	$scope.setFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.theFile = element.files[0];
        });
    };
	

	/* initialize entity's */
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
	    	$scope.newBeacon = beacon;
	    	$scope.disable = true;
	    };
	/* end */
	
    /** Buttons functions in First tab **/
	$scope.editFromSearch = function() {
		$scope.setTab(2);
	};
	$scope.selectFromSearch = function(beacon) {
		$scope.setTab(2);
		$scope.newBeacon = beacon;
	}
	
	$scope.searchBeacons = function() {
		$scope.beacons = BeaconService.query();
	}
	
	$scope.save = function() {
		$scope.newBeacon.activityData.imageDataURI = $scope.myCroppedImage;
		$scope.newBeacon = $scope.newBeacon.$save(function(){
			$scope.showSuccess = true;
			$scope.beacons.push(newBeacon);
			$scope.selectedBeacon = newBeacon;
			$scope.setTab(1);
			$scope.disable = true;
		});
	};
	
	$scope.createBeacon = function() {
		$scope.setTab(2);
		$scope.newBeacon = new BeaconService();
		$scope.newBeacon.activityData = new ActivityService();
		$scope.disable = false;
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
    
    $scope.myImage='';
    $scope.myCroppedImage='';

    var handleFileSelect=function(evt) {
      var file=evt.currentTarget.files[0];
      var reader = new FileReader();
      reader.onload = function (evt) {
        $scope.$apply(function($scope){
          $scope.myImage=evt.target.result;
        });
      };
      reader.readAsDataURL(file);
    };
    angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);
    
    
    
    
  });
/* FIM BEACON CONTROLLERS */

/* click directive */
beaconCtrl.directive('sglclick', ['$parse', function($parse) {
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
beaconCtrl.directive('modal', function () {
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