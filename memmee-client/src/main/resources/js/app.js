angular.module('memmee-app', ['memmee-app.services']).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/home', {templateUrl: 'partials/home.html'}).
            when('/loggedin', {templateUrl: 'partials/loggedin.html'}).
            when('/profile', {templateUrl: 'partials/profile.html'}).
            otherwise({redirectTo: '/home'});
    }]).
    config(['$locationProvider', function($locationProvider) {
    }]).
    run()