function FooterController($scope, $location, $rootScope) {

    $scope.footerClass = $scope.defaultClass;
    $scope.defaultClass = "footer";
    $scope.termPagesClass = "footerTerms";

    $scope.updateClassOnLocation = function() {
        if($location.path() == '/about') {
            $scope.footerClass = $scope.termPagesClass;
        }
        else if($location.path() == '/legal') {
            $scope.footerClass = $scope.termPagesClass;
        }
        else
        {
            $scope.footerClass = $scope.defaultClass;
        }
    }

    $rootScope.$on('$routeChangeSuccess', function() {
        $scope.updateClassOnLocation();
    });

    $scope.updateClassOnLocation();
}