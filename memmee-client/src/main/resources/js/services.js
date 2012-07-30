var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeBroadCastService", function ($rootScope, $location) {

    var broadCastService = {};

    broadCastService.user = null;
    broadCastService.attachment = null;
    broadCastService.apiKey = $location.search().apiKey;

    broadCastService.loginUser = function ($user) {
        this.user = $user;
        localStorage.setItem("user", JSON.stringify($user));
        $rootScope.$broadcast('handleLogin');

        if ($location.url() == "/home")
            $location.url("/loggedin");
    }

    broadCastService.logoutUser = function ($user) {
        this.user = null;
        localStorage.removeItem("user");
        $location.path('/home');
        $rootScope.$broadcast('handleLogout');
    }

    broadCastService.attachmentSuccess = function ($attachment) {
        this.attachment = $attachment;
        $rootScope.$broadcast('attachmentUploadSuccess');
    }


    var createMode = false;

    broadCastService.isCreateMode = function () {
        return createMode;
    }

    broadCastService.createModeStarted = function () {
        createMode = true;
        $rootScope.$broadcast('createModeStarted');
    }

    broadCastService.createModeCancelled = function () {
        createMode = false;
        $rootScope.$broadcast('createModeCancelled');
    }

    /**
     * Modal Popup Notifications:
     */
    broadCastService.showProfileUpdatedSuccess = function () {
        $rootScope.$broadcast('showProfileUpdatedSuccess');
    }

    broadCastService.showProfileUpdatedError = function () {
        $rootScope.$broadcast('showProfileUpdatedError');
    }

    /**
     * AlertsController
     */
    broadCastService.yesSelectedAlertsController = function(promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('YES_SELECTED'), promptingEvent);
    };

    broadCastService.noSelectedAlertsController = function(promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('NO_SELECTED'), promptingEvent);
    };

    /**
     * ArchiveListController
     */
    broadCastService.memmeeSelectedArchiveListController = function(memmee) {
        $rootScope.$broadcast(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), memmee);
    };

    /**
     * CreateModeController
     */
    broadCastService.confirmDiscardCreateModeController = function() {
        $rootScope.$broadcast(CreateModeControllerEvents.get('CONFIRM_DISCARD'));
    }

    /**
     * LoginController
     */
    broadCastService.invalidLoginLoginController = function() {
      $rootScope.$broadcast(LoginControllerEvents.get('INVALID_LOGIN'));
    };

    /**
     * ProfileController
     */
    broadCastService.showProfileUpdatedSuccess = function () {
        $rootScope.$broadcast(ProfileControllerEvents.get('UPDATE_SUCCESS'));
    }

    broadCastService.showProfileUpdatedError = function () {
        $rootScope.$broadcast(ProfileControllerEvents.get('UPDATE_FAILURE'));
    }

    /**
     * RegistrationController
     */
    broadCastService.errorSavingUserRegistrationController = function(message) {
        $rootScope.$broadcast(RegistrationControllerEvents.get('ERROR_SAVING'), message);
    }

    //Initialization
    broadCastService.setTheme = function( number )
    {
        switch ( number )
        {
            case 0:
                $rootScope.$broadcast('changeToDefaultTheme');
                break;
            case 1:
                $rootScope.$broadcast('changeToTheme1');
                break;
            case 2:
                $rootScope.$broadcast('changeToTheme2');
                break;
            case 3:
                $rootScope.$broadcast('changeToTheme3');
                break;
        }
    }

    return broadCastService;
});
