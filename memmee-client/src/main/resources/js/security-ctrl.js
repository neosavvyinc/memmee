function SecurityController($scope, broadCastService, $timeout, $location, $routeParams) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.defaultTheme = "../css/bootstrap.css";
            $scope.theme1 = "../css/bootstrap1.css";
            $scope.theme2 = "../css/bootstrap2.css";
            $scope.theme3 = "../css/bootstrap3.css";

            $scope.cssThemeUrl = $scope.defaultTheme;
        });

    $scope.$on('changeToTheme1', function () {
        $scope.cssThemeUrl = $scope.theme1;
        console.log("Theme is now set to 1");
    });
    $scope.$on('changeToTheme2', function () {
        $scope.cssThemeUrl = $scope.theme2;
        console.log("Theme is now set to 2");
    });
    $scope.$on('changeToTheme3', function () {
        $scope.cssThemeUrl = $scope.theme3;
        console.log("Theme is now set to 3");
    });
    $scope.$on('changeToDefaultTheme', function () {
        $scope.cssThemeUrl = $scope.defaultTheme;
        console.log("Theme is now set to default");
    });

    if( localStorage.getItem("user") !== null && localStorage.getItem("user") !== "" )
    {
        var obj = localStorage.getItem( "user" );
        broadCastService.user = JSON.parse(obj);
        broadCastService.loginUser(broadCastService.user);
        console.log("Loading a user from local storage: " + obj);
    }

    if( broadCastService.user !== null )
    {
        $timeout(function() {
            broadCastService.loginUser( broadCastService.user );
        }, 100);
    }
    else if( $location.path() == '/share' )
    {
        console.log("Loading a memmee from a share key");
    }
    else
    {
        $location.path('/home');
    }

    //UI Initialization
    $scope.initializeBodyUI = function() {
        $('body').iealert();
    };
}

SecurityController.$inject = ['$scope', 'memmeeBroadCastService', '$timeout', '$location', '$routeParams'];