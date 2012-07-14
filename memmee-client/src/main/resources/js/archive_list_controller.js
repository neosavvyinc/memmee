function ArchiveListController($scope, $http, broadCastService) {
    $scope.user = broadCastService.user;
    $scope.memmees = null;

    $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
        success(function (data, status, headers, config) {
            console.log('your memmee has been loaded');
            $scope.memmees = data;
        }).
        error(function (data, status, headers, config) {
              console.log('error loading memmees');
        });

}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];