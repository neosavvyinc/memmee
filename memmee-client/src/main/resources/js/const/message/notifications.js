var Notifications = (function () {
    "use strict";
    var private_notifications = {
        'PROFILE_UPDATE_SUCCESS_HEADER': "thanks for updating your profile.",
        'PROFILE_UPDATE_SUCCESS_MESSAGE': "let's memmee.",
        'DISCARD_MEMMEE_HEADER': 'discard memmee?',
        'DISCARD_MEMMEE_MESSAGE': 'you have not saved your memmee. would you like to discard it?',
        'DELETE_MEMMEE_HEADER': 'delete memmee?',
        'DELETE_MEMMEE_MESSAGE': 'are you sure you want to delete this memmee?',
        'INSPIRATION_PROMPT': "record what's on your mind today: a conversation, a feeling, an encounter, a mood, a laugh-out-loud moment. a few words or lines will do. need inspiration? hit the arrow on the right for writing prompts."
    };

    return {
        get: function (name) {
            return private_notifications[name];
        }
    };
}());