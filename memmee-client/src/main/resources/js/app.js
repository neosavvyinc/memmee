//Initialize Global Namespace
var Memmee = Memmee || {};

google.load("feeds", "1");

//Initialize Name Spaced Modules
Memmee.Constants = angular.module('memmee.constants', [])
Memmee.Services = angular.module('memmee.services', []);

var app = angular.module('memmee-app', ['memmee-app.services', 'ngSanitize', 'memmee.constants' , 'memmee.services']).
    config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {

    function getParameterByName (name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(window.location.search);
        if (results == null) {
            return "";
        }
        else {
            return decodeURIComponent(results[1].replace(/\+/g, " "));
        }
    }

    var isPushState = getParameterByName("pushState");
    console.log("Pushstate is: " + isPushState);
    if (isPushState === undefined || isPushState === null || isPushState === "") {
        isPushState = false;
    }

    if (isPushState == true) {
        console.log("Acting like push state should be active");
        routeConfig = {
            html5PushState: true // dev: false, prod: true
        };
    }
    else {
        console.log("Acting like push state should not be active");
        routeConfig = {
            html5PushState: false // dev: false, prod: true
        };
    }



    // enable html5 push state
    $locationProvider.html5Mode(routeConfig.html5PushState);

    $routeProvider.
        when('/home', {templateUrl: 'js/home/home-ptl.html'}).
        when('/create', {templateUrl: 'js/memmee/create/create-ptl.html'}).
        when('/view', {templateUrl: 'js/memmee/view/view-ptl.html'}).
        when('/requiredchangepassword', {templateUrl: 'js/profile/change-password-ptl.html'}).
        when('/profile', {templateUrl: 'js/profile/profile-ptl.html'}).
        when('/share', {templateUrl: 'js/memmee/share/share-ptl.html'}).
        when('/about', {templateUrl: 'js/about/about-ptl.html'}).
        when('/blog', {templateUrl: 'js/blog/blog-ptl.html'}).
        when('/legal', {templateUrl: 'js/legal/legal-ptl.html'}).
        otherwise({redirectTo: '/home'});
    }]).
    run(['$rootScope', '$location', function( $scope, $location ) {

        $scope.isPushState = $location.search().pushState;
        $scope.isMockMode = $location.search().mockMode;

        $scope.routeConfig = {};

        console.log("app.run() pushState=" + $scope.isPushState);
        console.log("app.run() isMockMode=" + $scope.isMockMode);

        if ($scope.isPushState === undefined || $scope.isPushState === null ||
            $scope.isPushState === "") {
            $scope.isPushState = false;
        }

        if ($scope.isPushState == true) {
            console.log("Acting like push state should be active");
            $scope.routeConfig = {
                html5PushState: true // dev: false, prod: true
            };
        }
        else {
            console.log("Acting like push state should not be active");
            $scope.routeConfig = {
                html5PushState: false // dev: false, prod: true
            };
        }

        $scope.$on( "$routeChangeStart", function(event, next, current) {
            console.log("route is changing");
            $scope.$broadcast("closeAllDropdowns");
        });
    }]).
    directive('fileButton', function() {
        return {
            link: function(scope, element, attributes) {

                var el = angular.element(element)
                var button = el.children()[0]

                el.css({
                    position: 'relative',
                    overflow: 'hidden',
                    width: button.offsetWidth,
                    height: button.offsetHeight
                })

                var fileInput = angular.element('<input id="uploadInput" type="file" multiple />')
                fileInput.css({
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    'z-index': '2',
                    width: '100%',
                    height: '100%',
                    opacity: '0',
                    cursor: 'pointer'
                })

                el.append(fileInput)


            }
        }
    }).run();