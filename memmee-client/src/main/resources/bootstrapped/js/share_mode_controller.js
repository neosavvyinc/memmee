function ShareModeController($scope, $http, broadCastService, $location, $routeParams) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.user = broadCastService.user;
            $scope.memmee = null;
            $scope.shareKey = null;
            $scope.errorMode = false;
        });

    //Action Handlers

    //Service Calls
    $scope.getDefaultMemmee = function () {
//        http://local.memmee.com/#/share?shareKey=f2d679d0-cb4f-46b8-9d4a-1cfab3585a96

        $scope.shareKey = $routeParams.shareKey;

        $http({method:'GET', url:'/memmeerest/open?shareKey=' + $scope.shareKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmee = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone memmee');
                $scope.errorMode = true;
            });
    };

    //UI
    $scope.getDisplayDate = function () {
        return $scope.memmee.displayDate.toDateString();
    }

    //Initializaton
    $scope.getDefaultMemmee();

    $scope.showImage = function () {
        if ($scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath) {
            return true;
        }
        return false;
    }
}

ShareModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService', '$location', '$routeParams'];