function ShowAllCategoriesController($scope, adminService ) {

    $scope.categories = [{}];

    $scope.onShowAll = function() {
        console.log("Show all inspirations");
        adminService.showAllCategories(
            showAllResult, showAllFault
        );
    }

    var showAllResult = function( data ) {
        $scope.categories = data;
    }

    var showAllFault = function( data ) {
        console.log("error loading inspirations");
    }


    $scope.onShowAll();


}