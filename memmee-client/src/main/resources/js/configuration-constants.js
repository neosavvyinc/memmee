
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
            APP_ID: "450283398376566"; // production: "280599165382862",
        }
    },
    EVENTS: {
        FACEBOOK_LINK_GENERATED: "facebookLinkGenerated"
    }

});
