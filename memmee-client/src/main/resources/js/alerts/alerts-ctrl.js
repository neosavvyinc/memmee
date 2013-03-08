function AlertsController($scope, $http, broadCastService, $location, configuration) {

    $scope.isModalPresent = "isHidden";
    $scope.isModalAlertPresent = "isHidden";
    $scope.isModalYesNoAlertPresent = "isHidden";

    $scope.yesText = "Ok";
    $scope.noText = "Cancel";

    $scope.promptingEvent = null;


    //Action Handlers
    $scope.showAlert = function (header, message) {
        $scope.header = header;
        $scope.message = message;

        $scope.isModalPresent = "isVisible";
        $scope.isModalAlertPresent = "isVisible";
    };

    $scope.hideAlert = function () {
        $scope.isModalPresent = "isHidden";
        $scope.isModalAlertPresent = "isHidden";
    };

    $scope.showYesNoAlert = function (header, message, yesText, noText) {
        $scope.header = header;
        $scope.message = message;
        $scope.yesText = yesText;
        $scope.noText = noText;

        $scope.isModalPresent = "isVisible";
        $scope.isModalYesNoAlertPresent = "isVisible";
    };

    $scope.hideYesNoAlert = function () {
        $scope.isModalPresent = "isHidden";
        $scope.isModalYesNoAlertPresent = "isHidden";
        $scope.promptingEvent = null;
    };

    $scope.yesClick = function () {
        broadCastService.yesSelectedAlertsController($scope.promptingEvent);
        $scope.hideAlert();
        $scope.hideYesNoAlert();
        $scope.promptingEvent = null;
    };

    $scope.noClick = function () {
        broadCastService.noSelectedAlertsController($scope.promptingEvent);
        $scope.hideAlert();
        $scope.hideYesNoAlert();
        $scope.promptingEvent = null;
    };


    /* Create Mode Controller */
    $scope.$on(CreateModeControllerEvents.get('CONFIRM_DISCARD'), function () {
        $scope.promptingEvent = CreateModeControllerEvents.get('CONFIRM_DISCARD');
        $scope.showYesNoAlert(Notifications.get('DISCARD_MEMMEE_HEADER'), Notifications.get('DISCARD_MEMMEE_MESSAGE'), "Discard", "Cancel");
    });

    $scope.$on(CreateModeControllerEvents.get('NEW_USER_LOGIN'), function () {
//        $scope.showAlert("Temporary Alert", "This will show on the first two logins for any new user. This is for instructions.");
    });

    /* LoginController */
    $scope.$on(LoginControllerEvents.get('INVALID_LOGIN'), function () {
        $scope.showAlert(Errors.get('INVALID_LOGIN_HEADER'), Errors.get('INVALID_LOGIN_MESSAGE'));
    });

    $scope.$on(LoginControllerEvents.get('FORGOT_PASSWORD_SENT'), function () {
        $scope.showAlert(Notifications.get("FORGOT_PASSWORD_SENT_HEADER"), Notifications.get("FORGOT_PASSWORD_SENT_MESSAGE"));
    });

    $scope.$on(LoginControllerEvents.get('FORGOT_PASSWORD_ERROR'), function () {
        $scope.showAlert(Errors.get("FORGOT_PASSWORD_HEADER"), Errors.get("FORGOT_PASSWORD_MESSAGE"));
    });

    /* ProfileController */
    $scope.$on(ProfileControllerEvents.get('UPDATE_SUCCESS'), function () {
        $scope.showAlert(Notifications.get('PROFILE_UPDATE_SUCCESS_HEADER'), Notifications.get('PROFILE_UPDATE_SUCCESS_MESSAGE'));
    });

    $scope.$on(ProfileControllerEvents.get('UPDATE_FAILURE'), function () {
        $scope.showAlert(Errors.get('PROFILE_UPDATE_HEADER'), Errors.get('PROFILE_UPDATE_MESSAGE'));
    });

    /* RegistrationController */
    $scope.$on(RegistrationControllerEvents.get('ERROR_SAVING'), function (event, message) {
        $scope.showAlert(Errors.get('REGISTRATION_HEADER'), "we had a problem signing up with that email address, if you already created an account you should reset your password");
    });

    /* ViewModeController */

    //Delete
    $scope.$on(ViewModeControllerEvents.get('CONFIRM_DELETE'), function () {
        $scope.showDiscardAlert = true;
        $scope.promptingEvent = ViewModeControllerEvents.get('CONFIRM_DELETE');
        $scope.showYesNoAlert(Notifications.get('DELETE_MEMMEE_HEADER'), Notifications.get('DELETE_MEMMEE_MESSAGE'), "Delete", "Keep");
    });
    $scope.onDeleteClick = function () {

    };

    $scope.$on(ViewModeControllerEvents.get('SHOW_SHARE_LINK'), function (event, message) {
        console.log("Memmee with id: " + message[0].id + " sharekey: " + message[0].shareKey);
        var shareUrl = message[0].shortenedUrl;
        console.log("location:::" + shareUrl);
        $scope.showAlert("copy and paste the link below to share.",
            "(don't worry, your other memmees will remain private.)<br><br><a href='"+ shareUrl +"'>" + shareUrl + "</a>");
    });

    $scope.$on(ViewModeControllerEvents.get('SHOW_FB_POST_SUCCESS'), function() {
        $scope.showAlert("You successfully posted your memmee to Facebook!");
    });

    $scope.$on(configuration.EVENTS.FACEBOOK_LINK_GENERATED, function (event, link) {
        $scope.showAlert("follow the link below to post on facebook.",
            "(don't worry, your other memmees will remain private.)<br><br><a href='" + link + "' target='_blank'>post my memmee</a>");
    });

    $scope.$on("closeAllDropdowns", function (event, next, current) {
        console.log("Hiding all alerts on route changed...");
        $scope.hideYesNoAlert();
        $scope.hideAlert();
    });

    //Initialization
    $scope.header = null;
    $scope.message = null;

    $scope.showDiscardAlert = false;
}

AlertsController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location', 'configuration'];
