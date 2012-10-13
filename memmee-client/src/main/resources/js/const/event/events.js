var AlertsControllerEvents = (function () {
    "use strict";
    var controllerName = "AlertsController",
        priv_events = {
            'YES_SELECTED': 'yesSelected' + controllerName,
            'NO_SELECTED': 'noSelected' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var ArchiveListControllerEvents = (function () {
    "use strict";
    var controllerName = "ArchiveListController",
        priv_events = {
            'MEMMEE_SELECTED': 'memmeeSelected' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var CreateModeControllerEvents = (function () {
    "use strict";
    var controllerName = "CreateModeController",
        priv_events = {
            'CONFIRM_DISCARD': 'confirmDiscard' + controllerName,
            'DISCARD_CONFIRMED': 'discardConfirmed' + controllerName,
            'MEMMEE_CREATED': 'memmeeCreated' + controllerName,
            'CREATE_MODE_CANCELLED': 'createModeCancelled' + controllerName,
            'CREATE_MODE_STARTED': 'createModeStarted' + controllerName,
            'NEW_USER_LOGIN': 'newUserLogin' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());

var LoginControllerEvents = (function () {
    "use strict";
    var controllerName = "LoginController",
        priv_events = {
            'INVALID_LOGIN': 'invalidLogin' + controllerName,
            'LOGIN': 'login' + controllerName,
            'FIRST_LOGIN': 'firstLogin' + controllerName,
            'LOGOUT': 'logout' + controllerName,
            'FORGOT_PASSWORD_SENT': 'forgotPasswordSent' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var ProfileControllerEvents = (function () {
    "use strict";
    var controllerName = "ProfileController",
        priv_events = {
            'UPDATE_SUCCESS': 'updateSuccess' + controllerName,
            'UPDATE_FAILURE': 'updateFailure' + controllerName,
            'PROFILE_OPENED': 'profileOpened' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var RegistrationControllerEvents = (function () {
    "use strict";
    var controllerName = "RegistrationController",
        priv_events = {
            'ERROR_SAVING': 'errorSaving' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };
}());

var ViewModeControllerEvents = (function () {
    "use strict";
    var controllerName = "ViewModeController",
        priv_events = {
            'CONFIRM_DELETE': 'confirmDelete' + controllerName,
            'DELETE_CONFIRMED': 'deleteConfirmed' + controllerName,
            'SHOW_SHARE_LINK': 'showShareLink' + controllerName,
            'MEMMEE_DELETED': 'memmeeDeleted' + controllerName
        };

    return {
        get: function (name) {
            return priv_events[name];
        }
    };

}());