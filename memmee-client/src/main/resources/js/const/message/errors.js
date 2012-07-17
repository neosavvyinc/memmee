var Errors = (function () {
    var private = {
        'INVALID_LOGIN_HEADER':'There was a problem logging in',
        'INVALID_LOGIN_MESSAGE':'You have entered an invalid username or password.',
        'PROFILE_UPDATE_HEADER':'There was a problem updating your profile',
        'PROFILE_UPDATE_MESSAGE':'You need to try again to update this profile'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();