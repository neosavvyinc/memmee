function LoggedInController($scope, $http, broadCastService) {

    $scope.bodyContentPartial = "partials/viewMode.html";

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