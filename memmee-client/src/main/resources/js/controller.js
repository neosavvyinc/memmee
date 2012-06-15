function RegistrationController($scope, $http) {

    $scope.user = {
        email: ''
    };

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving a new user');
            });
    }


}

function LoginController($scope, $http) {

    $scope.login = function()
    {
        console.log("This is a test");
    }

}