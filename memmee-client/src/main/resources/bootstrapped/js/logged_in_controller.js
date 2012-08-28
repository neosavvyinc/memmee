function LoggedInController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.bodyContentPartial = "partials/createMode.html";
        });

    $scope.$on(CreateModeControllerEvents.get('CREATE_MODE_STARTED'), function() {

        $scope.bodyContentPartial = "partials/createMode.html";
        console.log("starting create mode...");

    });

    $scope.$on(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'), function() {

        $scope.bodyContentPartial = "partials/viewMode.html";
        console.log("leaving create mode");

    });

}

LoggedInController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];