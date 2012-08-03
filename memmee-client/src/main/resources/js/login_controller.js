function LoginController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = {
                email:'',
                password:''
            };
            $scope.forgotPasswordReminder = "";
            $scope.forgotPasswordSuccessStyle = {};
        });

    //Action Handlers
    $scope.login = function () {
        $http({method:'POST', url:'/memmeeuserrest/user/login', data:$scope.user}).
            success(function (data, status, headers, config) {
                console.log('you were successfully logged into memmee.com');
                broadCastService.loginUser(data);
            }).
            error(function (data, status, headers, config) {
                broadCastService.invalidLoginLoginController();
            });
    }

    $scope.forgotPassword = function () {
        if ($scope.user['email'] != "") {
            $scope.forgotPasswordReminder = "";
            $http({method:'POST', url:'/memmeeuserrest/user/forgotpassword?email=' + $scope.user['email']}).
                success(function (data, status, headers, config) {
                    $scope.forgotPasswordReminder = "A new password was sent to your email";
                    $scope.forgotPasswordSuccessStyle = {'color': '#0000FF'};
                }).
                error(function (data, status, headers, config) {
                    $scope.forgotPasswordReminder = "There was an error sending the new password to your email.";
                });
        } else {
           $scope.forgotPasswordReminder = "Please enter your email address below";
        }
    }

}

LoginController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];