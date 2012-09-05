function BubblesController($scope, $http, broadCastService, $location) {

    //Handlers
    $scope.toggleNewUserCreateMode = function() {
        $scope.showNewUserCreateMode = !$scope.showNewUserCreateMode;
    };

    //Listeners
    $scope.$on(CreateModeControllerEvents.get('NEW_USER_LOGIN'), $scope.toggleNewUserCreateMode);

    //Initialization
    $scope.showNewUserCreateMode = false;

}

BubblesController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];