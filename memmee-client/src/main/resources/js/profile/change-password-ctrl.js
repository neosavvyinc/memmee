function RequiredChangePasswordController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.confirmedPass = '';
            $scope.passwordError = '';
        });

    $scope.changePassword = function () {
        if ($scope.confirmedPass != $scope.user.password.value) {
            $scope.passwordError = "The confirmation does not match the password value.";
            return;
        }

        $scope.passwordError = "The confirmation does not match the password value.";

        $http({method:'PUT', url:'/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data:$scope.user}).
            success(function (data, status, headers, config) {
                broadCastService.loginUser(data);
            }).
            error(function (data, status, headers, config) {
                broadCastService.showProfileUpdatedError();
            });
    }

}

RequiredChangePasswordController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];