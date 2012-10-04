var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeBroadCastService", function ($rootScope, $location) {

    var broadCastService = {};

    broadCastService.user = null;
    broadCastService.attachment = null;
    broadCastService.apiKey = $location.search().apiKey;
    broadCastService.selectedMemmee = null;

    broadCastService.loginUser = function ($user) {
        this.user = $user;
        $user.password.value = "";
        localStorage.setItem("user", JSON.stringify($user));
        $rootScope.$broadcast(LoginControllerEvents.get('LOGIN'));

        if ($location.url() == "/home" || $location.url() == "/requiredchangepassword") {
            if ($user.password.temp)
                $location.url("/requiredchangepassword");
            else
                $location.url("/create");
        }
    }

    broadCastService.logoutUser = function ($user) {
        this.user = null;
        localStorage.removeItem("user");
        $location.path('/home');
        $rootScope.$broadcast(LoginControllerEvents.get('LOGOUT'));
    }

    broadCastService.attachmentSuccess = function ($attachment) {
        this.attachment = $attachment;
        $rootScope.$broadcast('attachmentUploadSuccess');
    }

    broadCastService.deleteAttachment = function () {
        $rootScope.$broadcast('deleteAttachment');
    }

    broadCastService.deleteAttachmentSuccess = function () {
        $rootScope.$broadcast('deleteAttachmentSuccess');
    }

    var createMode = true;

    broadCastService.isCreateMode = function () {
        return createMode;
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
    broadCastService.yesSelectedAlertsController = function (promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('YES_SELECTED'), promptingEvent);
    };

    broadCastService.noSelectedAlertsController = function (promptingEvent) {
        $rootScope.$broadcast(AlertsControllerEvents.get('NO_SELECTED'), promptingEvent);
    };

    /**
     * ArchiveListController
     */
    broadCastService.memmeeSelectedArchiveListController = function (memmee) {
        $rootScope.$broadcast(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), memmee);
    };

    /**
     * CreateModeController
     */
    broadCastService.confirmDiscardCreateModeController = function () {
        $rootScope.$broadcast(CreateModeControllerEvents.get('CONFIRM_DISCARD'));
    };

    broadCastService.memmeeCreatedCreateModeController = function (memmee) {
        $rootScope.$broadcast(CreateModeControllerEvents.get('MEMMEE_CREATED'), memmee);
    };

    broadCastService.createModeCancelledCreateModeController = function () {
        createMode = false;
        $location.url("/view");
        $rootScope.$broadcast(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'));
    };

    broadCastService.createModeStartedCreateModeController = function () {
        createMode = true;
        $rootScope.$broadcast(CreateModeControllerEvents.get('CREATE_MODE_STARTED'));
    };

    broadCastService.createModeNewUserLogin = function() {
        $rootScope.$broadcast(CreateModeControllerEvents.get('NEW_USER_LOGIN'));
    };

    /**
     * LoginController
     */
    broadCastService.invalidLoginLoginController = function () {
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

    broadCastService.profileOpenedProfileController = function() {
        $rootScope.$broadcast(ProfileControllerEvents.get('PROFILE_OPENED'));
    }

    /**
     * RegistrationController
     */
    broadCastService.errorSavingUserRegistrationController = function (message) {
        $rootScope.$broadcast(RegistrationControllerEvents.get('ERROR_SAVING'), message);
    }

    /**
     * ViewModeController
     */
    broadCastService.confirmDeleteViewModeController = function () {
        $rootScope.$broadcast(ViewModeControllerEvents.get('CONFIRM_DELETE'));
    }

    broadCastService.memmeeDeletedViewModeController = function () {
        broadCastService.selectedMemmee = null;
        $rootScope.$broadcast(ViewModeControllerEvents.get('MEMMEE_DELETED'));
    }

    broadCastService.showShareLinkViewModeController = function (memmee) {
        $rootScope.$broadcast(ViewModeControllerEvents.get('SHOW_SHARE_LINK'), [memmee])
    }

    /**
     * Initialization
     */
    broadCastService.setTheme = function (number) {
        switch (number) {
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
