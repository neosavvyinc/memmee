function AdminController($scope, adminService ) {
    "use strict";
    console.log("Firing up the admin controller");

    $scope.inspirations = [{}];

    $scope.onShowAll = function() {
        console.log("Show all inspirations");
        adminService.showAllInspirations(
            showAllResult, showAllFault
        );
    }

    var showAllResult = function( data ) {
        $scope.inspirations = data;
    }

    var showAllFault = function( data ) {
        console.log("error loading inspirations");
    }

}