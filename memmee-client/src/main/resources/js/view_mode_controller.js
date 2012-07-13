function ViewModeController($scope, $http, broadCastService) {
    $scope.user = broadCastService.user;
    $scope.memmee = null
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];