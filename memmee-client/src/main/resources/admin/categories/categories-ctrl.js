function ShowAllCategoriesController($scope, adminService ) {

    $scope.categoryToAdd = {};
    $scope.categories = [{}];

    $scope.onAddCategory = function() {
        adminService.addCategory(
            $scope.categoryToAdd,
            function( data ) {
                $scope.categoryToAdd = {};
                $scope.onShowAll();
            },
            function( fault ) {
                console.log("something broke when adding a new category");
            }
        )
    }

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