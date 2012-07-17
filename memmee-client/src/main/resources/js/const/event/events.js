var LoginControllerEvents = (function () {
    var controllerName = "LoginController";
    var private = {
        'INVALID_LOGIN':'invalidLogin' + controllerName
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
        'UPDATE_FAILURE':'updateFailure' + controllerName
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();