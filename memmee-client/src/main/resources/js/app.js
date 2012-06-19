angular.module('memmee-app', ['memmee-app.services']).
    config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/home', {templateUrl: 'partials/home.html',   controller: ContainerController}).
            when('/view', {templateUrl: 'partials/view.html', controller: ContainerController}).
            when('/new', {templateUrl: 'partials/new.html', controller: ContainerController}).
            when('/profile', {templateUrl: 'partials/profile.html', controller: ContainerController}).
            otherwise({redirectTo: '/home'});
    }]).
    config(['$locationProvider', function($locationProvider) {
//        $locationProvider.html5Mode(true).hashPrefix('#');
    }]).
    run()