angular.module('admin', ['admin.services']).
    config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        //when('/home', {templateUrl: 'js/home/home-ptl.html'}).
        otherwise({redirectTo: '/'});
    }]).
    run(['$rootScope', '$location', function( $rootScope, $location ) {
        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            console.log("route is changing");
        });

        console.log("Application is about to start...");

    }]).run();