var Errors = (function () {
    "use strict";
    var private_events = {
        'INVALID_LOGIN_HEADER': 'there was a problem logging in',
        'INVALID_LOGIN_MESSAGE': 'you have entered an invalid username or password.',
        'PROFILE_UPDATE_HEADER': 'there was a problem updating your profile',
        'PROFILE_UPDATE_MESSAGE': "thanks for updating your profile. let's memmee.",
        'REGISTRATION_HEADER': 'there was a problem signing up'
    };

    return {
        get: function (name) {
            return private_events[name];
        }
    };
}());