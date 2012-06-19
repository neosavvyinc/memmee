function SecurityController($scope, securityService) {
    $scope.loggedInUser = null;
    $scope.visibleLoggedInStyle = { visibility: 'hidden' };

    $scope.saveLoggedInUser = function( $user ) {
        $scope.loggedInUser = $user;
        $scope.visibleLoggedInStyle = { visibility: 'visible' };
    }

    $scope.logout = function() {
        $scope.visibleLoggedInStyle = { visibility: 'hidden' };
        securityService.logoutUser($scope.loggedInUser);

    }
}

function NavigationController($scope, securityService) {


    $scope.loggedOutNavigationItems = [
        { displayName: "Home", navigationLink: "#home", selected: "active" }
    ];

    $scope.loggedInNavigationItems = [
            { displayName: "Home", navigationLink: "#home", selected: "active"},
            { displayName: "View", navigationLink: "#view", selected: ""},
            { displayName: "New", navigationLink: "#new", selected: ""}
        ];

    $scope.navigationItems = $scope.loggedOutNavigationItems;

    $scope.select = function( $selectedNavigationLink ) {
        for ( navIndex in $scope.navigationItems )
        {
            if( $scope.navigationItems[navIndex].navigationLink == $selectedNavigationLink.navigationLink )
            {
                $scope.navigationItems[navIndex].selected = "active";
            }
            else
            {
                $scope.navigationItems[navIndex].selected = "";
            }
        }
    }

    $scope.$on('handleLogin', function() {
        $scope.updateNavigation(true);
    });
    $scope.$on('handleLogout', function() {
        $scope.updateNavigation(false);
    });


    $scope.updateNavigation = function( loggedIn ) {

        if( loggedIn )
        {
            $scope.navigationItems = $scope.loggedInNavigationItems;
        }
        else
        {
            $scope.navigationItems = $scope.loggedOutNavigationItems;
        }

    }



}


function RegistrationController($scope, $http, securityService) {

    $scope.user = {
        email: ''
    };

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
                $scope.saveLoggedInUser(data);
                securityService.loginUser(data);
            }).
            error(function(data, status, headers, config) {
                console.log('error while saving a new user');
            });
    }


}

function LoginController($scope, $http) {

    $scope.login = function()
    {
        console.log("This is a test");
    }

}

SecurityController.$inject = ['$scope', 'memmeeSecurityService'];

NavigationController.$inject = ['$scope', 'memmeeSecurityService'];

RegistrationController.$inject = ['$scope', '$http', 'memmeeSecurityService'];