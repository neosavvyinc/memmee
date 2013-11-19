function LoginController($scope, $http, broadCastService, userService, $rootScope) {

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
        //Sets up notifications that only occur after login
        console.log("login?")

        broadCastService.firstLoginUser();

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
            $scope.forgotPasswordInputStyle = null;

            userService.forgotPassword(
                $scope.user,
                function(data) {
                    $scope.forgotPasswordReminder = "There was an error sending the new password to your email.";
                    $scope.forgotPasswordInputStyle = {'border-color': "#D84133"};

                },
                function(fault) {
                    $scope.forgotPasswordReminder = "There was an error sending the new password to your email.";
                    $scope.forgotPasswordInputStyle = {'border-color': "#D84133"};
                }
            )


        } else {
            $scope.forgotPasswordInputStyle = {'border-color': "#D84133"};
        }
    }
}

LoginController.$inject = ['$scope', '$http', 'memmeeBroadCastService', 'userService', '$rootScope'];