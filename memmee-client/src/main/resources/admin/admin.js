angular.module('admin', ['admin.services']).
    config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/', {templateUrl: 'home/admin-home.html'}).
        when('/showAllInspirations', {templateUrl: 'inspirations/show-all-inspirations.html'}).
        when('/showAllCategories', {templateUrl: 'categories/show-all-categories.html'}).
        otherwise({redirectTo: '/'});
    }]).
    run(['$rootScope', '$location', function( $rootScope, $location ) {
        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            console.log("route is changing");
        });

        console.log("Application is about to start...");

    }]).run();