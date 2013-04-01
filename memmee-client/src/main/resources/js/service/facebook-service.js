'use strict'

Memmee.Services.factory('facebookService', ['$rootScope', '$q', 'configuration', 'facebookLibService', function ($rootScope, $q, configuration, facebookLibService) {

    var FB = facebookLibService;

    // init facebook feed when service is injected
    FB.init({
        appId: configuration.API.FACEBOOK.APP_ID,
        status: true,
        cookie: true
    });

    var login = function (promise) {

        var deferred = $q.defer();

        FB.login(function (response) {
            if (response.authResponse) {
                // resolve promise and propagate via digest
                console.log("========= FB: user logged in after determining not authorized")
                $rootScope.$apply(function() {
                    deferred.resolve({msg : 'successfully logged in', response : response});
                });
            } else {
                // reject promise and propagate via digest
                console.log("========= FB: user failed to login")
                $rootScope.$apply(function() {
                    deferred.reject({msg : 'failed to login', response : response});
                });
            }
        });

        return deferred.promise;
    };

    var ensureUserLoggedIn = function (shared) {

        var deferred = $q.defer();

        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                // login promise resolved

                console.log("========= FB: user logged in");

                // hack as this doesn't work without $rootScope.apply from the share page
                if( shared ) {
                    $rootScope.$apply(function() {
                        deferred.resolve({msg : 'connected', response : response });
                    });
                }
                else
                {
                    //however this works on non share pages - def a bug
                    deferred.resolve({msg : 'connected', response : response });
                }

            } else if (response.status === 'not_authorized') {
                // not authorized
                console.log("========= FB: user not authorized")
                login().then(
                    function (logInSuccess) {
                        deferred.resolve({msg : 'logged in', response : logInSuccess });
                    },
                    function (logInFailure) {
                        deferred.reject({msg : 'failed to log in', response : logInFailure});
                    }
                );
            } else {
                // not logged in
                console.log("========= FB: user not logged in")
                login().then(
                    function (logInSuccess) {
                        deferred.resolve({msg : 'logged in', response : logInSuccess });
                    },
                    function (logInFailure) {
                        deferred.reject({msg : 'failed to log in', response : logInFailure});
                    }
                );
            }
        });

        return deferred.promise;
    };

    var postToFacebook = function(config, promise) {

        var deferred = $q.defer();

        FB.ui(config, function(response) {
            if (response && response.post_id) {
                $rootScope.$apply(function() {
                    deferred.resolve({msg: 'successfully posted to facebook', response : response });
                });
            } else {
                $rootScope.$apply(function() {
                    deferred.reject({msg : 'facebook post failed', response : response });
                });
            }
        });

        return deferred.promise;
    };

    return {


        postMemmee : function(config, shared) {

            var deferred = $q.defer();

            ensureUserLoggedIn(shared).then(
                // user successfully authenticated
                function(logInSuccess) {
                    postToFacebook(config).then(
                        function(postSuccess) {
                            deferred.resolve({msg : 'success', response : postSuccess});
                        },
                        function(postFail) {
                            deferred.reject({msg : 'failed', response : postFail});
                        }
                    );
                },
                // user not logged in and/or not authenticated, handle error...
                function(logInFailure) {
                    console.error(logInFailure);
                    deferred.reject({msg : 'facebook post failed', response : logInFailure });
                }
            );

            return deferred.promise;
        }
    }
}]);
