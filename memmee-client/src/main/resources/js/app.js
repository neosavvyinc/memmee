angular.module('memmee-app', ['memmee-app.services']).
    config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/home', {templateUrl:'js/home/home-ptl.html'}).
        when('/create', {templateUrl:'js/memmee/create/create-ptl.html'}).
        when('/view', {templateUrl:'js/memmee/view/view-ptl.html'}).
        when('/requiredchangepassword', {templateUrl:'partials/requiredChangePassword.html'}).
        when('/profile', {templateUrl:'js/profile/profile-ptl.html'}).
        when('/share', {templateUrl:'partials/shareMode.html'}).
        otherwise({redirectTo:'/home'});
}]).
    config(['$locationProvider', function ($locationProvider) {
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