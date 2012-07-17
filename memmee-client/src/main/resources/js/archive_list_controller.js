function ArchiveListController($scope, $http, broadCastService) {
    $scope.user = broadCastService.user;
    $scope.memmees = null;

    $http({method:'GET', url:'/memmeerest/getmemmees?apiKey=' + $scope.user.apiKey}).
        success(function (data, status, headers, config) {
            console.log('your memmee has been loaded');
            $scope.memmees = data;
        }).
        error(function (data, status, headers, config) {
              console.log('error loading memmees');
        });

    //UI
    $scope.innerScrollerWidth = function(memmees) {
        return {'width': (memmees.length * 450).toString() + "px"};
    };

    $scope.imageStyle = function(memmee) {
        return {'background-image': 'url(' + memmee.attachment.thumbFilePath + ')'};
    }

    //Action Handlers
    $scope.onMemmeeSelect = function(memmee) {
        broadCastService.memmeeSelectedArchiveListController(memmee);
    };
}

ArchiveListController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];