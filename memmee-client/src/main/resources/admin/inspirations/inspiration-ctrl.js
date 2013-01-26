function ShowAllInspirationsController($scope, adminService ) {

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


    $scope.onShowAll();


}