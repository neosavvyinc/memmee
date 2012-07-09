var memmeeServices = angular.module('memmee-app.services', ['ngResource']);
memmeeServices.factory("memmeeBroadCastService", function($rootScope, $location) {

    var broadCastService = {};

    broadCastService.user = null;
    broadCastService.attachment = null;
    broadCastService.apiKey = $location.search().apiKey;

    broadCastService.loginUser = function ( $user ) {
        this.user = $user;
        $rootScope.$broadcast('handleLogin');

        if( $location.url() == "/home" )
            $location.url( "/view" );
    }

    broadCastService.logoutUser = function( $user ) {
        this.user = null;
        $rootScope.$broadcast('handleLogout');
    }

    broadCastService.attachmentSuccess = function( $attachment ) {
        this.attachment = $attachment;
        $rootScope.$broadcast('attachmentUploadSuccess');
    }

    return broadCastService;
});
