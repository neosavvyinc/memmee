
'use strict'

/**
 * Defines application-wide key value pairs
 */

Memmee.Constants.constant('configuration', {

    ENDPOINTS:{
        MEMMEE: {
            SHARE: "/memmeerest/sharememmee"
        }
    },
    API: {
        FACEBOOK: {
            SHARE_URL: "http://www.facebook.com/sharer.php?",
            FEED_URL: "https://www.facebook.com/dialog/feed?",
            APP_ID_DEV: "450283398376566",
            APP_ID: "280599165382862"
        }
    },
    EVENTS: {
        FACEBOOK_LINK_GENERATED: "facebookLinkGenerated"
    }

});
