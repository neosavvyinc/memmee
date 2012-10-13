var Notifications = (function () {
    "use strict";
    var private_notifications = {
        'PROFILE_UPDATE_SUCCESS_HEADER': "thanks for updating your profile.",
        'PROFILE_UPDATE_SUCCESS_MESSAGE': "let's memmee.",
        'DISCARD_MEMMEE_HEADER': 'discard memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'you have not saved your memmee. would you like to discard it?',
        'DELETE_MEMMEE_HEADER': 'delete memmee?',
        'DELETE_MEMMEE_MESSAGE': 'are you sure you want to delete this memmee?',
        'INSPIRATION_PROMPT': "record what's on your mind today: a conversation, a feeling, a laugh. a few words will do. need inspiration? hit the arrow on the right for writing prompts.",
        'FORGOT_PASSWORD_SENT_HEADER': "your temporary password was sent...",
        'FORGOT_PASSWORD_SENT_MESSAGE': "you will receive an email within a few moments with your new temporary password."
    };

    return {
        get: function (name) {
            return private_notifications[name];
        }
    };
}());