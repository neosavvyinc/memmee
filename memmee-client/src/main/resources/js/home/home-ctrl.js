function HomeController($scope, $http, broadCastService) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
        });

    //UI Initialization
    $scope.initializeScrolling = function (clazz) {
        $(clazz).scrollable();
    };
}

HomeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];