function RegistrationController($scope, $http) {

    $scope.user = {
        firstName: '',
        lastName: '',
        pass: '',
        email: ''
    };

    $scope.register = function()
    {
        console.log("This is a registration test");
    }


}

function LoginController($scope, $http) {

    $scope.login = function()
    {
        console.log("This is a test");
    }

}