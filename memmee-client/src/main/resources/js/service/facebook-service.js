'use strict'

Memmee.Services.factory('facebookService', ['$rootScope', '$q', 'configuration', function ($rootScope, $q, configuration) {

    var login,
        getLoginStatus,
        postToFacebook;

    // init facebook feed when service is injected
    FB.init({
        appId: configuration.API.FACEBOOK.APP_ID_DEV,
        status: true,
        cookie: true
    });

    login = function (promise) {
        FB.login(function (response) {
            if (response.authResponse) {
                // resolve promise and propagate via digest
                $rootScope.$apply(function() {
                    promise.resolve({msg : 'successfully logged in', response : response});
                });

                // get response.authResponse.userID & accessToken, etc... save to localStorage?
                // followup: looks like this is easily accessible via FB API
                //
                // ...
                //

            } else {
                // reject promise and propagate via digest
                $rootScope.$apply(function() {
                    promise.reject({msg : 'failed to login', response : response});
                });
            }
        });

        return promise;
    };

    getLoginStatus = function () {

        var loginStatusDeferred = $q.defer();

        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                // login promise resolved
                loginStatusDeferred.resolve({msg : 'connected', response : response });

            } else if (response.status === 'not_authorized') {
                // not authorized
                console.log({msg : 'user is not authorized, attempting to log in', response : response});
                login(loginStatusDeferred);
            } else {
                // not logged in
                console.log({msg : 'user is not logged in, attempting to log in', response : response});
                login(loginStatusDeferred);
            }
        });

        return loginStatusDeferred.promise;
    };

    postToFacebook = function(config, promise) {
        FB.ui(config, function(response) {

            if (response && response.post_id) {
                $rootScope.$apply(function() {
                    promise.resolve({msg: 'successfully posted to facebook', response : response });
                });
            } else {
                $rootScope.$apply(function() {
                    promise.reject({msg : 'facebook post failed', response : response });
                });
            }
        });
    };

    return {
        postMemmee : function(config) {

            var postStatusDeferred = $q.defer();

            getLoginStatus().then(
                // user successfully authenticated
                function(success) {
                    postToFacebook(config, postStatusDeferred);
                },
                // user not logged in and/or not authenticated, handle error...
                function(failure) {
                    console.log(failure);
                    postStatusDeferred.reject({msg : 'facebook post failed', response : failure });
                }
            );

            return postStatusDeferred.promise;
        }
    }
}]);
