var Notifications = (function () {
    var private = {
        'PROFILE_UPDATE_SUCCESS_HEADER':'Thanks for updating your profile',
        'PROFILE_UPDATE_SUCCESS_MESSAGE':'You are all set now go create some Memmees!',
        'DISCARD_MEMMEE_HEADER': 'Discard Memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'You have not saved your memmee. Would you like to discard it?',
        'DELETE_MEMMEE_HEADER': 'Delete Memmee?',
        'DELETE_MEMMEE_MESSAGE': 'Are you sure you want to delete this Memmee?'
    };

    return {
        get:function (name) {
            return private[name];
        }
    }
})();