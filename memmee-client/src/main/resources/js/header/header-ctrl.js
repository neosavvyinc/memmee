function HeaderController($scope, broadCastService, $location, $timeout) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.loggedInUser = "";

            $scope.visibleLoggedInStyle = 'isHidden';
            $scope.visibleLoggedOutStyle = 'isVisible';
        });

    $scope.$on(LoginControllerEvents.get('LOGIN'), function() {

        console.log("NavigationController:handleLogin()");

        $scope.visibleLoggedInStyle = 'isVisible';
        $scope.visibleLoggedOutStyle = 'isHidden';
        $scope.loggedInUser = broadCastService.user;

    });

    $scope.createModeClicked = function()
    {
        broadCastService.createModeStartedCreateModeController();
        $location.path("create");
    }

    $scope.viewModeClicked = function()
    {
        broadCastService.createModeCancelledCreateModeController();
        $location.path("view");
    }

    $scope.isCreateMode = function () {
        return broadCastService.isCreateMode();
    }

    $scope.isProfileMode = function() {
        var urlPath = $location.path();
        var isProfile = urlPath.indexOf("profile") > -1 ? true : false;
        return isProfile;
    }

    var closedropdownTimer;

    $scope.userSettingsDropdownStyle = "isHidden";
    $scope.toggleSettingsDropdown = function () {
        console.log("mouse over happening.")
        if ($scope.userSettingsDropdownStyle == "isHidden") {
            $scope.userSettingsDropdownStyle = "isVisible";
        }
        else {
            $scope.userSettingsDropdownStyle = "isHidden";
        }

        if( closedropdownTimer )
        {
            closedropdownTimer = undefined;
        }
    };

    $scope.onClickProfile = function() {
        $scope.toggleSettingsDropdown();
        broadCastService.profileOpenedProfileController();
    };

    $scope.closeDropdown = function() {
        $scope.userSettingsDropdownStyle = "isHidden";

        if( closedropdownTimer )
        {
            closedropdownTimer = undefined;
        }
    }

    $scope.closeDropdownIfMouseOutside = function( ){
        closedropdownTimer = $timeout( $scope.closeDropdown, 100);
    };

    $scope.cancelDropdownTimer = function() {
        if( closedropdownTimer )
        {
            $timeout.cancel( closedropdownTimer );
        }
    };

    $scope.$on( "closeAllDropdowns", function(event, next, current) {
        $scope.closeDropdown();
    });

    $scope.signOut = function()
    {
        broadCastService.logoutUser();
        $scope.loggedInUser = "";
        $scope.visibleLoggedInStyle = "isHidden";
        $scope.visibleLoggedOutStyle = 'isVisible';
    };

}

HeaderController.$inject = ['$scope', 'memmeeBroadCastService', '$location', '$timeout'];