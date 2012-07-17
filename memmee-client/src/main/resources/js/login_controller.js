function LoginController($scope, $http, broadCastService) {

    $scope.user = {
        email: '',
        password: ''
    };

    $scope.login = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user/login', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully logged into memmee.com');
                broadCastService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                broadCastService.invalidLoginLoginController();
            });
    }

}

LoginController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];