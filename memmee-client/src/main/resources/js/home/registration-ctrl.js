function RegistrationController($scope, $http, broadCastService, userService) {

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

RegistrationController.$inject = ['$scope', '$http', 'memmeeBroadCastService', 'userService'];