function ViewModeController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmee = null

    $http({method: 'GET', url: '/memmeerest/getmemmee/?apiKey=' + $scope.user.apiKey}).
        success(function(data, status, headers, config) {
            console.log('your memmee has been loaded');
            $scope.memmee = data;
        }).error(function(data, status, headers, config) {
            console.log('error loading your doggone memmee');
        });
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];