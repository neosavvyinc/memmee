function ViewModeController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmee = null;

    //Action Handlers
    $scope.getDefaultMemmee = function () {
        $http({method:'GET', url:'/memmeerest/getmemmee?apiKey=' + $scope.user.apiKey}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been loaded');
                $scope.memmee = data;
            }).error(function (data, status, headers, config) {
                console.log('error loading your doggone memmee');
            });
    };

    $scope.deleteMemmee = function (memmee) {
        $http({method:'DELETE', url:'/memmeerest/deletememmee?apiKey=' + $scope.user.apiKey + "&id=" + memmee.id}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been deleted');
                $scope.getDefaultMemmee();
            }).
            error(function (data, status, headers, config) {
                console.log('error deleting your doggone memmee');
            });
    };

    $scope.getDefaultMemmee();

    $scope.getDisplayDate = function( )
    {
        return $scope.memmee.displayDate.toDateString();
    }

    $scope.showImage = function( )
    {
        if( $scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath )
        {
            return true;
        }
        return false;
    }
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];