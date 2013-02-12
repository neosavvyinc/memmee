'use strict'

Memmee.Services.factory('facebookService', ['memmeeService', '$q', 'configuration', function (memmeeService, $q, configuration) {


    // init facebook feed when service is injected
    FB.init({
        appId: configuration.API.APP_ID,
        status: true,
        cookie: true
    });

    var login = function(deferred) {

        FB.login(function(response) {
            if (response.authResponse) {
                console.log('successfully logged in');
                deferred.resolve('oh boy');
            } else {
                console.log('ah, dang, rejected');
                deferred.reject('failed to login');
            }
        });

        return loginStatus.promise;
    };

    var getLoginStatus = function() {
        var deferred = $q.defer();

        FB.getLoginStatus(function(response) {
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

        return userStatus.promise;
    };

    var postToFacebook = function(config) {
        FB.ui(config, function(response) {
            console.dir(response);
        });
    };

    return {
        postMemmee : function(config) {
            getLoginStatus().then(
                // user successfully authenticated
                function(success) {
                    console.log(success);
                    console.dir(config);

                    //postToFacebook(config);
                },
                // user not logged in and/or not authenticated, handle error...
                function(failureMessage) {

                    console.log(failureMessage);
                });
        }
    }
}]);
