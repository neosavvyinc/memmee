var Notifications = (function () {
    var private = {
        'PROFILE_UPDATE_SUCCESS_HEADER':'Thanks for updating your profile',
        'PROFILE_UPDATE_SUCCESS_MESSAGE':'You are all set now go create some Memmees!'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();