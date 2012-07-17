function RegistrationController($scope, $http, broadCastService) {

    $scope.user = {
        email: ''
    };

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                $scope.saveLoggedInUser(data);
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                broadCastService.errorSavingUserRegistrationController(data);
            });
    }


}

RegistrationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];