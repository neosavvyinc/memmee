function AlertsController($scope, $http, broadCastService, $location) {

    //Action Handlers
    $scope.toggleAlert = function (header, message) {
        $scope.header = header;
        $scope.message = message;
        $('#modalAlert').modal('toggle');
    };

    $scope.toggleYesNoAlert = function (header, message, yesText, noText) {
        $scope.header = header;
        $scope.message = message;
        $scope.yesText = yesText;
        $scope.noText = noText;
        $('#modalYesNoAlert').modal('toggle');
    };

    $scope.yesClick = function () {
        broadCastService.yesSelectedAlertsController($scope.promptingEvent);
    };

    $scope.noClick = function () {
        broadCastService.noSelectedAlertsController($scope.promptingEvent);
    };

    $scope.yesText = "Ok";
    $scope.noText = "Cancel";

    $scope.promptingEvent = null;

    /**
     * Broadcast Handlers
     */

    /* Self */
    $scope.$on(AlertsControllerEvents.get('YES_SELECTED'), function () {
        $('#modalYesNoAlert').modal('toggle');
    });

    $scope.$on(AlertsControllerEvents.get('NO_SELECTED'), function () {
        $('#modalYesNoAlert').modal('toggle');
    });

    /* Create Mode Controller */
    $scope.$on(CreateModeControllerEvents.get('CONFIRM_DISCARD'), function () {
        $scope.promptingEvent = CreateModeControllerEvents.get('CONFIRM_DISCARD');
        $scope.toggleYesNoAlert(Notifications.get('DISCARD_MEMMEE_HEADER'), Notifications.get('DISCARD_MEMMEE_MESSAGE'), "Discard", "Cancel");
    });

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

    /* RegistrationController */
    $scope.$on(RegistrationControllerEvents.get('ERROR_SAVING'), function (event, message) {
        $scope.toggleAlert(Errors.get('REGISTRATION_HEADER'), message);
    });

    /* ViewModeController */
    $scope.$on(ViewModeControllerEvents.get('CONFIRM_DELETE'), function() {
        $scope.promptingEvent = ViewModeControllerEvents.get('CONFIRM_DELETE');
        $scope.toggleYesNoAlert(Notifications.get('DELETE_MEMMEE_HEADER'), Notifications.get('DELETE_MEMMEE_MESSAGE'), "Delete", "Cancel");
    });

    $scope.$on(ViewModeControllerEvents.get('SHOW_SHARE_LINK'), function( event, message) {
        console.log("Memmee with id: " + message[0].id + " sharekey: " + message[0].shareKey);
        $scope.toggleAlert("You generated this link, send it to some friends", "http://local.memmee.com/memmeerest/?shareKey=" + message[0].shareKey);
    });

    //Initialization
    $scope.header = null;
    $scope.message = null;
}

AlertsController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location'];