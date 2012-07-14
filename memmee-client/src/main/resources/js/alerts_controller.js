function AlertsController($scope, $http, broadCastService, $location) {


    $scope.$on('showProfileUpdatedSuccess', function() {
        $scope.profileUpdatedSuccessAlert();
    });
    $scope.$on('showProfileUpdatedError', function() {
        $scope.profileUpdatedErrorAlert();
    });

    $scope.profileUpdatedSuccessAlert = function()
    {
        $('#profileUpdatedSuccess').modal('toggle');
    }

    $scope.profileUpdatedErrorAlert = function()
    {
        $('#profileUpdatedError').modal('toggle');
    }

}

AlertsController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];