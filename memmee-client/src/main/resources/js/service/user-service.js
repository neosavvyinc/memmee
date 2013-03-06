'use strict'

Memmee.Services.factory('userService', function (configuration, $q, $rootScope, $http) {

    return {

        forgotPassword: function forgotPassword(user, resultHandler, faultHandler) {
            $http({method:'POST', url:'/memmeeuserrest/user/forgotpassword?email=' + user['email']}).
                success(function (data, status, headers, config) {
                    $rootScope.$broadcast(LoginControllerEvents.get('FORGOT_PASSWORD_SENT'));
                    resultHandler( data );
                }).
                error(function (data, status, headers, config) {
                    $rootScope.$broadcast(LoginControllerEvents.get('FORGOT_PASSWORD_ERROR'));
                    faultHandler( data );
                });
        }

    }

});
