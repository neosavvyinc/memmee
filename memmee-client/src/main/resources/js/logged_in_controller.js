function LoggedInController($scope, $http, broadCastService) {

    $scope.createMode = false;

    $scope.toggleCreateMode = function() {
        $scope.createMode = !($scope.createMode);
    }

}

LoggedInController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];