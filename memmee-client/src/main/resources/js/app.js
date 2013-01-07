google.load("feeds", "1");
google.setOnLoadCallback(function(){
    angular.bootstrap(document.body, ['memmee-app']);
});

//Initialize Global Namespace
var Memmee = Memmee || {};

//Initialize Name Spaced Modules
Memmee.Constants = angular.module('memmee.constants', [])
Memmee.Services = angular.module('memmee.services', []);

var app = angular.module('memmee-app', ['memmee-app.services', 'ngSanitize', 'memmee.constants' , 'memmee.services']).
    config(['$routeProvider', function ($routeProvider) {
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
    run(['$rootScope', '$location', function( $rootScope, $location ) {
        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            console.log("route is changing");
            $rootScope.$broadcast("closeAllDropdowns");
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