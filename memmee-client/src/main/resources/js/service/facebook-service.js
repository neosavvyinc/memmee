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

    login = function (deferred) {
        FB.login(function (response) {
            if (response.authResponse) {
                // resolve promise and propagate via digest
                $rootScope.$apply(function() {
                    deferred.resolve({msg : 'successfully logged in', response : response});
                });

                // get response.authResponse.userID & accessToken, etc... save to localStorage?
                // followup: looks like this is easily accessible via FB API
                //
                // ...
                //

            } else {
                // reject promise and propagate via digest
                $rootScope.$apply(function() {
                    deferred.reject({msg : 'failed to login', response : response});
                });
            }
        }, { auth_type : 'reauthenticate' });

        return deferred;
    };

    getLoginStatus = function () {

        var deferred = $q.defer();

        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                // login promise resolved
                deferred.resolve({msg : 'connected', response : response });

            } else if (response.status === 'not_authorized') {
                // not authorized
                console.log({msg : 'user is not authorized, attempting to log in', response : response});
                login(deferred);
            } else {
                // not logged in
                console.log({msg : 'user is not logged in, attempting to log in', response : response});
                login(deferred);
            }
        });

        return deferred.promise;
    };

    postToFacebook = function(config) {
        FB.ui(config, function(response) {
            console.dir(response);
        });
    };

    return {
        postMemmee : function(config) {
            getLoginStatus().then(
                // user successfully authenticated
                function(success) {
                    postToFacebook(config);
                    console.log(success);
                },
                // user not logged in and/or not authenticated, handle error...
                function(failure) {
                    console.log(failure);
                }
            );
        }
    }
}]);
