function LoggedInController($scope, $http, broadCastService) {

    $scope.createMode = false;
    /*'partials/archiveList.html'*/
    $scope.bodyContentPartial = "partials/createMode.html";


    $scope.toggleCreateMode = function() {
        $scope.createMode = !($scope.createMode);

        if( $scope.createMode )
        {
            $scope.bodyContentPartial = "partials/createMode.html";
        }
        else
        {
            $scope.bodyContentPartial = "partials/viewMode.html";
        }
    }

}

LoggedInController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];