var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeSecurityService", function($rootScope) {

    var securityService = {};

    securityService.user = null;

    securityService.loginUser = function ( $user ) {
        this.user = $user;
        localStorage.setItem( "user", JSON.stringify($user) );
        $rootScope.$broadcast('handleLogin');
    }

    securityService.logoutUser = function( $user ) {
        this.user = null;
        localStorage.setItem( "user", null);
        $rootScope.$broadcast('handleLogout');
    }

    if( localStorage.getItem("user") != null )
    {
        var obj = JSON.parse(localStorage.getItem( "user" ));
        securityService.user = obj;
        console.log("Loading a user from local storage: " + obj);
    }

    if( securityService.user != null )
    {
        securityService.loginUser( securityService.user );
    }

    return securityService;
});
