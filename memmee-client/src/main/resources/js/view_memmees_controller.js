function ViewMemmeesController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmees = [];

    $http({method: 'GET', url: '/memmeerest/getmemmees/?apiKey=' + $scope.user.apiKey}).
        success(function(data, status, headers, config) {
            console.log('your memmees have been loaded')
            $scope.memmees = data;
        }).
        error(function(data, status, headers, config) {
            console.log('error loading your doggone memmees');
        });

}

ViewMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];