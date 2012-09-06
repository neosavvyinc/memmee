function BubblesController($scope, $http, broadCastService, $location) {

    //Handlers
    $scope.toggleNewUserCreateMode = function () {
        $scope.showNewUserCreateMode = !$scope.showNewUserCreateMode;
    };

    $scope.clearALlBubbles = function() {
        $scope.showNewUserCreateMode = false;
    };

    //Styles
    $scope.setNewUserCreateModeStyle = function () {
        $scope.newUserCreateModeStyle = {
            'top':"270px",
            'left':((window.innerWidth / 2) + 185).toString() + "px"};
        $scope.$apply();
    }

    //Listeners
    $scope.$on(CreateModeControllerEvents.get('NEW_USER_LOGIN'), $scope.toggleNewUserCreateMode);
    $scope.$on(LoginControllerEvents.get('LOGOUT'), $scope.clearALlBubbles);
    $scope.$on(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'), $scope.clearALlBubbles);
    window.addEventListener('onresize', $scope.setNewUserCreateModeStyle, false);
    window.addEventListener('resize', $scope.setNewUserCreateModeStyle, false);

    //Initialization
    $scope.showNewUserCreateMode = false;
    $scope.setNewUserCreateModeStyle();
}

BubblesController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];