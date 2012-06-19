angular.module('memmee-app', ['memmee-app.services']).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/home', {templateUrl: 'partials/home.html'}).
            when('/view', {templateUrl: 'partials/view.html'}).
            when('/new', {templateUrl: 'partials/new.html'}).
            when('/profile', {templateUrl: 'partials/profile.html'}).
            otherwise({redirectTo: '/home'});
    }]).
    config(['$locationProvider', function($locationProvider) {
    }]).
    run()