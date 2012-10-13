function RegistrationController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = {
                email: ''
            };
        });

    $scope.register = function()
    {
        //Sets up notifications that only occur after login
        broadCastService.firstLoginUser();

        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                broadCastService.errorSavingUserRegistrationController(data);
            });
    }
}

RegistrationController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];