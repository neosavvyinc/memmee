function NavigationController($scope) {

    $scope.navigationItems = [
            { displayName: "Home", navigationLink: "#home", selected: "active" },
            { displayName: "View", navigationLink: "#view", selected: "" },
            { displayName: "New", navigationLink: "#new", selected: "" }
        ];

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

}


function RegistrationController($scope, $http) {

    $scope.user = {
        email: ''
    };

    $scope.register = function()
    {
        $http({method: 'POST', url: '/memmeeuserrest/user', data: $scope.user}).
            success(function(data, status, headers, config) {
                console.log('you were successfully registered');
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