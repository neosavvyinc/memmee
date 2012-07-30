var Notifications = (function () {
    var private = {
        'PROFILE_UPDATE_SUCCESS_HEADER':'Thanks for updating your profile',
        'PROFILE_UPDATE_SUCCESS_MESSAGE':'You are all set now go create some Memmees!',
        'DISCARD_MEMMEE_HEADER': 'Discard Memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'You have not saved your memmee. Would you like to discard it?'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();