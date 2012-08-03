function ProfileController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.confirmedPass = '';
        });

    $scope.update = function()
    {
        if( $scope.confirmedPass != $scope.user.password )
        {
            console.log("your password doesn't match your provided password");
            return;
        }

        console.log("scope.user.id" + $scope.user.id);

        $http({method: 'PUT', url: '/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('your user has been updated')
                broadCastService.loginUser(data);
                broadCastService.showProfileUpdatedSuccess();
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                broadCastService.showProfileUpdatedError();
            });
    }

    if( broadCastService && broadCastService.apiKey )
    {
        $http({method: 'GET', url: '/memmeeuserrest/user/login?apiKey=' + broadCastService.apiKey}).
            success(function(data, status, headers, config) {
                console.log('your user was loaded via API key')
                broadCastService.loginUser(data);
                $scope.user = data;
                $scope.saveLoggedInUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('unable to load your user by API key');
            });
    }
}

ProfileController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];