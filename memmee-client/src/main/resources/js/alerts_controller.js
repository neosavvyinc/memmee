function AlertsController($scope, $http, broadCastService, $location) {

    //Action Handlers
    $scope.toggleAlert = function (header, message) {
        $scope.header = header;
        $scope.message = message;
        $('#modalAlert').modal('toggle');
    }

    /**
     * Broadcast Handlers
     */

    /* LoginController */
    $scope.$on(LoginControllerEvents.get('INVALID_LOGIN'), function () {
        $scope.toggleAlert(Errors.get('INVALID_LOGIN_HEADER'), Errors.get('INVALID_LOGIN_MESSAGE'));
    });

    /* ProfileController */
    $scope.$on(ProfileControllerEvents.get('UPDATE_SUCCESS'), function () {
        $scope.toggleAlert(Notifications.get('PROFILE_UPDATE_SUCCESS_HEADER'), Notifications.get('PROFILE_UPDATE_SUCCESS_MESSAGE'));
    });

    $scope.$on(ProfileControllerEvents.get('UPDATE_FAILURE'), function () {
        $scope.toggleAlert(Errors.get('PROFILE_UPDATE_HEADER'), Errors.get('PROFILE_UPDATE_MESSAGE'));
    });

    //Initialization
    $scope.header = null;
    $scope.message = null;
}

AlertsController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];