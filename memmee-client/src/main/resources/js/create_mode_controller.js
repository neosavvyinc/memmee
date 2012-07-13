function CreateMemmeesController($scope, $http, broadCastService) {

    $scope.master= {
        id: '',
        userId: broadCastService.user ? broadCastService.user.id : null,
        text: ''
    };

    $scope.memmee = {
        id: '',
        userId: broadCastService.user ? broadCastService.user.id : null,
        text: ''
    };

    $scope.update = function(memmee) {
        $scope.master= angular.copy(memmee);
    };

    $scope.reset = function() {
        $scope.memmee = angular.copy($scope.master);
    };

    $scope.isUnchanged = function(memmee) {
        return angular.equals(memmee, $scope.master);
    };

    $scope.cancel = function()
    {
        broadCastService.createModeCancelled();
    }

    $scope.createMemmee = function()
    {
        $http({method: 'POST', url: '/memmeerest/insertmemmee/?apiKey=' + broadCastService.user.apiKey, data: $scope.memmee}).
            success(function(data, status, headers, config) {
                console.log('you have saved a memmee');
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                console.log(data);
            });
    }
}

CreateMemmeesController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];