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
                    deferred.resolve('successfully logged in');
                });
            } else {
                // reject promise and propagate via digest
                $rootScope.$apply(function() {
                    deferred.reject({msg : 'failed to login', response : response});
                });
            }
        });

        return deferred;
    };

    getLoginStatus = function () {

        var deferred = $q.defer();

        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                // login promise resolved
                deferred.resolve('connected');

            } else if (response.status === 'not_authorized') {
                // not authorized
                console.log('user is not authorized, attempting to log in');
                login(deferred);
            } else {
                // not logged in
                console.log('user is not logged in, attempting to log in');
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
                },
                // user not logged in and/or not authenticated, handle error...
                function(failure) {
                    console.log(failure);
                }
            );
        }
    }
}]);
