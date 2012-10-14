function ProfileController($scope, $http, broadCastService, $location) {
    "use strict";

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.confirmedPass = '';
        });

    $scope.validUserOrApiKey = function () {

        if (!broadCastService) {
            return false;
        }

        if (broadCastService.apiKey === undefined &&
            broadCastService.user === undefined &&
            (broadCastService.user.id === "" ||
                broadCastService.user.id === undefined ||
                broadCastService.user.id === null)) {
            return false;
        }
        return true;
    };

    $scope.update = function () {

        if ($scope.confirmedPass !== $scope.user.password.value){
            console.log("your password doesn't match your provided password");
            return;
        }

        console.log("scope.user.id" + $scope.user.id);

        $http({method: 'PUT', url: '/memmeeuserrest/user/' + $scope.user.id + '?apiKey=' + $scope.user.apiKey, data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('your user has been updated');
                broadCastService.loginUser(data);
                broadCastService.showProfileUpdatedSuccess();
                broadCastService.createModeStartedCreateModeController();
                $location.path("create");
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving your user');
                broadCastService.showProfileUpdatedError();
            });
    };

    if ($scope.validUserOrApiKey()) {
        console.log("Retrieving a user based on their apiKey");
        $http({method: 'GET', url: '/memmeeuserrest/user/login?apiKey=' + broadCastService.user.apiKey}).
            success(function(data, status, headers, config) {
                console.log('your user was loaded via API key');
                broadCastService.loginUser(data);
                $scope.user = data;
//                $scope.saveLoggedInUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('unable to load your user by API key');
                $location.path("/home");
            });
    } else {
        $location.path("/home");
    }
}

ProfileController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];