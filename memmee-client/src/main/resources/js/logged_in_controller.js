function LoggedInController($scope, $http, broadCastService) {

    $scope.bodyContentPartial = "partials/createMode.html";

    $scope.$on('createModeStarted', function() {

        $scope.bodyContentPartial = "partials/createMode.html";
        console.log("starting create mode...");

    });

    $scope.$on('createModeCancelled', function() {

        $scope.bodyContentPartial = "partials/viewMode.html";
        console.log("leaving create mode");

    });

}

LoggedInController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];