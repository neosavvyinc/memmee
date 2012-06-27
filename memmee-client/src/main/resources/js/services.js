var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeSecurityService", function($rootScope) {

    var securityService = {};

    securityService.user = null;

    securityService.loginUser = function ( $user ) {
        this.user = $user;
        $rootScope.$broadcast('handleLogin');
    }

    securityService.logoutUser = function( $user ) {
        this.user = null;
        $rootScope.$broadcast('handleLogout');
    }

    return securityService;
});
