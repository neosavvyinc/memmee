var AlertsControllerEvents = (function () {
    var controllerName = "AlertsController";
    var private = {
        'YES_SELECTED':'yesSelected' + controllerName,
        'NO_SELECTED':'noSelected' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }

})();

var ArchiveListControllerEvents = (function () {
    var controllerName = "ArchiveListController";
    var private = {
        'MEMMEE_SELECTED':'memmeeSelected' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }

})();

var CreateModeControllerEvents = (function () {
    var controllerName = "CreateModeController";
    var private = {
        'CONFIRM_DISCARD':'confirmDiscard' + controllerName,
        'DISCARD_CONFIRMED':'discardConfirmed' + controllerName,
        'MEMMEE_CREATED':'memmeeCreated' + controllerName,
        'CREATE_MODE_CANCELLED':'createModeCancelled' + controllerName,
        'CREATE_MODE_STARTED':'createModeStarted' + controllerName,
        'NEW_USER_LOGIN':'newUserLogin' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }

})();

var LoginControllerEvents = (function () {
    var controllerName = "LoginController";
    var private = {
        'INVALID_LOGIN':'invalidLogin' + controllerName,
        'LOGIN':'login' + controllerName,
        'LOGOUT':'logout' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();

var ProfileControllerEvents = (function () {
    var controllerName = "ProfileController";
    var private = {
        'UPDATE_SUCCESS':'updateSuccess' + controllerName,
        'UPDATE_FAILURE':'updateFailure' + controllerName,
        'PROFILE_OPENED':'profileOpened' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();

var RegistrationControllerEvents = (function () {
    var controllerName = "RegistrationController";
    var private = {
        'ERROR_SAVING':'errorSaving' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();

var ViewModeControllerEvents = (function () {
    var controllerName = "ViewModeController";
    var private = {
        'CONFIRM_DELETE':'confirmDelete' + controllerName,
        'DELETE_CONFIRMED':'deleteConfirmed' + controllerName,
        'SHOW_SHARE_LINK':'showShareLink' + controllerName,
        'MEMMEE_DELETED':'memmeeDeleted' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }

})();