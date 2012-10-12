var Notifications = (function () {
    "use strict";
    var private_notifications = {
        'PROFILE_UPDATE_SUCCESS_HEADER': "thanks for updating your profile.",
        'PROFILE_UPDATE_SUCCESS_MESSAGE': "let's memmee.",
        'DISCARD_MEMMEE_HEADER': 'discard memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'you have not saved your memmee. would you like to discard it?',
        'DELETE_MEMMEE_HEADER': 'delete memmee?',
        'DELETE_MEMMEE_MESSAGE': 'are you sure you want to delete this memmee?'
    };

    return {
        get: function (name) {
            return private_notifications[name];
        }
    };
}());