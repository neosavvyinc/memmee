var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeBroadCastService", function($rootScope, $location) {

    var broadCastService = {};

    broadCastService.user = null;
    broadCastService.attachment = null;
    broadCastService.apiKey = $location.search().apiKey;

    broadCastService.loginUser = function ( $user ) {
        this.user = $user;
        localStorage.setItem( "user", JSON.stringify($user) );
        $rootScope.$broadcast('handleLogin');

        if( $location.url() == "/home" )
            $location.url( "/loggedin" );
    }

    broadCastService.logoutUser = function( $user ) {
        this.user = null;
        localStorage.removeItem( "user");
        $location.path('/home');
        $rootScope.$broadcast('handleLogout');
    }

    broadCastService.attachmentSuccess = function( $attachment ) {
        this.attachment = $attachment;
        $rootScope.$broadcast('attachmentUploadSuccess');
    }


    var createMode = false;

    broadCastService.isCreateMode = function() {
        return createMode;
    }

    broadCastService.createModeStarted = function() {
        createMode = true;
        $rootScope.$broadcast('createModeStarted');
    }

    broadCastService.createModeCancelled = function() {
        createMode = false;
        $rootScope.$broadcast('createModeCancelled');
    }

    return broadCastService;
});
